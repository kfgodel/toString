package ar.com.kfgodel.stringer.impl.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * This class abstracts some of java reflection to access fields so we don't need extra dependencies and
 * redeuce traditional reflection verbosity
 * Date: 30/03/18 - 16:31
 */
public class ObjectFieldExtractor {

  private Object anObject;

  public static ObjectFieldExtractor create(Object anObject) {
    ObjectFieldExtractor extractor = new ObjectFieldExtractor();
    extractor.anObject = anObject;
    return extractor;
  }

  public List<ObjectField> extractFields() {
    Set<ObjectField> fields = new TreeSet<>();

    Class<?> currentClass = anObject.getClass();
    while(currentClass != null){
      List<ObjectField> classFields = getFieldsFrom(currentClass, anObject);
      fields.addAll(classFields);
      currentClass = currentClass.getSuperclass();
    }
    return new ArrayList<>(fields);
  }

  private List<ObjectField> getFieldsFrom(Class<?> currentClass, Object anObject) {
    Field[] declaredFields = currentClass.getDeclaredFields();
    return Arrays.stream(declaredFields)
      .filter(declaredField -> !Modifier.isStatic(declaredField.getModifiers()) ) //only instance fields
      .peek(declaredField -> declaredField.setAccessible(true)) // Make private fields accesible
      .map(declaredField -> ObjectField.create(anObject, declaredField))
      .collect(Collectors.toList());
  }
}
