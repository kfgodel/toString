package ar.com.kfgodel.stringer;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.stringer.impl.LazyRepresentationStringer;
import ar.com.kfgodel.stringer.impl.config.DefaultStringerConfiguration;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies the behavior of the lazy stringer
 * Date: 15/03/18 - 21:47
 */
@RunWith(JavaSpecRunner.class)
public class LazyRepresentationStringerTest extends JavaSpec<StringerTestContext> {
  @Override
  public void define() {
    describe("a lazy representation stringer", () -> {
      context().lazyStringer(()-> LazyRepresentationStringer.create(context().supplier()));

      it("gets the representation from the supplier", () -> {
        String representation = "Hello World";
        context().supplier(() -> () -> representation);
        assertThat(context().lazyStringer().get()).isSameAs(representation);
      });

      it("caches the first representation and never uses the supplier again",()->{
        AtomicInteger integer = new AtomicInteger(1);
        context().supplier(() -> () -> String.valueOf(integer.getAndIncrement()));

        assertThat(context().lazyStringer().get()).isEqualTo("1");
        assertThat(context().lazyStringer().get()).isEqualTo("1");
        assertThat(context().lazyStringer().get()).isEqualTo("1");
      });

      it("handles an exception on the supplier with a brief description",()->{
        context().supplier(() -> () -> {throw new RuntimeException("Ka-Boom!");});

        assertThat(context().lazyStringer().get()).isEqualTo("Exception evaluating stringer: Ka-Boom!\n" +
          "ar.com.kfgodel.stringer.LazyRepresentationStringerTest.lambda$null$7(LazyRepresentationStringerTest.java:40)\n" +
          "ar.com.kfgodel.stringer.impl.DynamicRepresentationStringer.get(DynamicRepresentationStringer.java:25)\n" +
          "ar.com.kfgodel.stringer.impl.LazyRepresentationStringer.get(LazyRepresentationStringer.java:21)");
      });

      it("returns 'null' if null is returned by supplier",()->{
        context().supplier(() -> () -> null);

        assertThat(context().lazyStringer().get()).isEqualTo("null");
      });

      describe("when a custom config is used", () -> {
        context().configuration(()-> DefaultStringerConfiguration.create()
          .usingForNullValues(()-> "a null value")
          .limitingFailedRepresentationStackTo(0)
        );
        context().lazyStringer(()-> LazyRepresentationStringer.create(context().supplier(), context().configuration()));

        it("allows for a deeper or shorter stacktrace",()->{
          context().supplier(() -> () -> {throw new RuntimeException("Ka-Boom!");});

          assertThat(context().lazyStringer().get()).isEqualTo("Exception evaluating stringer: Ka-Boom!\n");
        });

        it("allows for a custom null representation",()->{
          context().supplier(() -> () -> null);

          assertThat(context().lazyStringer().get()).isEqualTo("a null value");
        });

      });
    });

  }
}