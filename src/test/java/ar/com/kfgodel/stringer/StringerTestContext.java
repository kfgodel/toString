package ar.com.kfgodel.stringer;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.api.builder.StringerBuilder;
import ar.com.kfgodel.stringer.api.config.StringerConfiguration;
import ar.com.kfgodel.stringer.impl.*;
import ar.com.kfgodel.stringer.samples.ExamplePojo;

import java.util.List;
import java.util.function.Supplier;

/**
 * This interface defines the possible context variables to use on tests
 * Date: 15/03/18 - 19:49
 */
public interface StringerTestContext extends TestContext {
  ImmutableRepresentationStringer immutableStringer();
  void immutableStringer(Supplier<ImmutableRepresentationStringer> definition);

  String representation();
  void representation(Supplier<String> definition);

  DynamicRepresentationStringer dynamicStringer();
  void dynamicStringer(Supplier<DynamicRepresentationStringer> definition);

  Supplier<String> supplier();
  void supplier(Supplier<Supplier<String>> definition);

  LazyRepresentationStringer lazyStringer();
  void lazyStringer(Supplier<LazyRepresentationStringer> definition);

  StringerConfiguration configuration();
  void configuration(Supplier<StringerConfiguration> definition);

  CompositeRepresentationStringer compositeStringer();
  void compositeStringer(Supplier<CompositeRepresentationStringer> definition);

  StringerBuilder builder();
  void builder(Supplier<StringerBuilder> definition);

  EmptyRepresentationStringer emptyStringer();
  void emptyStringer(Supplier<EmptyRepresentationStringer> definition);

  Stringer stringer();
  void stringer(Supplier<Stringer> definition);

  ExamplePojo pojo();
  void pojo(Supplier<ExamplePojo> definition);

  List<Stringer> stringers();
  void stringers(Supplier<List<Stringer>> definition);

}
