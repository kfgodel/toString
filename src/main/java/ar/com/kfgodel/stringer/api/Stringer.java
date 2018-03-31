package ar.com.kfgodel.stringer.api;

import java.util.function.Supplier;

/**
 * This interface defines the basic contract for a stringer instance.<br>
 *   A stringer responsability is to be able to return a string representing the state
 *   or view of another instance.<br>
 *     <br>
 *   Depending on the mutability of that other instance a stringer can return different representations in different times
 * Date: 15/03/18 - 19:52
 */
public interface Stringer extends Supplier<String> {

  /**
   * Indicates if this stringer representation is a constant value (doesn't need to be computed)
   * @return true if this stringer representation is a known value
   */
  default boolean isConstant(){
    return false;
  }

  /**
   * Indicates if this stringer representation can be cached once calculated
   * @return false if this stringer representation can change over time
   */
  default boolean isCacheable(){
    return false;
  }
}
