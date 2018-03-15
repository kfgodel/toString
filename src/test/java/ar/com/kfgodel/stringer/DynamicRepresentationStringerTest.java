package ar.com.kfgodel.stringer;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.stringer.impl.DynamicRepresentationStringer;
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

      it("evaluates the given supplier to get the representation", () -> {
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

        assertThat(context().dynamicStringer().get()).isEqualTo("Exception evaluating stringer: Ka-Boom!\n" +
          "ar.com.kfgodel.stringer.DynamicRepresentationStringerTest.lambda$null$7(DynamicRepresentationStringerTest.java:40)\n" +
          "ar.com.kfgodel.stringer.impl.DynamicRepresentationStringer.get(DynamicRepresentationStringer.java:22)\n" +
          "ar.com.kfgodel.stringer.DynamicRepresentationStringerTest.lambda$null$9(DynamicRepresentationStringerTest.java:42)");
      });

      it("returns 'null' if null is returned by supplier",()->{
        context().supplier(() -> () -> null);

        assertThat(context().dynamicStringer().get()).isEqualTo("null");
      });

    });

  }
}