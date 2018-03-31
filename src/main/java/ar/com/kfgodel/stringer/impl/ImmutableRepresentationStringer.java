package ar.com.kfgodel.stringer.impl;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.config.DefaultStringerConfiguration;

/**
 * This class implements the stringer for a value that stays the same during the whole application lifetime.<br>
 *   That means that the representation can be constructed once, and then cached forever
 *
 * Date: 15/03/18 - 19:54
 */
public class ImmutableRepresentationStringer implements Stringer {
  private String representation;
  private StringerConfiguration config;

  @Override
  public String get() {
    return representation;
  }

  /**
   * Creates an instance configured with a default configuration
   */
  public static ImmutableRepresentationStringer create(Object value) {
    return create(value, DefaultStringerConfiguration.create());
  }


  public static ImmutableRepresentationStringer create(Object value, StringerConfiguration config) {
    ImmutableRepresentationStringer stringer = new ImmutableRepresentationStringer();
    stringer.representation = config.convertToString(value);
    stringer.config = config;
    return stringer;
  }

}
