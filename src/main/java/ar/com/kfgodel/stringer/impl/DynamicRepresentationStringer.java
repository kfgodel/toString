package ar.com.kfgodel.stringer.impl;

import ar.com.kfgodel.stringer.api.Stringer;

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

  @Override
  public String get() {
    try {
      String representation = representationSupplier.get();
      return ImmutableRepresentationStringer.fixRepresentation(representation);
    } catch (Exception e) {
      String stackPreview = Arrays.stream(e.getStackTrace())
        .limit(MAX_STACK_PREVIEW_SIZE)
        .map(Object::toString)
        .collect(Collectors.joining("\n"));
      return "Exception evaluating stringer: " + e.getMessage() + "\n" + stackPreview;
    }
  }

  public static DynamicRepresentationStringer create(Supplier<String> representationSupplier) {
    DynamicRepresentationStringer stringer = new DynamicRepresentationStringer();
    stringer.representationSupplier = representationSupplier;
    return stringer;
  }

}
