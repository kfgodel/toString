package ar.com.kfgodel.stringer.builder;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.stringer.StringerTestContext;
import ar.com.kfgodel.stringer.impl.builder.MutableBuilder;
import ar.com.kfgodel.stringer.samples.ExamplePojo;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 31/03/18 - 11:57
 */
@RunWith(JavaSpecRunner.class)
public class PropertyEvaluationTest extends JavaSpec<StringerTestContext> {
  @Override
  public void define() {
    describe("a stringer builder", () -> {
      context().builder(MutableBuilder::createDefault);

      describe("when representing an object", () -> {
        context().pojo(ExamplePojo::createDefault);

        describe("by default", () -> {
          context().stringer(() -> context().builder()
            .withProperty(ExamplePojo.age_FIELD, context().pojo()::getAge)
            .build()
          );

          it("calculates the property value on each representation", () -> {
            String firstValue = context().stringer().get();
            assertThat(firstValue).isEqualTo("age: 24");

            context().pojo().setAge(25);
            String secondValue = context().stringer().get();
            assertThat(secondValue).isEqualTo("age: 25");
          });
        });

        describe("when caching the property", () -> {
          context().stringer(() -> context().builder()
            .withProperty(ExamplePojo.age_FIELD, context().pojo()::getAge).cacheable()
            .build()
          );

          it("calculates the property value only the first time", () -> {
            context().pojo().setAge(26);
            String firstValue = context().stringer().get();
            assertThat(firstValue).isEqualTo("age: 26");

            context().pojo().setAge(27);
            String secondValue = context().stringer().get();
            assertThat(secondValue).isEqualTo("age: 26");
          });
        });

        describe("when a value is passed", () -> {
          context().stringer(() -> context().builder()
            .withProperty(ExamplePojo.age_FIELD, context().pojo().getAge())
            .build()
          );

          it("uses the given value", () -> {
            String firstValue = context().stringer().get();
            assertThat(firstValue).isEqualTo("age: 24");

            context().pojo().setAge(27);
            String secondValue = context().stringer().get();
            assertThat(secondValue).isEqualTo("age: 24");
          });
        });

      });

    });

  }
}