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

  private Stringer delegate;

  @Override
  public String get() {
    return delegate.get();
  }

  /**
   * Creates an instance with the default configuration
   */
  public static LazyRepresentationStringer create(Supplier<?> valueSupplier) {
    return create(valueSupplier, DefaultStringerConfiguration.create());
  }


  public static LazyRepresentationStringer create(Supplier<?> valueSupplier, StringerConfiguration configuration) {
    LazyRepresentationStringer stringer = new LazyRepresentationStringer();
    stringer.initialize(DynamicRepresentationStringer.create(valueSupplier, configuration));
    return stringer;
  }

  private void initialize(Stringer initialStringer) {
    // We use a self replacing instance so we can remove any overhead required for the lazy part (after the first call)
    this.delegate = new SelfReplacingStringer(initialStringer);
  }

  /**
   * This instance is first used and replaces itself as lazy delegate to use an immutable instance
   * once the representation is defined by the dynamic
   */
  private class SelfReplacingStringer implements Stringer {

    private final Stringer stringer;

    public SelfReplacingStringer(Stringer initialStringer) {
      this.stringer = initialStringer;
    }

    @Override
    public String get() {
      String firstRepresentation = stringer.get();
      delegate = ImmutableRepresentationStringer.create(firstRepresentation);
      return firstRepresentation;
    }

  }

}
