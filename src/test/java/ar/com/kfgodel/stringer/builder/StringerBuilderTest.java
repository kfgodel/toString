package ar.com.kfgodel.stringer.builder;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.stringer.StringerTestContext;
import ar.com.kfgodel.stringer.api.Stringer;
import ar.com.kfgodel.stringer.impl.EmptyRepresentationStringer;
import ar.com.kfgodel.stringer.impl.builder.MutableBuilder;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 18/03/18 - 15:42
 */
@RunWith(JavaSpecRunner.class)
public class StringerBuilderTest extends JavaSpec<StringerTestContext> {
  @Override
  public void define() {
    describe("a stringer builder", () -> {
      context().builder(MutableBuilder::createDefault);

      it("allows configuring the representation content", () -> {
        Stringer stringer = context().builder()
          .with("something")
          .with(" and ")
          .with("something else")
          .build();
        assertThat(stringer.get()).isEqualTo("something and something else");
      });

      it("can receive multiple content parts at the same time", () -> {
        Stringer stringer = context().builder()
          .with("something", " and ", "something else")
          .build();
        assertThat(stringer.get()).isEqualTo("something and something else");
      });

      it("builds an empty stringer if no configuration specified", () -> {
        Stringer stringer = context().builder().build();
        assertThat(stringer).isInstanceOf(EmptyRepresentationStringer.class);
      });

      it("can be used with non-String values", () -> {
        Stringer stringer = context().builder()
          .with(10)
          .with('a')
          .with(4.0)
          .with(false)
          .build();
        assertThat(stringer.get()).isEqualTo("10a4.0false");
      });

      describe("when using Suppliers as values", () -> {

        it("creates a dynamic stringer for the supplier part", () -> {
          Stringer stringer = context().builder()
            .with(() -> "a dynamic part")
            .build();
          assertThat(stringer.get()).isEqualTo("a dynamic part");
        });

        it("can receive multiple suppliers at the same time", () -> {
          Stringer stringer = context().builder()
            .with(() -> "a dynamic part", () -> " and other part")
            .build();
          assertThat(stringer.get()).isEqualTo("a dynamic part and other part");
        });

        it("allows changing the value on each representation", () -> {
          AtomicInteger number = new AtomicInteger(1);

          Stringer stringer = context().builder()
            .with("representation N°")
            .with(() -> String.valueOf(number.getAndIncrement()))
            .build();
          assertThat(stringer.get()).isEqualTo("representation N°1");
          assertThat(stringer.get()).isEqualTo("representation N°2");
        });

        it("allows caching the result the first time", () -> {
          AtomicInteger number = new AtomicInteger(1);

          Stringer stringer = context().builder()
            .with("representation N°")
            .with(() -> String.valueOf(number.getAndIncrement())).cacheable()
            .build();
          assertThat(stringer.get()).isEqualTo("representation N°1");
          assertThat(stringer.get()).isEqualTo("representation N°1");
        });
      });

    });

  }
}