package ar.com.kfgodel.stringer.impl.config;

import ar.com.kfgodel.stringer.api.config.StringerConfiguration;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class represents a mutable configuration with sensible defaults
 * Date: 15/03/18 - 21:52
 */
public class DefaultStringerConfiguration implements StringerConfiguration {

  public static final int DEFAULT_STACK_LEVEL_COUNT = 3;
  public static final String DEFAULT_PROPERTY_TO_VALUE_SEPARATOR = ": ";

  private Function<Object, String> stringConversion;
  private Supplier<String> nullRepresentationSupplier;
  private int exceptionStackLevelCount;
  private String propertyToValueSeparator;

  public static DefaultStringerConfiguration create() {
    DefaultStringerConfiguration configuration = new DefaultStringerConfiguration();
    configuration.nullRepresentationSupplier = configuration::changeNullValue;
    configuration.exceptionStackLevelCount = DEFAULT_STACK_LEVEL_COUNT;
    configuration.stringConversion = String::valueOf;
    configuration.propertyToValueSeparator = DEFAULT_PROPERTY_TO_VALUE_SEPARATOR;
    return configuration;
  }

  private String changeNullValue() {
    return "null";
  }

  @Override
  public StringerConfiguration usingForNullValues(Supplier<String> nullRepresentationSupplier) {
    this.nullRepresentationSupplier = nullRepresentationSupplier;
    return this;
  }

  @Override
  public StringerConfiguration usingForStringConversion(Function<Object, String> stringConversion) {
    this.stringConversion = stringConversion;
    return this;
  }

  @Override
  public String ensureNonNullRepresentation(String representation) {
    if(representation == null){
      return nullRepresentationSupplier.get();
    }
    return representation;
  }

  @Override
  public StringerConfiguration limitingFailedRepresentationStackTo(int exceptionStackLevelsCount) {
    this.exceptionStackLevelCount = exceptionStackLevelsCount;
    return this;
  }

  @Override
  public int getExceptionStackLimit() {
    return this.exceptionStackLevelCount;
  }

  @Override
  public String convertToString(Object value) {
    return stringConversion.apply(value);
  }

  @Override
  public String getPropertyNameToValueSeparator() {
    return propertyToValueSeparator;
  }
}
