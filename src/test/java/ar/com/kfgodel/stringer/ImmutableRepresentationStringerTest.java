package ar.com.kfgodel.stringer;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.stringer.impl.ImmutableRepresentationStringer;
import ar.com.kfgodel.stringer.impl.config.DefaultStringerConfiguration;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies the basic bahavior of an immutable stringer
 * Date: 15/03/18 - 19:50
 */
@RunWith(JavaSpecRunner.class)
public class ImmutableRepresentationStringerTest extends JavaSpec<StringerTestContext> {
  @Override
  public void define() {
    describe("an immutable representation stringer", () -> {
      context().immutableStringer(() -> ImmutableRepresentationStringer.create(context().representation()));

      it("returns the given representation", () -> {
        String representation = "Hola there";
        context().representation(() -> representation);
        assertThat(context().immutableStringer().get()).isSameAs(representation);
      });

      it("returns 'null' if null is used to create it", () -> {
        ImmutableRepresentationStringer stringer = ImmutableRepresentationStringer.create(null);
        assertThat(stringer.get()).isEqualTo("null");
      });

      describe("when a custom config is used", () -> {
        context().configuration(()-> DefaultStringerConfiguration.create()
          .usingForNullValues(()-> "a null value")
        );
        it("allows for a custom null representation", () -> {
          ImmutableRepresentationStringer stringer = ImmutableRepresentationStringer.create(null, context().configuration());
          assertThat(stringer.get()).isEqualTo("a null value");
        });

      });

    });

  }
}