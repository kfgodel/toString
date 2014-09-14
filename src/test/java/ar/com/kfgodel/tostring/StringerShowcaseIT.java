package ar.com.kfgodel.tostring;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.tostring.testobjects.*;
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
                assertThat(Stringer.representationOf(null))
                        .isEqualTo("null");
            });
            it("integers are represented with digits", ()->{
                assertThat(Stringer.representationOf(1))
                        .isEqualTo("1");
                assertThat(Stringer.representationOf(-3L))
                        .isEqualTo("-3");
            });

            it("strings are surrounded by double quotes", ()->{
                assertThat(Stringer.representationOf("Hello World"))
                        .isEqualTo("\"Hello World\"");
            });

            it("decimal point numbers use default locale", ()->{
                assertThat(Stringer.representationOf(1.0))
                        .isEqualTo(String.valueOf(1.0));
                assertThat(Stringer.representationOf(-2000.0))
                        .isEqualTo(String.valueOf(-2000.0));
            });

        });

        describe("for non primitive values", ()->{
            it("an ordinal number is prefixed to identify it", ()->{
                assertThat(Stringer.representationOf(new Object()))
                        .startsWith("1º");
            });
        });

        describe("for arrays/collections", ()->{
            it("size is indicated as a prefix", ()->{
                int[] intArray = new int[]{1,2,3,4};
                assertThat(Stringer.representationOf(intArray))
                        .isEqualTo("1º·4#[1, 2, 3, 4]");
            });

            describe("with few elements", ()->{
                it("the entire array content is represented", ()->{
                    String[] stringArray = new String[]{"a", "b", "c"};
                    assertThat(Stringer.representationOf(stringArray))
                            .isEqualTo("1º·3#[\"a\", \"b\", \"c\"]");
                });
                it("the entire list content is represented", () -> {
                    List<Long> list = new ArrayList<>();
                    list.add(3L);
                    list.add(4L);
                    list.add(5L);
                    list.add(6L);
                    assertThat(Stringer.representationOf(list))
                            .isEqualTo("1º·4#[3, 4, 5, 6]");
                });
                it("the entire map content is represented", () -> {
                    Map<String, Double> map = new TreeMap<>();
                    map.put("a",2.0);
                    map.put("b",6.0);
                    map.put("c",1.0);
                    assertThat(Stringer.representationOf(map))
                            .isEqualTo("1º·3#{\"a\": 2.0, \"b\": 6.0, \"c\": 1.0}");
                });
            });

            describe("with many elements or representation too long", ()->{
                it("the array content is truncated with an ellipsis", ()->{
                    String[] longStringArray = createLongArray();
                    assertThat(Stringer.representationOf(longStringArray))
                            .isEqualTo("1º·200#[\"a\", \"b\", \"c\", \"d\", \"e\", \"f\", \"g\", \"h\", \"i\", \"j\", \"k\", \"l\", ...]");
                });
                it("the list content is truncated with an ellipsis", ()->{
                    List<Long> longList = createLongList();
                    assertThat(Stringer.representationOf(longList))
                            .isEqualTo("1º·100#[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, ...]");
                });
                it("the map content is truncated with an ellipsis", ()->{
                    Map<Double, Integer> longMap = createLongMap();
                    assertThat(Stringer.representationOf(longMap))
                            .isEqualTo("1º·100#{0.0: 0, 1.0: 1, 2.0: 2, 3.0: 3, 4.0: 4, 5.0: 5, 6.0: 6, ...}");
                });
            });
        });

        describe("for typical objects", ()->{

            context().object(()-> new Object());

            it("the short class name is used as prefix", () -> {
                assertThat(Stringer.representationOf(context().object()))
                        .startsWith("1º·Object");
            });

            describe("without an id field", ()->{
                it("native Object hashcode is used as discriminator value", ()->{
                    Object object = context().object();
                    String objectHash = Integer.toHexString(object.hashCode());
                    assertThat(Stringer.representationOf(object)).endsWith("Object«"+objectHash +"»");
                });
            });

            describe("with an id field", ()->{
                context().object(()-> ClassWithId.create(23L));
                it("id value is used as discriminator if present", () -> {
                    assertThat(Stringer.representationOf(context().object()))
                            .endsWith("ClassWithId«23»");
                });
                it("native Object hashcode is used as discriminator if id is null", ()->{
                    Object object = ClassWithId.create(null);
                    String objectHash = Integer.toHexString(object.hashCode());
                    assertThat(Stringer.representationOf(object))
                            .endsWith("ClassWithId«" + objectHash +"»");
                });
            });

            it("object fields are represented like a JSON string", () -> {
                Person fred = Person.createFred();
                assertThat(Stringer.representationOf(fred))
                        .isEqualTo("1º·Person«\"1\"»{\"name\": \"Fred\", \"age\": 42, \"height\": 6.7}");
            });

            describe("with too many properties", ()->{
                it("objects fields are truncated with an ellipsis", () -> {
                    ManyProperties allison = ManyProperties.createAllison();
                    assertThat(Stringer.representationOf(allison))
                            .isEqualTo("1º·ManyProperties«\"allis\"»{\"name\": \"Allison\", \"property1\": \"value1\", ...}");
                });
                it("null fields are omitted", ()->{
                    ManyProperties allison = ManyProperties.createAllison();
                    allison.setProperty1(null);
                    allison.setProperty2(null);
                    assertThat(Stringer.representationOf(allison))
                            .isEqualTo("1º·ManyProperties«\"allis\"»{\"name\": \"Allison\", \"property3\": \"value3\", ...}");

                });
            });
        });

        describe("for objects with cyclic references", ()->{
            it("an integer is assigned to identify first representation", ()->{
                SelfReferencingObject autoReference = SelfReferencingObject.create();
                assertThat(Stringer.representationOf(autoReference))
                        .startsWith("1º·SelfReferencingObject");
            });
            it("a call to first representation is used instead of a duplicate", ()->{
                SelfReferencingObject autoReference = SelfReferencingObject.create();
                assertThat(Stringer.representationOf(autoReference))
                        .isEqualTo("1º·SelfReferencingObject«42»{\"referencing\": §1}");
            });
            it("duplicate objects occupy less space on repetition", ()->{
                List<ClassWithId> exampleList = new ArrayList<>();
                ClassWithId duplicate = ClassWithId.create(42L);
                exampleList.add(duplicate);
                exampleList.add(duplicate);
                exampleList.add(duplicate);
                assertThat(Stringer.representationOf(exampleList))
                        .isEqualTo("1º·3#[2º·ClassWithId«42», §2, §2]");
            });
            it("collections can be detected as circular too", ()->{
                List<List> selfReferencingList = new ArrayList<>();
                selfReferencingList.add(selfReferencingList);
                assertThat(Stringer.representationOf(selfReferencingList))
                        .isEqualTo("1º·1#[§1]");
            });
            it("circular references are detected too", () -> {
                CircularReferencingObject first = CircularReferencingObject.create(42);
                CircularReferencingObject second = CircularReferencingObject.create(24);
                first.setReferencing(second);
                second.setReferencing(first);
                assertThat(Stringer.representationOf(first))
                        .isEqualTo("1º·CircularReferencingObject«42»{\"referencing\": 2º·CircularReferencingObject«24»{\"referencing\": §1}}");

            });
        });
        
        describe("custom toString() implementation", ()->{
            it("is used if present", ()->{
                CustomToStringObject customStringObject = CustomToStringObject.create();
                assertThat(Stringer.representationOf(customStringObject))
                        .isEqualTo("1º·TaDa!");
            });
            it("is overrided if faulty", ()->{
                FaultyToStringObject faultyToStringObject = FaultyToStringObject.create();
                assertThat(Stringer.representationOf(faultyToStringObject))
                        .isEqualTo("1º·FaultyToStringObject«42»{\"usedInToString\": null} instead of NullPointerException: null");
            });
        });
    }

    private Map<Double, Integer> createLongMap() {
        Map<Double, Integer> longMap = new TreeMap<>();
        for (int i = 0; i < 100; i++) {
            longMap.put((double) i, i);
        }
        return longMap;
    }

    private List<Long> createLongList() {
        List<Long> longList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            longList.add((long) i);
        }
        return longList;
    }

    private String[] createLongArray() {
        byte a = 'a';
        String[] longArray = new String[200];
        for (int i = 0; i < longArray.length; i++) {
            longArray[i] = (char)(a + i) + "";
        }
        return longArray;
    }
}
