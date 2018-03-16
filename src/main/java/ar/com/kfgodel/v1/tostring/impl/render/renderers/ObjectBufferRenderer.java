package ar.com.kfgodel.v1.tostring.impl.render.renderers;

import ar.com.kfgodel.v1.tostring.ImplementedWithStringer;
import ar.com.kfgodel.v1.tostring.OldStringer;
import ar.com.kfgodel.v1.tostring.config.OldStringerConfiguration;
import ar.com.kfgodel.v1.tostring.impl.properties.ObjectField;
import ar.com.kfgodel.v1.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.RenderingBuffer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.SingleStringBuffer;
import ar.com.kfgodel.v1.tostring.impl.render.renderers.collections.SequenceBufferRenderer;
import ar.com.kfgodel.v1.tostring.impl.render.renderers.recursive.RecursiveRenderObjectFieldIntoBuffer;
import net.vidageek.mirror.dsl.Mirror;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This type knows how to render objects into buffers
 * Created by kfgodel on 15/09/14.
 */
public class ObjectBufferRenderer implements PartialBufferRenderer<Object> {

    private SequenceBufferRenderer sequenceRenderer;
    private OldStringerConfiguration config;

    @Override
    public RenderingBuffer render(Object value) {
        // If it has other definition of toString we try to use it
        Optional<Exception> errorRaisedInCustomString = Optional.empty();
        boolean itHasNonStringerCustomToString = getDifferentStringDefinitionFrom(value);
        if(itHasNonStringerCustomToString){
            try {
                return useObjectDefinitionOfToString(value);
            } catch (Exception e) {
                // If it fails we record the error
                errorRaisedInCustomString = Optional.of(e);
            }
        }

        // It failed, or it didn't have one. We use our own
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        useOurDefinitionFor(value, buffer, errorRaisedInCustomString);
        return buffer;
    }

    /**
     * Renders the object representation using its toString definition
     * @param value The object to render
     * @return The rendered buffer
     * @throws java.lang.Exception if something failed in object toString definition
     */
    private RenderingBuffer useObjectDefinitionOfToString(Object value){
        return SingleStringBuffer.create(value.toString());
    }

    /**
     * Extracts the toString() method defined in the object class that is not the default Object#toString() and
     * was not implemented with Stringer.
     * @param value The object to extract the method from
     * @return True id the object has a definition of toString that's not from object and it's not implemented with Stringer
     */
    private boolean getDifferentStringDefinitionFrom(Object value) {
        Method definedToStringMethod = new Mirror().on(value.getClass()).reflect().method("toString").withoutArgs();
        if(definedToStringMethod.getDeclaringClass().equals(Object.class)){
            // It's the default definition. It doesn't have one
            return false;
        }
        ImplementedWithStringer annotation = definedToStringMethod.getAnnotation(ImplementedWithStringer.class);
        if(annotation != null){
            // It's implemented with Stringer, so we don't have to call it
            return false;
        }
        return true;
    }

    /**
     * Adds the exception description to this object render
     */
    private void addErrorDescriptionTo(ListRenderingBuffer buffer, Exception exception) {
        // We let the user know that something went wrong with custom string
        buffer.addPart(" instead of ");
        buffer.addPart(exception.getClass().getSimpleName());
        buffer.addPart(": ");
        buffer.addPart(exception.getMessage());
    }

    private void useOurDefinitionFor(Object object, ListRenderingBuffer buffer, Optional<Exception> errorRaisedInCustomString) {
        Class<?> objectClass = object.getClass();
        List<Field> objectFields = getRenderableFieldsFrom(objectClass);
        Field idField = segregateIdFieldFrom(objectFields);

        addTypeAndIdPrefixTo(buffer, object, idField);
        addFieldValuesTo(buffer, object, objectFields);
        errorRaisedInCustomString.ifPresent((exception) -> {
            addErrorDescriptionTo(buffer, exception);
        });
    }

    /**
     * Returns the fields from the given class that can be rendered.<br>
     *     Static and banned fields are ignored
     * @param objectClass The class to inspect
     * @return The list of renderable fields (if any)
     */
    private List<Field> getRenderableFieldsFrom(Class<?> objectClass) {
        List<Field> objectFields = new ArrayList<>(new Mirror().on(objectClass).reflectAll().fields().matching((field)->
                        !Modifier.isStatic(field.getModifiers()) && !field.getName().equals("$jacocoData")
        ));
        return objectFields;
    }

    private void addFieldValuesTo(ListRenderingBuffer buffer, Object object, List<Field> fields) {
        List<ObjectField> objectFields = fields.stream().map((field) -> ObjectField.create(object, field)).collect(Collectors.toList());
        this.sequenceRenderer.render(buffer, objectFields.iterator(), objectFields.size());
    }

    /**
     * Adds a prefix with the type and Id for the object
     */
    private void addTypeAndIdPrefixTo(ListRenderingBuffer buffer, Object object, Field idField) {
        buffer.addPart(object.getClass().getSimpleName());
        buffer.addPart(config.getOpeningIdSymbol());
        buffer.addPart(calculateIdValueFor(object, idField));
        buffer.addPart(config.getClosingIdSymbol());
    }

    /**
     * Removes the id field from the list if present, and returns it
     */
    private Field segregateIdFieldFrom(List<Field> objectFields) {
        Iterator<Field> fieldIterator = objectFields.iterator();
        while(fieldIterator.hasNext()){
            Field field = fieldIterator.next();
            if(field.getName().equals("id")){
                fieldIterator.remove();
                return field;
            }
        }
        return null;
    }

    /**
     * Gets the id value of a field in the object or calculates it as the system hashcode
     * @param object The object to discriminate
     * @param idField The optional id field on the object
     * @return The id value or a hashcode for the object
     */
    private String calculateIdValueFor(Object object, Field idField) {
        // Let's try to get the id value, if it has one
        Object idValue = (idField == null)? null : new Mirror().on(object).get().field(idField);
        if(idValue != null){
            return OldStringer.representationOf(idValue);
        }
        // It doesn't have an ID field, or it's null. We use native hashcode
        int nativeHashcode = System.identityHashCode(object);
        return Integer.toHexString(nativeHashcode);
    }

    public static ObjectBufferRenderer create(OldStringerConfiguration config) {
        ObjectBufferRenderer renderer = new ObjectBufferRenderer();
        renderer.config = config;
        renderer.sequenceRenderer = SequenceBufferRenderer.create(
                config.getOpeningHashSymbol(),
                config.getClosingHashSymbol(),
                RecursiveRenderObjectFieldIntoBuffer.create(config),
                true,
                config);
        return renderer;
    }

}
