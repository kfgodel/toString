package ar.com.kfgodel.stringer.impl;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.config.DefaultStringerConfiguration;

import java.util.List;

/**
 * This class represents a stringer that is made up of other stringers from which it generates
 * its representation
 * <p>
 * Date: 17/03/18 - 11:15
 */
public class CompositeRepresentationStringer implements Stringer {

  private StringerConfiguration configuration;
  private List<Stringer> parts;

  /**
   * Creates an instance with a default configuration
   * @param stringers
   */
  public static CompositeRepresentationStringer createDefault(List<Stringer> stringers) {
    return create(stringers, DefaultStringerConfiguration.create());
  }

  public static CompositeRepresentationStringer create(List<Stringer> parts, StringerConfiguration configuration) {
    CompositeRepresentationStringer stringer = new CompositeRepresentationStringer();
    stringer.configuration = configuration;
    stringer.parts = parts;
    return stringer;
  }

  @Override
  public String get() {
    StringBuilder builder = new StringBuilder(512);
    for (Stringer part : parts) {
      builder.append(part.get());
    }
    return builder.toString();
  }

  public CompositeRepresentationStringer addPart(Stringer part) {
    parts.add(part);
    return this;
  }

  @Override
  public boolean isConstant() {
    // True if it's empty
    return parts.stream().allMatch(Stringer::isConstant);
  }

  @Override
  public boolean isCacheable() {
    // True if it's empty
    return parts.stream().allMatch(Stringer::isCacheable);
  }
}
