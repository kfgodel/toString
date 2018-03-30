package ar.com.kfgodel.stringer;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.stringer.impl.EmptyRepresentationStringer;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 19/03/18 - 23:36
 */
@RunWith(JavaSpecRunner.class)
public class EmptyRepresentationStringerTest extends JavaSpec<StringerTestContext> {
  @Override
  public void define() {
    describe("an empty representation stringer", () -> {
      context().emptyStringer(() -> EmptyRepresentationStringer.create());

      it("returns an empty string as representation", () -> {
        assertThat(context().emptyStringer().get()).isEmpty();
      });
    });

  }
}