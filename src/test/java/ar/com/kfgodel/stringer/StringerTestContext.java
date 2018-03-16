package ar.com.kfgodel.stringer;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.kfgodel.stringer.impl.DynamicRepresentationStringer;
import ar.com.kfgodel.stringer.impl.ImmutableRepresentationStringer;
import ar.com.kfgodel.stringer.impl.LazyRepresentationStringer;

import java.util.function.Supplier;

/**
 * This interface defines the possible context variables to use on tests
 * Date: 15/03/18 - 19:49
 */
public interface StringerTestContext extends TestContext {
  ImmutableRepresentationStringer immutableStringer();
  void immutableStringer(Supplier<ImmutableRepresentationStringer> definition);

  Object value();
  void value(Supplier<Object> definition);

  String representation();
  void representation(Supplier<String> definition);

  DynamicRepresentationStringer dynamicStringer();
  void dynamicStringer(Supplier<DynamicRepresentationStringer> definition);

  Supplier<String> supplier();
  void supplier(Supplier<Supplier<String>> definition);

  LazyRepresentationStringer lazyStringer();
  void lazyStringer(Supplier<LazyRepresentationStringer> definition);

}
