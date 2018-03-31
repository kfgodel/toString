package ar.com.kfgodel.stringer.impl.optimizer;

import ar.com.kfgodel.stringer.api.Stringer;

import java.util.List;

/**
 * Date: 31/03/18 - 15:59
 */
public class PartsOptimizer {

  private List<Stringer> unoptimized;

  public static PartsOptimizer create(List<Stringer> parts) {
    PartsOptimizer optimizer = new PartsOptimizer();
    optimizer.unoptimized = parts;
    return optimizer;
  }

  public List<Stringer> optimize() {
    return unoptimized;
  }
}
