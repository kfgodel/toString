package ar.com.kfgodel.stringer.api.builder;

import ar.com.kfgodel.stringer.api.Stringer;

/**
 * This interfaz defines the available builder methods to create a stringer instance to represent
 * another object
 *
 * Date: 17/03/18 - 22:19
 */
public interface StringerBuilder {
  /**
   * Creates the stringer instance according to the definitions indicated in this instance
   * @return A new stringer instance configured
   */
  Stringer build();
}
