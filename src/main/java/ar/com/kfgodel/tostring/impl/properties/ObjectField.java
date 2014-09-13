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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static ObjectField create(Object object, Field field) {
        ObjectField objectField = new ObjectField();
        objectField.field = field;
        objectField.object = object;
        return objectField;
    }

    public String getFieldName() {
        return this.field.getName();
    }

    public Object getFieldValue() {
        return new Mirror().on(this.object).get().field(this.field);
    }
}
