package ar.com.kfgodel.stringer.impl;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.config.DefaultStringerConfiguration;

import java.util.function.Supplier;

/**
 * This stringer constructs the representation once, but it delays its construction until its needed
 * Date: 15/03/18 - 21:43
 */
public class LazyRepresentationStringer implements Stringer {

  private DynamicRepresentationStringer representationSupplier;
  private String representation;

  @Override
  public String get() {
    if(representation == null){
      this.representation = representationSupplier.get();
      this.representationSupplier = null; // Release reference if not needed
    }
    return representation;
  }

  /**
   * Creates an instance with the default configuration
   */
  public static LazyRepresentationStringer create(Supplier<String> representationSupplier) {
    return create(representationSupplier, DefaultStringerConfiguration.create());
  }


  public static LazyRepresentationStringer create(Supplier<String> representationSupplier, StringerConfiguration configuration) {
    LazyRepresentationStringer stringer = new LazyRepresentationStringer();
    stringer.representationSupplier = DynamicRepresentationStringer.create(representationSupplier, configuration);
    stringer.representation = null;
    return stringer;
  }

}
