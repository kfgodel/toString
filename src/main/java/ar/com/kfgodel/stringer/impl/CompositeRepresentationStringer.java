package ar.com.kfgodel.stringer.impl;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.config.DefaultStringerConfiguration;

import java.util.List;
import java.util.stream.Collectors;

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
    return parts.stream()
      .map(Stringer::get)
      .collect(Collectors.joining());
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
