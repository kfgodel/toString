package ar.com.kfgodel.stringer.impl.builder;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.ImmutableRepresentationStringer;

/**
 * This class represents the specification of a builder part from the user building a stringer.<br>
 * Being immutable, a part can be optimized to avoid re-calculation everytime
 * Date: 31/03/18 - 11:40
 */
public class ImmutablePart implements BuilderPart {

  private Object value;
  private StringerConfiguration configuration;

  public static ImmutablePart create(Object value, StringerConfiguration configuration) {
    ImmutablePart part = new ImmutablePart();
    part.value = value;
    part.configuration = configuration;
    return part;
  }

  @Override
  public Stringer createStringer() {
    String representation = configuration.convertToString(value);
    ImmutableRepresentationStringer stringer = ImmutableRepresentationStringer.create(representation);
    return stringer;
  }
}
