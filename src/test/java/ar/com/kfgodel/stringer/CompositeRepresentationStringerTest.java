package ar.com.kfgodel.stringer;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.stringer.impl.CompositeRepresentationStringer;
import ar.com.kfgodel.stringer.impl.ImmutableRepresentationStringer;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 17/03/18 - 11:18
 */
@RunWith(JavaSpecRunner.class)
public class CompositeRepresentationStringerTest extends JavaSpec<StringerTestContext> {
  @Override
  public void define() {
    describe("a composite stringer", () -> {
      context().compositeStringer(CompositeRepresentationStringer::create);

      it("returns an empty string when no stringer part is defined", () -> {
        assertThat(context().compositeStringer().get()).isEqualTo("");
      });

      describe("when a part is added", () -> {
        beforeEach(() -> {
          context().compositeStringer().addPart(ImmutableRepresentationStringer.create("Part1"));
        });

        it("uses the stringer part as return", () -> {
          assertThat(context().compositeStringer().get()).isEqualTo("Part1");
        });

        describe("when another part is added", () -> {
          beforeEach(() -> {
            context().compositeStringer().addPart(ImmutableRepresentationStringer.create("Part2"));
          });

          it("concatenates each part after the previous", () -> {
            assertThat(context().compositeStringer().get()).isEqualTo("Part1Part2");
          });

        });
        ;
      });

    });
  }
}