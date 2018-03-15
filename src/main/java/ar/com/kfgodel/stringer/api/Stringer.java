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
}
