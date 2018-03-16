package ar.com.kfgodel.stringer.impl.config;

import ar.com.kfgodel.stringer.api.config.StringerConfiguration;

import java.util.function.Supplier;

/**
 * This class represents a mutable configuration with sensible defaults
 * Date: 15/03/18 - 21:52
 */
public class DefaultStringerConfiguration implements StringerConfiguration {

  public static final int DEFAULT_STACK_LEVEL_COUNT = 3;

  private Supplier<String> nullRepresentationSupplier;
  private int exceptionStackLevelCount;

  public static DefaultStringerConfiguration create() {
    DefaultStringerConfiguration configuration = new DefaultStringerConfiguration();
    configuration.nullRepresentationSupplier = configuration::changeNullValue;
    configuration.exceptionStackLevelCount = DEFAULT_STACK_LEVEL_COUNT;
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
}
