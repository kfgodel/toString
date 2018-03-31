package ar.com.kfgodel.stringer.impl.builder;

import ar.com.kfgodel.stringer.api.Stringer;

/**
 * This type defines the general contract for builder parts
 * Date: 31/03/18 - 11:41
 */
public interface BuilderPart {
  /**
   * Creates a stringer to represent this part
   */
  Stringer createStringer();
}
