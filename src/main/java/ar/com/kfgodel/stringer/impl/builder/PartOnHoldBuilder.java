package ar.com.kfgodel.stringer.impl.builder;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.builder.PartialDefinitionBuilder;
import ar.com.kfgodel.stringer.api.builder.StringerBuilder;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.DynamicRepresentationStringer;
import ar.com.kfgodel.stringer.impl.LazyRepresentationStringer;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Date: 31/03/18 - 12:18
 */
public class PartOnHoldBuilder implements PartialDefinitionBuilder {

  private MutableBuilder originalBuilder;
  private Supplier<?> dynamicValue;

  public static PartOnHoldBuilder create(MutableBuilder originalBuilder, Supplier<?> dynamicValue) {
    PartOnHoldBuilder builder = new PartOnHoldBuilder();
    builder.originalBuilder = originalBuilder;
    builder.dynamicValue = dynamicValue;
    return builder;
  }

  @Override
  public StringerBuilder cacheable() {
    addAsLazyPart();
    return originalBuilder;
  }

  @Override
  public StringerBuilder dynamic() {
    addAsDynamicPart();
    return originalBuilder;
  }

  private void addAsLazyPart() {
    this.originalBuilder.addPart(LazyRepresentationStringer.create(dynamicValue, originalBuilder.getConfiguration()));
  }
  private void addAsDynamicPart() {
    this.originalBuilder.addPart(DynamicRepresentationStringer.create(dynamicValue, originalBuilder.getConfiguration()));
  }

  @Override
  public StringerConfiguration getConfiguration() {
    return originalBuilder.getConfiguration();
  }

  @Override
  public Stringer build() {
    this.addAsDynamicPart();
    return originalBuilder.build();
  }


  @Override
  public StringerBuilder with(Object immutableValue) {
    this.addAsDynamicPart();
    return originalBuilder.with(immutableValue);
  }

  @Override
  public StringerBuilder with(Object... immutableValues) {
    this.addAsDynamicPart();
    return originalBuilder.with(immutableValues);
  }

  @Override
  public PartialDefinitionBuilder with(Supplier<?> dynamicValue) {
    this.addAsDynamicPart();
    return originalBuilder.with(dynamicValue);
  }

  @Override
  public StringerBuilder with(Supplier<?>... dynamicValues) {
    this.addAsDynamicPart();
    return originalBuilder.with(dynamicValues);
  }

  @Override
  public PartialDefinitionBuilder withProperty(String propertyName, Supplier<?> propertyValue) {
    this.addAsDynamicPart();
    return originalBuilder.withProperty(propertyName, propertyValue);
  }

  @Override
  public PartialDefinitionBuilder andProperty(String propertyName, Supplier<?> propertyValue) {
    this.addAsDynamicPart();
    return originalBuilder.andProperty(propertyName, propertyValue);
  }

  @Override
  public StringerBuilder enclosingIn(String prefix, String suffix, Function<StringerBuilder, StringerBuilder> definition) {
    this.addAsDynamicPart();
    return originalBuilder.enclosingIn(prefix, suffix, definition);
  }

  @Override
  public StringerBuilder enclosingAsState(Function<StringerBuilder, StringerBuilder> definition) {
    this.addAsDynamicPart();
    return originalBuilder.enclosingAsState(definition);
  }

  @Override
  public StringerBuilder representing(Object representable) {
    this.addAsDynamicPart();
    return originalBuilder.representing(representable);
  }
}
