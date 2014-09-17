package ar.com.kfgodel.tostring.impl.render.renderers;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.StringerConfiguration;
import ar.com.kfgodel.tostring.impl.properties.ObjectField;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.buffer.RenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.renderers.collections.SequenceBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.renderers.recursive.RecursiveRenderObjectFieldIntoBuffer;
import net.vidageek.mirror.dsl.Mirror;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    private StringerConfiguration config;

    @Override
    public RenderingBuffer render(Object value) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();

        // If defined, we try its own toString
        Optional<String> customString = Optional.empty();
        Optional<Exception> errorRaisedInCustomString = Optional.empty();
        try {
            customString = useToStringDefinedIn(value);
        } catch (Exception e) {
            errorRaisedInCustomString = Optional.of(e);
        }
        if(customString.isPresent()){
            // It has its own definition, we use that
            buffer.addPart(customString.get());
        }else{
            // It failed, or it didn't have one. We use our own
            useOurDefinitionFor(value, buffer);
            errorRaisedInCustomString.ifPresent((exception) -> {
                addErrorDescriptionTo(buffer, exception);
            });
        }
        return buffer;
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

    /**
     * Gets the String representation from custom defined toString() method
     * @param object The object to look for custom definition of toString()
     * @return null if the object inherits definition from object
     */
    private Optional<String> useToStringDefinedIn(Object object) {
        Class<?> objectClass = object.getClass();
        Method definedToStringMethod = new Mirror().on(objectClass).reflect().method("toString").withoutArgs();
        if(definedToStringMethod.getDeclaringClass().equals(Object.class)){
            // It's the default definition. It doesn't have one
            return Optional.empty();
        }
        return Optional.ofNullable(object.toString());
    }


    private void useOurDefinitionFor(Object object, ListRenderingBuffer buffer) {
        Class<?> objectClass = object.getClass();
        List<Field> objectFields = new ArrayList<>(new Mirror().on(objectClass).reflectAll().fields());
        filterIgnoredFields(objectFields);
        Field idField = segregateIdFieldFrom(objectFields);

        addTypeAndIdPrefixTo(buffer, object, idField);
        addFieldValuesTo(buffer, object, objectFields);
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
     * Removes fields that are banned from representation
     * @param objectFields The fields to omit during representation
     */
    private void filterIgnoredFields(List<Field> objectFields) {
        for (Iterator<Field> iterator = objectFields.iterator(); iterator.hasNext(); ) {
            Field next = iterator.next();
            if(next.getName().equals("$jacocoData")){
                // Jacoco adds this on CI, and we don't want it
                iterator.remove();
            }
        }
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
            return Stringer.representationOf(idValue);
        }
        // It doesn't have an ID field, or it's null. We use native hashcode
        int nativeHashcode = System.identityHashCode(object);
        return Integer.toHexString(nativeHashcode);
    }

    public static ObjectBufferRenderer create(StringerConfiguration config) {
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
