package ar.com.kfgodel.stringer.impl;

import ar.com.kfgodel.stringer.api.Stringer;

/**
 * This class represents a base case for a stringer when no input is defined
 * for a representation
 *
 * Date: 19/03/18 - 23:34
 */
public class EmptyRepresentationStringer implements Stringer {

  @Override
  public String get() {
    return "";
  }

  @Override
  public boolean isConstant() {
    return true;
  }

  @Override
  public boolean isCacheable() {
    return true;
  }

  public static EmptyRepresentationStringer create() {
    EmptyRepresentationStringer stringer = new EmptyRepresentationStringer();
    return stringer;
  }

}
