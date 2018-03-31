package ar.com.kfgodel.stringer.impl.reflection;

import ar.com.kfgodel.stringer.impl.builder.MutableBuilder;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * This class adds a small abstraction over field reflection so its easier to use for representations
 * <p>
 * Date: 30/03/18 - 16:28
 */
public class ObjectField implements Comparable<ObjectField> {

  private Field nativeField;
  private Object anObject;

  public static ObjectField create(Object anObject, Field nativeField) {
    ObjectField property = new ObjectField();
    property.anObject = anObject;
    property.nativeField = nativeField;
    return property;
  }

  public String getName() {
    return nativeField.getName();
  }

  public Object getValue() {
    try {
      return nativeField.get(anObject);
    } catch (Exception e) {
      throw new ObjectFieldException("Failed to get value from field[" + nativeField + "] for an object", e);
    }
  }

  @Override
  public int compareTo(ObjectField o) {
    return this.getName().compareTo(o.getName());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ObjectField)) return false;
    ObjectField that = (ObjectField) o;
    return Objects.equals(nativeField, that.nativeField) &&
      Objects.equals(anObject, that.anObject);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nativeField, anObject);
  }

  @Override
  public String toString() {
    return MutableBuilder.createDefault()
      .with(getClass().getSimpleName())
      .enclosingAsState((builder) -> builder.with(this.getName()))
      .build()
      .toString();
  }
}
