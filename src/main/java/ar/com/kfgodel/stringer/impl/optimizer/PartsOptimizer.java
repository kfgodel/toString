package ar.com.kfgodel.stringer.impl.optimizer;

import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.ImmutableRepresentationStringer;
import ar.com.kfgodel.stringer.impl.LazyRepresentationStringer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * This class represents the object that tries to reduce the number of calculated parts for a stringer
 * Date: 31/03/18 - 15:59
 */
public class PartsOptimizer {

  private List<Stringer> optimized;
  private StringerConfiguration configuration;

  public static PartsOptimizer create(List<Stringer> parts, StringerConfiguration configuration) {
    PartsOptimizer optimizer = new PartsOptimizer();
    optimizer.optimized = new ArrayList<>(parts);
    optimizer.configuration = configuration;
    return optimizer;
  }

  public List<Stringer> optimize() {
    mergeAlike();
    return optimized;
  }

  private void mergeAlike() {
    for (int currentIndex = 0, nextIndex = 1; nextIndex < optimized.size();) {
      Stringer current = optimized.get(currentIndex);
      Stringer next = optimized.get(nextIndex);
      Optional<Stringer> merged = tryToMerge(current, next);
      if(merged.isPresent()){
        Stringer replacement = merged.get();
        optimized.set(currentIndex, replacement);
        optimized.remove(nextIndex);
      }else{
        currentIndex++;
        nextIndex = currentIndex+1;
      }
    }
  }

  private Optional<Stringer> tryToMerge(Stringer current, Stringer next) {
    if(current.isConstant() && next.isConstant()){
      String concatenated = current.get() + next.get();
      Stringer concatenatedStringer = ImmutableRepresentationStringer.create(concatenated, configuration);
      return Optional.of(concatenatedStringer);
    }
    if(current.isCacheable() && next.isCacheable()){
      Supplier<?> lazyConcatenation = ()-> current.get() + next.get();
      Stringer lazyContatenationStringer = LazyRepresentationStringer.create(lazyConcatenation, configuration);
      return Optional.of(lazyContatenationStringer);
    }
    return Optional.empty();
  }

}
