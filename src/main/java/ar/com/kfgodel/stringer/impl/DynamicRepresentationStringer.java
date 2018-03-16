package ar.com.kfgodel.stringer.impl;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.config.DefaultStringerConfiguration;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * This class represents the stringer instantance whose reprepresentation changes over time and it's not predictable.<br>
 * A representation construction is needed everytime the stringer is asked one.
 * Date: 15/03/18 - 20:11
 */
public class DynamicRepresentationStringer implements Stringer {
  public static final int MAX_STACK_PREVIEW_SIZE = 3;

  private Supplier<String> representationSupplier;
  private StringerConfiguration config;

  @Override
  public String get() {
    try {
      String representation = representationSupplier.get();
      return config.ensureNonNullRepresentation(representation);
    } catch (Exception e) {
      String stackPreview = Arrays.stream(e.getStackTrace())
        .limit(config.getExceptionStackLimit())
        .map(Object::toString)
        .collect(Collectors.joining("\n"));
      return "Exception evaluating stringer: " + e.getMessage() + "\n" + stackPreview;
    }
  }

  /**
   * Creates an instance with a default configuration
   */
  public static DynamicRepresentationStringer create(Supplier<String> representationSupplier) {
    return DynamicRepresentationStringer.create(representationSupplier, DefaultStringerConfiguration.create());
  }

  public static DynamicRepresentationStringer create(Supplier<String> representationSupplier, StringerConfiguration configuration) {
    DynamicRepresentationStringer stringer = new DynamicRepresentationStringer();
    stringer.representationSupplier = representationSupplier;
    stringer.config = configuration;
    return stringer;
  }

}
