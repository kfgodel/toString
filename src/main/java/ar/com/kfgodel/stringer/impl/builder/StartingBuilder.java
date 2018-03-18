package ar.com.kfgodel.stringer.impl.builder;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.builder.StringerBuilder;

/**
 * This class implementes the starting object to define a stringer
 * Date: 17/03/18 - 22:21
 */
public class StartingBuilder implements StringerBuilder {

  public static StartingBuilder create() {
    StartingBuilder builder = new StartingBuilder();
    return builder;
  }

  @Override
  public Stringer build() {
    return null;
  }
}
