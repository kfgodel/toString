package ar.com.kfgodel.stringer.impl.config;

import ar.com.kfgodel.stringer.api.config.StringerConfiguration;

/**
 * This class represents a mutable configuration with sensible defaults
 * Date: 15/03/18 - 21:52
 */
public class DefaultStringerConfiguration implements StringerConfiguration {

  public static DefaultStringerConfiguration create() {
    DefaultStringerConfiguration configuration = new DefaultStringerConfiguration();
    return configuration;
  }

}
