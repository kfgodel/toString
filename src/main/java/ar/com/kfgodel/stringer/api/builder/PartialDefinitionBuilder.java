package ar.com.kfgodel.stringer.api.builder;

/**
 * This type represents the partial definition of a property, with posibility to add extra definition or do something else
 * Date: 31/03/18 - 12:16
 */
public interface PartialDefinitionBuilder extends StringerBuilder {
  /**
   * Modifies the last dynamic value so it's cached the first time and never evaluated again
   * @return The builder to keep adding definitions
   */
  StringerBuilder cacheable();

  /**
   * Uses default behavior for las dynamic value
   * @return The builder
   */
  StringerBuilder dynamic();
}
