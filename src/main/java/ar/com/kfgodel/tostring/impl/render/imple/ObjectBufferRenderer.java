package ar.com.kfgodel.tostring.impl.render.imple;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.properties.ObjectRendering;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.RenderingBuffer;
import net.vidageek.mirror.dsl.Mirror;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This type knows how to render objects into buffers
 * Created by kfgodel on 15/09/14.
 */
public class ObjectBufferRenderer implements PartialBufferRenderer<Object> {
    @Override
    public RenderingBuffer render(Object value) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();

        // If defined, we try its own toString
        String customString = null;
        Exception errorRaisedInCustomString = null;
        try {
            customString = getStringFromOwnDefinitionFor(value);
        } catch (Exception e) {
            errorRaisedInCustomString = e;
        }
        if(customString != null){
            // It has its own definition, we use that
            buffer.addPart(customString);
        }else{
            // It failed, or didn't have one. We use our own
            useOurDefinitionFor(value, buffer);
            if(errorRaisedInCustomString != null){
                // We let the user know that something went wrong with custom string
                buffer.addPart(" instead of ");
                buffer.addPart(errorRaisedInCustomString.getClass().getSimpleName());
                buffer.addPart(": ");
                buffer.addPart(errorRaisedInCustomString.getMessage());
            }
        }
        return buffer;
    }

    /**
     * Gets the String representation from custom defined toString() method
     * @param object The object to look for custom definition of toString()
     * @return null if the object inherits definition from object
     */
    private String getStringFromOwnDefinitionFor(Object object) {
        Class<?> objectClass = object.getClass();
        Method definedToStringMethod = new Mirror().on(objectClass).reflect().method("toString").withoutArgs();
        if(definedToStringMethod.getDeclaringClass().equals(Object.class)){
            // It's the default definition. It doesn't have one
            return null;
        }
        return object.toString();
    }


    private void useOurDefinitionFor(Object object, ListRenderingBuffer buffer) {
        Class<?> objectClass = object.getClass();
        buffer.addPart(objectClass.getSimpleName());
        buffer.addPart(Stringer.CONFIGURATION.getOpeningIdSymbol());
        List<Field> objectFields = new ArrayList<>(new Mirror().on(objectClass).reflectAll().fields());
        filterIgnoredFields(objectFields);
        Field idField = segregateIdFieldFrom(objectFields);
        buffer.addPart(calculateIdValueFor(object, idField));
        buffer.addPart(Stringer.CONFIGURATION.getClosingIdSymbol());
        if(objectFields.size() > 0){
            // Only if it has any fields we include a body
            buffer.addPart(Stringer.CONFIGURATION.getOpeningHashSymbol());
            addContentTo(buffer, object, objectFields);
            buffer.addPart(Stringer.CONFIGURATION.getClosingHashSymbol());
        }

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
     * Removes the id field from teh list if present, and returns it
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

    private void addContentTo(RenderingBuffer buffer, Object object, List<Field> objectFields) {
        ObjectRendering rendering = ObjectRendering.create(object, objectFields);
        rendering.process(buffer);
    }

}
