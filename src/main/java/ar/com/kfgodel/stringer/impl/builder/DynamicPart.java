package ar.com.kfgodel.stringer.impl.builder;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.DynamicRepresentationStringer;

import java.util.function.Supplier;

/**
 * This class represents a part of a builder that holds a dynaic value
 * Date: 31/03/18 - 11:43
 */
public class DynamicPart implements BuilderPart {

  private Supplier<?> dynamicValue;
  private StringerConfiguration configuration;

  public static DynamicPart create(Supplier<?> dynamicValue, StringerConfiguration configuration) {
    DynamicPart part = new DynamicPart();
    part.dynamicValue = dynamicValue;
    part.configuration = configuration;
    return part;
  }

  @Override
  public Stringer createStringer() {
    Supplier<String> dynamicConversion = () -> configuration.convertToString(dynamicValue.get());
    DynamicRepresentationStringer stringer = DynamicRepresentationStringer.create(dynamicConversion);
    return stringer;
  }
}
