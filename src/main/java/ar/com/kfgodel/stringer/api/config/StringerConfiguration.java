package ar.com.kfgodel.stringer.api.config;

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
}
