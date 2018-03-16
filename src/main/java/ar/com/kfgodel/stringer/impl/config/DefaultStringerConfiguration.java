package ar.com.kfgodel.stringer.impl.config;

import ar.com.kfgodel.stringer.api.config.StringerConfiguration;

import java.util.function.Supplier;

/**
 * This class represents a mutable configuration with sensible defaults
 * Date: 15/03/18 - 21:52
 */
public class DefaultStringerConfiguration implements StringerConfiguration {


  private Supplier<String> nullRepresentationSupplier;

  public static DefaultStringerConfiguration create() {
    DefaultStringerConfiguration configuration = new DefaultStringerConfiguration();
    configuration.nullRepresentationSupplier = configuration::changeNullValue;
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
}
