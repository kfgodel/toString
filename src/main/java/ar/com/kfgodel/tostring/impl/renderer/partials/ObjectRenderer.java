package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.properties.ObjectField;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;
import net.vidageek.mirror.dsl.ClassController;
import net.vidageek.mirror.dsl.Mirror;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This type knows how to render a typical object
 * Created by kfgodel on 11/09/14.
 */
public class ObjectRenderer implements PartialRenderer<Object> {

    public static final ObjectRenderer INSTANCE = new ObjectRenderer();

    @Override
    public String render(Object object) {
        // If defined we try its own definition
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
            builder.append("instead of ");
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
        builder.append(calculateIdValueFor(object));
        builder.append(Stringer.CONFIGURATION.getClosingIdSymbol());
        List<Field> objectFields = new Mirror().on(objectClass).reflectAll().fields();
        if(objectFields.size() > 0){
            // Only if it has any fields we include a body
            builder.append(Stringer.CONFIGURATION.getOpeningHashSymbol());
            addContentTo(builder, object, objectFields);
            builder.append(Stringer.CONFIGURATION.getClosingHashSymbol());
        }
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
     * @return The id value or a hashcode for the object
     */
    private String calculateIdValueFor(Object object) {
        Class<?> objectClass = object.getClass();
        Field idField = new Mirror().on(objectClass).reflect().field("id");
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
        int lowToleranceCardinality = Stringer.CONFIGURATION.getCardinalityForLowTolerance();
        int contentSizeLimit = (objectFields.size() > lowToleranceCardinality)?  Stringer.CONFIGURATION.getLowToleranceSize() : Stringer.CONFIGURATION.getHighToleranceSize();

        AtomicInteger counter = new AtomicInteger(1);
        boolean limitExceeded = objectFields.stream()
                .map((field) -> ObjectField.create(object, field))
                .map((objectField)-> Stringer.representationOf(objectField))
                .anyMatch((fieldRepresentation) -> {
                    builder.append(fieldRepresentation);
                    boolean isNotLastElement = counter.getAndIncrement() < objectFields.size();
                    if (isNotLastElement) {
                        //Is not the last, we need a separator
                        builder.append(Stringer.CONFIGURATION.getSequenceElementSeparatorSymbol());
                    }
                    // End if we exceeded the limit
                    return builder.length() > contentSizeLimit;
                });
        boolean beforeTheLastOne = counter.get() <= objectFields.size();
        if(limitExceeded && beforeTheLastOne){
            //Not all the content made it
            builder.append(Stringer.CONFIGURATION.getTruncatedContentSymbol());
        }
    }

}
