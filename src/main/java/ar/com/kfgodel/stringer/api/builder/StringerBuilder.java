package ar.com.kfgodel.stringer.api.builder;

import ar.com.kfgodel.stringer.api.Stringer;

import java.util.function.Supplier;

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

  /**
   * Adds an immutable value to the representation generated by the stringer created by this
   * builder
   * @param immutableValue The value to include in the representation
   * @return The modified builder to chain calls
   */
  StringerBuilder with(Object immutableValue);

  /**
   * Adds multiple immutable values to the representation
   * @param immutableValues The values to concatenate
   * @return The modified builder
   */
  StringerBuilder with(Object... immutableValues);

  /**
   * Adds a dynamic piece to the representation to be evaluated each time the stringer is used
   * @param dynamicValue The lambda to call for the dynamically generated piece of representation
   * @return The modified builder
   */
  StringerBuilder with(Supplier<?> dynamicValue);

  /**
   * Adds multiple dynamic pieces to the representation to be evaluated each time the stringer is used
   * @param dynamicValues The suppliers for dynamic values
   * @return The modified builder
   */
  StringerBuilder with(Supplier<?>... dynamicValues);

  /**
   * Adds two parts to this builder as a property. Where the value can change over time.<br>
   *   The property name to value separator string is used to separate name from value
   * @param propertyName The name of the property
   * @param propertyValue The value
   * @return This instance
   */
  StringerBuilder withProperty(String propertyName, Supplier<?> propertyValue);
}
