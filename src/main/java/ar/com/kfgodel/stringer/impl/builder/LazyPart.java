package ar.com.kfgodel.stringer.impl.builder;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.LazyRepresentationStringer;

import java.util.function.Supplier;

/**
 * This
 * Date: 31/03/18 - 12:38
 */
public class LazyPart implements BuilderPart {
  private Supplier<?> dynamicValue;
  private StringerConfiguration configuration;

  @Override
  public Stringer createStringer() {
    Supplier<String> dynamicRepresentation = ()-> configuration.convertToString(dynamicValue.get());
    return LazyRepresentationStringer.create(dynamicRepresentation, configuration);
  }

  public static LazyPart create(Supplier<?> dynamicValue, StringerConfiguration configuration) {
    LazyPart part = new LazyPart();
    part.dynamicValue = dynamicValue;
    part.configuration = configuration;
    return part;
  }

}
