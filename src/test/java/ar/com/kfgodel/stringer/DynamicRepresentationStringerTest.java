package ar.com.kfgodel.stringer;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.stringer.impl.DynamicRepresentationStringer;
import ar.com.kfgodel.stringer.impl.config.DefaultStringerConfiguration;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies the behavior of the dynamic stringer and its edge cases
 * <p>
 * Date: 15/03/18 - 20:13
 */
@RunWith(JavaSpecRunner.class)
public class DynamicRepresentationStringerTest extends JavaSpec<StringerTestContext> {
  @Override
  public void define() {
    describe("a dynamic representation stringer", () -> {
      context().dynamicStringer(() -> DynamicRepresentationStringer.create(context().supplier()));

      it("gets the representation from the supplier", () -> {
        String representation = "Hello World";
        context().supplier(() -> () -> representation);
        assertThat(context().dynamicStringer().get()).isSameAs(representation);
      });

      it("allows the supplier to change its value every time",()->{
        AtomicInteger integer = new AtomicInteger(1);
        context().supplier(() -> () -> String.valueOf(integer.getAndIncrement()));

        assertThat(context().dynamicStringer().get()).isEqualTo("1");
        assertThat(context().dynamicStringer().get()).isEqualTo("2");
        assertThat(context().dynamicStringer().get()).isEqualTo("3");
      });

      it("handles an exception on the supplier with a brief description",()->{
        context().supplier(() -> () -> {throw new RuntimeException("Ka-Boom!");});

        assertThat(context().dynamicStringer().get())
          .startsWith("Exception evaluating stringer: Ka-Boom!")
          .contains("ar.com.kfgodel.stringer.DynamicRepresentationStringerTest")
          .matches("(.+\\n){3}.+");// 4 lineas separadas por \n
      });

      it("returns 'null' if null is returned by supplier",()->{
        context().supplier(() -> () -> null);

        assertThat(context().dynamicStringer().get()).isEqualTo("null");
      });

      describe("when a custom config is used", () -> {
        context().configuration(()-> DefaultStringerConfiguration.create()
          .usingForNullValues(()-> "a null value")
          .limitingFailedRepresentationStackTo(0)
        );
        context().dynamicStringer(() -> DynamicRepresentationStringer.create(context().supplier(), context().configuration()));

        it("allows for a deeper or shorter stacktrace",()->{
          context().supplier(() -> () -> {throw new RuntimeException("Ka-Boom!");});

          assertThat(context().dynamicStringer().get()).isEqualTo("Exception evaluating stringer: Ka-Boom!\n");
        });

        it("allows for a custom null representation",()->{
          context().supplier(() -> () -> null);

          assertThat(context().dynamicStringer().get()).isEqualTo("a null value");
        });

      });

    });

  }
}