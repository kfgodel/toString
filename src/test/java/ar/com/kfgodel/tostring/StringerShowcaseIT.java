package ar.com.kfgodel.tostring;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.kfgodel.tostring.impl.StringerImpl;
import org.junit.runner.RunWith;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type shows typical use cases for toString
 * Created by kfgodel on 09/09/14.
 */
@RunWith(JavaSpecRunner.class)
public class StringerShowcaseIT extends JavaSpec<StringerTestContext> {
    @Override
    public void define() {

        describe("for primitive values", ()->{

            it("null is represented as 'null'", ()->{
                assertThat(Stringer.asString(null)).isEqualTo("null");
            });
            it("integers are represented with digits", ()->{
                assertThat(Stringer.asString(1)).isEqualTo("1");
                assertThat(Stringer.asString(-3L)).isEqualTo("-3");
            });

            it("strings are surrounded by double quotes", ()->{
                assertThat(Stringer.asString("Hello World")).isEqualTo("\"Hello World\"");
            });

            it("decimal point numbers use default locale", ()->{
                assertThat(Stringer.asString(1.0)).isEqualTo(String.valueOf(1.0));
                assertThat(Stringer.asString(-2000.0)).isEqualTo(String.valueOf(-2000.0));
            });

        });

        describe("for arrays/collections", ()->{
            it("size is indicated as a prefix", ()->{
                int[] intArray = new int[]{1,2,3,4};
                assertThat(Stringer.asString(intArray)).isEqualTo("4:[1,2,3,4]");
            });

            describe("with few elements", ()->{
                it("the entire array content is represented", ()->{
                    String[] stringArray = new String[]{"a", "b", "c"};
                    assertThat(Stringer.asString(stringArray)).isEqualTo("3:[\"a\",\"b\",\"c\"]");
                });
                it("the entire list content is represented", () -> {
                    List<Long> list = new ArrayList<>();
                    list.add(3L);
                    list.add(4L);
                    list.add(5L);
                    list.add(6L);
                    assertThat(Stringer.asString(list)).isEqualTo("4:[3,4,5,6]");
                });
                it("the entire map content is represented", () -> {
                    Map<String, Double> map = new TreeMap<>();
                    map.put("a",2.0);
                    map.put("b",6.0);
                    map.put("c",1.0);
                    assertThat(Stringer.asString(map)).isEqualTo("3:{\"a\":2.0,\"b\":6.0,\"c\":1.0}");
                });
            });

            describe("with many elements or representation too long", ()->{
                it("the array content is truncated with an ellipsis");
                it("the list content is truncated with an ellipsis");
                it("the map content is truncated with an ellipsis");
            });
        });

        describe("for typical objects", ()->{

            it("the short class name is used as prefix");

            describe("without an id field", ()->{
                it("Object instance hashcode is used as discriminator value");
            });

            describe("with an id field", ()->{
                it("id value is used as discriminator if present");
                it("Object instance hashcode is used as discriminator if id is null");
            });

            it("object fields are represented like a JSON string");

            describe("with too many properties", ()->{
                it("objects fields are truncated with an ellipsis");
                it("null fields are omitted");
            });
        });

        describe("for objects with cyclic references", ()->{
            it("an integer is assigned to identify first representation");
            it("a call to first representation is used in the other places");
            it("detection works on collections");
            it("and circular references");
        });
    }
}
