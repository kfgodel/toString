package ar.com.kfgodel.stringer.builder;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.stringer.StringerTestContext;
import ar.com.kfgodel.stringer.impl.builder.MutableBuilder;
import ar.com.kfgodel.stringer.samples.ExamplePojo;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 30/03/18 - 14:59
 */
@RunWith(JavaSpecRunner.class)
public class ExamplePojoRepresentationTest extends JavaSpec<StringerTestContext> {
  @Override
  public void define() {
    describe("a simple pojo represented with stringer", () -> {
      context().pojo(ExamplePojo::createDefault);

      describe("when fine grained control is needed", () -> {
        context().stringer(() -> MutableBuilder.createDefault()
          .with(context().pojo().getClass().getSimpleName())
          .with("{")
          .with(ExamplePojo.id_FIELD, ": ").with(context().pojo()::getId).with(", ")
          .with(ExamplePojo.name_FIELD, ": \"").with(context().pojo()::getName).with("\", ")
          .with(ExamplePojo.age_FIELD, ": ").with(context().pojo()::getAge).with(", ")
          .with(ExamplePojo.telephone_FIELD, ": ").with(context().pojo()::getTelephone)
          .with("}")
          .build()
        );

        it("allows creating a fully custom representation", () -> {
          assertThat(context().stringer().get()).isEqualTo("ExamplePojo{id: 1, name: \"Pepe\", age: 24, telephone: 4544-464}");
        });

        it("allows the representation to change according to pojo state", () -> {
          context().pojo().setAge(34);
          context().pojo().setTelephone("123-456");
          assertThat(context().stringer().get()).isEqualTo("ExamplePojo{id: 1, name: \"Pepe\", age: 34, telephone: 123-456}");
        });
      });

      describe("using properties to build the representation", () -> {
        context().stringer(() -> MutableBuilder.createDefault()
          .with(context().pojo().getClass().getSimpleName())
          .with("{")
          .withProperty(ExamplePojo.id_FIELD, context().pojo()::getId).with(", ")
          .withProperty(ExamplePojo.name_FIELD, context().pojo()::getName).with(", ")
          .withProperty(ExamplePojo.age_FIELD, context().pojo()::getAge).with(", ")
          .withProperty(ExamplePojo.telephone_FIELD, context().pojo()::getTelephone)
          .with("}")
          .build()
        );

        it("allows creating a fully custom representation", () -> {
          assertThat(context().stringer().get()).isEqualTo("ExamplePojo{id: 1, name: Pepe, age: 24, telephone: 4544-464}");
        });

        it("allows the representation to change according to pojo state", () -> {
          context().pojo().setAge(34);
          context().pojo().setTelephone("123-456");
          assertThat(context().stringer().get()).isEqualTo("ExamplePojo{id: 1, name: Pepe, age: 34, telephone: 123-456}");
        });
      });


    });

  }
}