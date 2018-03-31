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
  private static final String DEFAULT_PROPERTY_SEPARATOR = ", ";
  private static final String DEFAULT_STATE_PREFIX = "{";
  private static final String DEFAULT_STATE_SUFFIX = "}";

  private Function<Object, String> stringConversion;
  private Supplier<String> nullRepresentationSupplier;
  private int exceptionStackLevelCount;
  private String propertyToValueSeparator;
  private String propertySeparator;
  private String statePrefix;
  private String stateSuffix;

  public static DefaultStringerConfiguration create() {
    DefaultStringerConfiguration configuration = new DefaultStringerConfiguration();
    configuration.nullRepresentationSupplier = configuration::defaultNullRepresentation;
    configuration.exceptionStackLevelCount = DEFAULT_STACK_LEVEL_COUNT;
    configuration.stringConversion = configuration::defaultStringConversion;
    configuration.propertyToValueSeparator = DEFAULT_PROPERTY_TO_VALUE_SEPARATOR;
    configuration.propertySeparator = DEFAULT_PROPERTY_SEPARATOR;
    configuration.statePrefix = DEFAULT_STATE_PREFIX;
    configuration.stateSuffix = DEFAULT_STATE_SUFFIX;
    return configuration;
  }

  private String defaultStringConversion(Object value) {
    if(value == null){
      return nullRepresentationSupplier.get();
    }
    String representation = String.valueOf(value);
    return ensureNonNullRepresentation(representation);
  }

  private String defaultNullRepresentation() {
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
  public StringerConfiguration usingAsPropertyNameToValueSeparator(String nameToValueSeparator) {
    this.propertyToValueSeparator = nameToValueSeparator;
    return this;
  }

  @Override
  public String getPropertyNameToValueSeparator() {
    return propertyToValueSeparator;
  }

  @Override
  public StringerConfiguration usingAsPropertySeparator(String propertySeparator) {
    this.propertySeparator = propertySeparator;
    return this;
  }

  @Override
  public String getPropertySeparator() {
    return propertySeparator;
  }

  @Override
  public StringerConfiguration usingAsStatePrefix(String prefix) {
    this.statePrefix = prefix;
    return this;
  }

  @Override
  public String getStatePrefix() {
    return statePrefix;
  }

  @Override
  public StringerConfiguration usingAsStateSuffix(String suffix) {
    this.stateSuffix = suffix;
    return this;
  }

  @Override
  public String getStateSuffix() {
    return stateSuffix;
  }
}
