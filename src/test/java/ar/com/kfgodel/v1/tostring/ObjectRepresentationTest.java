package ar.com.kfgodel.v1.tostring;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.v1.tostring.testobjects.ClassWithNoFields;
import ar.com.kfgodel.v1.tostring.testobjects.OrderedFieldsObject;
import ar.com.kfgodel.v1.tostring.testobjects.OverridedHashcodeObject;
import ar.com.kfgodel.v1.tostring.testobjects.SubPerson;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type defines tests for object representations
 * Created by kfgodel on 10/09/14.
 */
@RunWith(JavaSpecRunner.class)
public class ObjectRepresentationTest extends JavaSpec<OldStringerTestContext> {
    @Override
    public void define() {
        describe("object discriminator", ()->{
            it("uses native hashcode even if class overrides it", ()->{
                OverridedHashcodeObject overridingObject = new OverridedHashcodeObject();
                String nativeHascode = Integer.toHexString(System.identityHashCode(overridingObject));
                assertThat(OldStringer.representationOf(overridingObject))
                        .contains("OverridedHashcodeObject«" + nativeHascode + "»");
            });
        });
        describe("object fields", ()->{
            it("are ordered in declaration order", ()->{
                OrderedFieldsObject object = new OrderedFieldsObject();
                assertThat(OldStringer.representationOf(object))
                        .contains("\"first\": 1, \"middle\": 2, \"last\": 3");
            });
            it("null values are included if small representation", ()->{
                OrderedFieldsObject object = new OrderedFieldsObject();
                object.setFirst(null);
                assertThat(OldStringer.representationOf(object))
                        .contains("null");
            });
            it("include inherited fields", ()-> {
                SubPerson person = new SubPerson();
                assertThat(OldStringer.representationOf(person))
                        .contains("ownField")
                        .contains("name")
                        .contains("age");
            });
        });
        describe("object body", ()->{
            it("is omitted if no fields", ()->{
                ClassWithNoFields object = new ClassWithNoFields();
                assertThat(OldStringer.representationOf(object))
                        .doesNotContain("{").doesNotContain("}");
            });
        });
    }
}
