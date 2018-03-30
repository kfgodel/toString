package ar.com.kfgodel.stringer.impl.builder;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.builder.StringerBuilder;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.CompositeRepresentationStringer;
import ar.com.kfgodel.stringer.impl.DynamicRepresentationStringer;
import ar.com.kfgodel.stringer.impl.EmptyRepresentationStringer;
import ar.com.kfgodel.stringer.impl.ImmutableRepresentationStringer;
import ar.com.kfgodel.stringer.impl.config.DefaultStringerConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Date: 30/03/18 - 13:42
 */
public class MutableBuilder implements StringerBuilder {

  private List<Stringer> parts;
  private StringerConfiguration configuration;

  /**
   * Creates a builder instance with the deafult stringer configuration
   */
  public static StringerBuilder createDefault() {
    return create(DefaultStringerConfiguration.create());
  }

  public static MutableBuilder create(StringerConfiguration configuration) {
    MutableBuilder builder = new MutableBuilder();
    builder.parts = new ArrayList<>();
    builder.configuration = configuration;
    return builder;
  }



  @Override
  public Stringer build() {
    if(parts.isEmpty()){
      //Optimization
      return EmptyRepresentationStringer.create();
    }
    CompositeRepresentationStringer composite = CompositeRepresentationStringer.create();
    parts.forEach(composite::addPart);
    return composite;
  }

  @Override
  public StringerBuilder with(Object immutableValue) {
    String representation = configuration.convertToString(immutableValue);
    parts.add(ImmutableRepresentationStringer.create(representation));
    return this;
  }

  @Override
  public StringerBuilder with(Object... immutableValues) {
    Arrays.stream(immutableValues)
      .forEach(this::with);
    return this;
  }

  @Override
  public StringerBuilder with(Supplier<?> dynamicValue) {
    Supplier<String> dynamicConversion = () -> configuration.convertToString(dynamicValue.get());
    this.parts.add(DynamicRepresentationStringer.create(dynamicConversion));
    return this;
  }

  @Override
  public StringerBuilder with(Supplier<?>... dynamicValues) {
    Arrays.stream(dynamicValues)
      .forEach(this::with);
    return this;
  }

  @Override
  public StringerBuilder withProperty(String propertyName, Supplier<?> propertyValue) {
    this.with(propertyName);
    this.with(this.configuration.getPropertyNameToValueSeparator());
    this.with(propertyValue);
    return this;
  }

  @Override
  public StringerBuilder andProperty(String propertyName, Supplier<?> propertyValue) {
    this.with(configuration.getPropertySeparator());
    this.withProperty(propertyName, propertyValue);
    return this;
  }

  @Override
  public StringerBuilder enclosingIn(String prefix, String suffix, Consumer<StringerBuilder> definition) {
    this.with(prefix);
    definition.accept(this);
    this.with(suffix);
    return this;
  }
}