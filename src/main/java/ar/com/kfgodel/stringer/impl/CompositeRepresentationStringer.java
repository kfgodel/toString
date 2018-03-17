package ar.com.kfgodel.stringer.impl;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.config.DefaultStringerConfiguration;

import java.util.ArrayList;
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
   */
  public static CompositeRepresentationStringer create() {
    return create(DefaultStringerConfiguration.create());
  }

  public static CompositeRepresentationStringer create(StringerConfiguration configuration) {
    CompositeRepresentationStringer stringer = new CompositeRepresentationStringer();
    stringer.configuration = configuration;
    stringer.parts = new ArrayList<>();
    return stringer;
  }

  @Override
  public String get() {
    StringBuilder builder = new StringBuilder();
    parts.stream()
      .map(Stringer::get)
      .forEach(builder::append);
    return builder.toString();
  }

  public CompositeRepresentationStringer addPart(Stringer part) {
    parts.add(part);
    return this;
  }
}
