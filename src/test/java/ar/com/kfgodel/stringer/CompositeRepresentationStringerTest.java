package ar.com.kfgodel.stringer;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.stringer.impl.CompositeRepresentationStringer;
import ar.com.kfgodel.stringer.impl.ImmutableRepresentationStringer;
import org.assertj.core.util.Lists;
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
      context().compositeStringer(() -> CompositeRepresentationStringer.createDefault(context().stringers()));

      describe("created with no stringer parts", () -> {
        context().stringers(Lists::newArrayList);

        it("returns an empty string when no stringer part is defined", () -> {
          assertThat(context().compositeStringer().get()).isEqualTo("");
        });
      });

      describe("when a part is used", () -> {
        context().stringers(() -> Lists.newArrayList(ImmutableRepresentationStringer.create("Part1")));

        it("uses the stringer representation as result", () -> {
          assertThat(context().compositeStringer().get()).isEqualTo("Part1");
        });
      });

      describe("when more than one stringer is used", () -> {
        context().stringers(() -> Lists.newArrayList(ImmutableRepresentationStringer.create("Part1"), ImmutableRepresentationStringer.create("Part2")));

        it("concatenates each part representation", () -> {
          assertThat(context().compositeStringer().get()).isEqualTo("Part1Part2");
        });

      });
    });
  }
}