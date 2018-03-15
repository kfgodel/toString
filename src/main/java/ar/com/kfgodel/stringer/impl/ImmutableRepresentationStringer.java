package ar.com.kfgodel.stringer.impl;

import ar.com.kfgodel.stringer.api.Stringer;

/**
 * This class implements the stringer for a value that stays the same during the whole application lifetime.<br>
 *   That means that the representation can be constructed once, and then cached forever
 *
 * Date: 15/03/18 - 19:54
 */
public class ImmutableRepresentationStringer implements Stringer {
  private String representation;

  @Override
  public String get() {
    return representation;
  }

  public static ImmutableRepresentationStringer create(String representation) {
    ImmutableRepresentationStringer stringer = new ImmutableRepresentationStringer();
    stringer.representation = fixRepresentation(representation);
    return stringer;
  }

  /**
   * Forces the given value to be a string, even if it's null.<br>
   *   A 'null' string is used if null is passed
   * @param representation The value to fix
   * @return A valid string representation
   */
  public static String fixRepresentation(String representation){
    return representation == null ? "null" : representation;
  }

}
