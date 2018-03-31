package ar.com.kfgodel.stringer.impl.builder;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.builder.PartialDefinitionBuilder;
import ar.com.kfgodel.stringer.api.builder.StringerBuilder;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.CompositeRepresentationStringer;
import ar.com.kfgodel.stringer.impl.EmptyRepresentationStringer;
import ar.com.kfgodel.stringer.impl.ImmutableRepresentationStringer;
import ar.com.kfgodel.stringer.impl.config.DefaultStringerConfiguration;
import ar.com.kfgodel.stringer.impl.optimizer.PartsOptimizer;
import ar.com.kfgodel.stringer.impl.reflection.ObjectField;
import ar.com.kfgodel.stringer.impl.reflection.ObjectFieldExtractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
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
  public StringerConfiguration getConfiguration() {
    return configuration;
  }

  @Override
  public Stringer build() {
    List<Stringer> optimizedParts = PartsOptimizer.create(this.parts)
      .optimize();
    if (optimizedParts.isEmpty()) {
      //Optimization
      return EmptyRepresentationStringer.create();
    }
    if(optimizedParts.size() == 1){
      return optimizedParts.get(0);
    }
    CompositeRepresentationStringer composite = CompositeRepresentationStringer.create(this.configuration);
    optimizedParts.forEach(composite::addPart);
    return composite;
  }

  @Override
  public StringerBuilder with(Object immutableValue) {
    return addPart(ImmutableRepresentationStringer.create(immutableValue, configuration));
  }

  public MutableBuilder addPart(Stringer aPart) {
    parts.add(aPart);
    return this;
  }

  @Override
  public StringerBuilder with(Object... immutableValues) {
    Arrays.stream(immutableValues)
      .forEach(this::with);
    return this;
  }

  @Override
  public PartialDefinitionBuilder with(Supplier<?> dynamicValue) {
    PartOnHoldBuilder unfinishedDefinition = PartOnHoldBuilder.create(this, dynamicValue);
    return unfinishedDefinition;
  }

  @Override
  public StringerBuilder with(Supplier<?>... dynamicValues) {
    StringerBuilder builder = this;
    for (Supplier<?> dynamicValue : dynamicValues) {
      builder = builder.with(dynamicValue).dynamic();
    }
    return builder;
  }

  @Override
  public PartialDefinitionBuilder withProperty(String propertyName, Supplier<?> propertyValue) {
    this.with(propertyName);
    this.with(this.configuration.getPropertyNameToValueSeparator());
    return this.with(propertyValue);
  }

  @Override
  public PartialDefinitionBuilder andProperty(String propertyName, Supplier<?> propertyValue) {
    this.with(configuration.getPropertySeparator());
    return this.withProperty(propertyName, propertyValue);
  }

  @Override
  public StringerBuilder enclosingIn(String prefix, String suffix, Function<StringerBuilder, StringerBuilder> definition) {
    StringerBuilder firstBuilder = this.with(prefix);
    StringerBuilder secondBuilder = definition.apply(firstBuilder);
    return secondBuilder.with(suffix);
  }

  @Override
  public StringerBuilder enclosingAsState(Function<StringerBuilder, StringerBuilder> definition) {
    return enclosingIn(this.configuration.getStatePrefix(), this.configuration.getStateSuffix(), definition);
  }

  @Override
  public StringerBuilder representing(Object representable) {
    return this.with(representable.getClass().getSimpleName())
      .enclosingAsState((builder) -> {
        List<ObjectField> properties = ObjectFieldExtractor.create(representable).extractFields();
        return this.representProperties(properties, builder);
      });
  }

  private StringerBuilder representProperties(List<ObjectField> properties, StringerBuilder builder) {
    if(properties.isEmpty()){
      return builder;
    }
    ObjectField firstProperty = properties.get(0);
    PartialDefinitionBuilder currentBuilder = builder.withProperty(firstProperty.getName(), firstProperty::getValue);

    for (int i = 1; i < properties.size(); i++) {
      ObjectField additionalProperty = properties.get(i);
      currentBuilder = currentBuilder.andProperty(additionalProperty.getName(), additionalProperty::getValue);
    }
    return currentBuilder;
  }

}
