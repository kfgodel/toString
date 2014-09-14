package ar.com.kfgodel.tostring.impl.properties;

import net.vidageek.mirror.dsl.Mirror;

import java.lang.reflect.Field;

/**
 * This type represents an object field to be represented as part of the object
 * Created by kfgodel on 13/09/14.
 */
public class ObjectField {

    private Field field;
    private Object object;
    private Object fieldValue;

    public static ObjectField create(Object object, Field field) {
        ObjectField objectField = new ObjectField();
        objectField.field = field;
        objectField.object = object;
        objectField.fieldValue = new Mirror().on(object).get().field(field);
        return objectField;
    }

    public String getFieldName() {
        return this.field.getName();
    }

    public Object getFieldValue() {
        return this.fieldValue;
    }

    /**
     * Indicates if this field value is null
     * @return True if this field is null on the object
     */
    public boolean hasNullValue() {
        return this.fieldValue == null;
    }
}
