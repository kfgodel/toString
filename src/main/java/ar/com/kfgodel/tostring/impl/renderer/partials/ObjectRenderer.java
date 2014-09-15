package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.properties.ObjectField;
import ar.com.kfgodel.tostring.impl.properties.ObjectRendering;
import ar.com.kfgodel.tostring.impl.properties.RenderedObjectField;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;
import net.vidageek.mirror.dsl.ClassController;
import net.vidageek.mirror.dsl.Mirror;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This type knows how to render a typical object
 * Created by kfgodel on 11/09/14.
 */
public class ObjectRenderer implements PartialRenderer<Object> {

    public static final ObjectRenderer INSTANCE = new ObjectRenderer();

    @Override
    public String render(Object object) {
        // If defined we try its own toString
        String customDefinedString = null;
        Exception errorRaisedInCustomString = null;
        try {
            customDefinedString = getStringFromOwnDefinitionFor(object);
        } catch (Exception e) {
            errorRaisedInCustomString = e;
        }
        if(customDefinedString != null){
            // It has its own definition, we use that
            return customDefinedString;
        }

        // It failed, or didn't have one. We use our own
        StringBuilder builder = new StringBuilder();
        renderObjectInto(builder, object);
        if(errorRaisedInCustomString != null){
            // We let the user know that something went wrong with custom string
            builder.append(" instead of ");
            builder.append(errorRaisedInCustomString.getClass().getSimpleName());
            builder.append(": ");
            builder.append(errorRaisedInCustomString.getMessage());
        }
        return builder.toString();
    }

    /**
     * Renders the object in our way the given builder
     * @param builder The builder to render into
     * @param object The object to represent
     */
    private void renderObjectInto(StringBuilder builder, Object object) {
        Class<?> objectClass = object.getClass();
        builder.append(objectClass.getSimpleName());
        builder.append(Stringer.CONFIGURATION.getOpeningIdSymbol());
        List<Field> objectFields = new ArrayList<>(new Mirror().on(objectClass).reflectAll().fields());
        filterIgnoredFields(objectFields);
        Field idField = segregateIdFieldFrom(objectFields);
        builder.append(calculateIdValueFor(object, idField));
        builder.append(Stringer.CONFIGURATION.getClosingIdSymbol());
        if(objectFields.size() > 0){
            // Only if it has any fields we include a body
            builder.append(Stringer.CONFIGURATION.getOpeningHashSymbol());
            addContentTo(builder, object, objectFields);
            builder.append(Stringer.CONFIGURATION.getClosingHashSymbol());
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

    private void addContentTo(StringBuilder builder, Object object, List<Field> objectFields) {
        ObjectRendering rendering = ObjectRendering.create(object, objectFields);
        rendering.process(builder);
    }

}
