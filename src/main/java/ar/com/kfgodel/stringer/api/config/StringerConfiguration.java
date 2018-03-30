package ar.com.kfgodel.stringer.api.config;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This interface defines the behavior parameters that are modifiable by a stringer user
 * Date: 15/03/18 - 21:51
 */
public interface StringerConfiguration {
  /**
   * Defines a supplier lambda to invoke for null value representation
   * @param nullRepresentationSupplier The lambda to invoke to get a representation for the value null
   * @return This instance
   */
  StringerConfiguration usingForNullValues(Supplier<String> nullRepresentationSupplier);

  /**
   * Defines the function to use when converting values into strings
   * @param stringConversion The conversion function to use
   * @return This instance
   */
  StringerConfiguration usingForStringConversion(Function<Object, String> stringConversion);

  /**
   * Modifies the given representation if it's null using this configuration representation supplier
   * @param representation The representation to fix
   * @return The same instance if not null, a null representing string otherwise
   */
  String ensureNonNullRepresentation(String representation);

  /**
   * Defines the number of stacktrace lines to include in error details when a representation fails to execute
   *  (a stringer that dependes on a supplier)
   * @param exceptionStackLevelsCount The number of stack levels to include
   * @return This instance
   */
  StringerConfiguration limitingFailedRepresentationStackTo(int exceptionStackLevelsCount);

  /**
   * The numebr of stack levels to include in representation when something fails to render the string
   */
  int getExceptionStackLimit();

  /**
   * Converts the given value to a representation string.<br>
   *   It uses this configuration state to get a representation out of the passed value
   * @param value The value to convert
   * @return A non-null string representing the value
   */
  String convertToString(Object value);

  /**
   * Defines the property name to value separator string
   * @param nameToValueSeparator The string to use when separating the name and the value of a property
   * @return This instance
   */
  StringerConfiguration usingAsPropertyNameToValueSeparator(String nameToValueSeparator);

  /**
   * The character or string that is used to separate a property name from its value
   * @return The separator
   */
  String getPropertyNameToValueSeparator();


  /**
   * Defines the separator to use when representing different properties together
   * @param propertySeparator The string to delimit properties
   * @return This instance
   */
  StringerConfiguration usingAsPropertySeparator(String propertySeparator);

  /**
   * The character or string used to separate different properties
   * @return The separator
   */
  String getPropertySeparator();

  /**
   * Defines the prefix to use when representing an object state
   * @param prefix The prefix part to use at the beggining of state
   * @return this instance
   */
  StringerConfiguration usingAsStatePrefix(String prefix);

  /**
   * The character or string used to delimit the start of the state representation for an object
   * @return The starting delimiter
   */
  String getStatePrefix();

  /**
   * Defines the suffix to use when representing an object state
   * @param suffix The suffix to add at the end of state
   * @return This instance
   */
  StringerConfiguration usingAsStateSuffix(String suffix);

  /**
   * The character or string used to delimit the end of the state representation for an object
   * @return The endinf delimiter
   */
  String getStateSuffix();
}
