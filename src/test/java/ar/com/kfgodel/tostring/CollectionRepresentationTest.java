package ar.com.kfgodel.tostring;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type tests and defines behavior for collection representation
 * Created by kfgodel on 10/09/14.
 */
@RunWith(JavaSpecRunner.class)
public class CollectionRepresentationTest extends JavaSpec<StringerTestContext> {
    @Override
    public void define() {

        describe("arrays, lists and sets", ()-> {
            beforeEach(()->{
                context().object(ArrayList::new);
            });
            it("indicate size as part of the representation", () -> {
                assertThat(Stringer.representationOf(context().object()))
                        .contains("0#");
            });
            it("are represented using enclosing brackets", ()->{
                assertThat(Stringer.representationOf(context().object()))
                        .endsWith("[]");
            });
            it("elements are separated with a comma and a space", () -> {
                List<Integer> list = (List<Integer>) context().object();
                list.add(1);
                list.add(2);
                assertThat(Stringer.representationOf(list))
                        .contains("1, 2");
            });
            it("are omitted if their representation exceeds 80 chars", ()->{
                List<String> list = (List<String>) context().object();
                list.add("Lorem ipsum dolor sit amet.");
                list.add("Lorem ipsum dolor sit amet.");
                list.add("Lorem ipsum dolor sit amet.");
                list.add("Lorem ipsum dolor sit amet.");
                assertThat(Stringer.representationOf(list))
                        .contains("4#[\"Lorem ipsum dolor sit amet.\", \"Lorem ipsum dolor sit amet.\", \"Lorem ipsum dolor sit amet.\", ...]");
            });
            it("are omitted if they have more than 5 elements and their representation exceeds 60 chars", ()->{
                List<Long> longList = createLongList();
                assertThat(Stringer.representationOf(longList))
                        .contains("100#[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, ...]");
            });
            it("are not omitted if they have 5 or less elements and don't exceed 80 chars", ()->{
                List<String> list = (List<String>) context().object();
                list.add("Lorem ipsum dolor sit amet.");
                list.add("Lorem ipsum dolor sit amet.");
                list.add("Lorem ipsum dolor sit amet.");
                assertThat(Stringer.representationOf(list))
                        .isEqualTo("1º·3#[\"Lorem ipsum dolor sit amet.\", \"Lorem ipsum dolor sit amet.\", \"Lorem ipsum dolor sit amet.\"]");
            });
        });

        describe("maps", ()->{
            beforeEach(()->{
                context().object(TreeMap::new);
            });
            it("indicate size as part of the representation", () -> {
                assertThat(Stringer.representationOf(context().object()))
                        .contains("0#");
            });
            it("are represented with curly braces and key/value pairs", ()->{
                assertThat(Stringer.representationOf(context().object()))
                        .endsWith("{}");
            });
            it("key is separated from value with a colon and a space", ()->{
                Map<String, Integer> map = (Map<String, Integer>) context().object();
                map.put("a", 1);
                assertThat(Stringer.representationOf(context().object()))
                        .contains("\"a\": 1");
            });
            it("entries are separated with a comma and a space", ()->{
                Map<String, Integer> map = (Map<String, Integer>) context().object();
                map.put("a", 1);
                map.put("b", 2);
                assertThat(Stringer.representationOf(context().object()))
                        .contains("\"a\": 1, \"b\": 2");
            });
            it("number keys are represented as numbers", ()->{
                Map<Double, Integer> map = (Map<Double, Integer>) context().object();
                map.put(1.0, 1);
                assertThat(Stringer.representationOf(context().object()))
                        .contains("1.0: 1");
            });
            it("are omitted if their representation exceeds 60 chars", ()->{
                Map<String, String> map = (Map<String, String>) context().object();
                map.put("Lorem ipsum1", "Lorem ipsum dolor sit amet.");
                map.put("Lorem ipsum2", "Lorem ipsum dolor sit amet.");
                map.put("Lorem ipsum3", "Lorem ipsum dolor sit amet.");
                map.put("Lorem ipsum4", "Lorem ipsum dolor sit amet.");
                assertThat(Stringer.representationOf(context().object()))
                        .isEqualTo("1º·4#{\"Lorem ipsum1\": \"Lorem ipsum dolor sit amet.\", \"Lorem ipsum2\": \"Lorem ipsum dolor sit amet.\", ...}");
            });
            it("are omitted if they have more than 5 elements and their representation exceeds 40 chars", ()->{
                Map<Double, Integer> longMap = createLongMap();
                assertThat(Stringer.representationOf(longMap))
                        .isEqualTo("1º·100#{0.0: 0, 1.0: 1, 2.0: 2, 3.0: 3, 4.0: 4, 5.0: 5, 6.0: 6, ...}");

            });
            it("are not omitted if they have 5 or less elements and don't exceed 60 chars", ()->{
                Map<String, String> map = (Map<String, String>) context().object();
                map.put("Lorem ipsum1", "Lorem ipsum dolor sit amet.");
                map.put("Lorem ipsum2", "Lorem ipsum dolor sit amet.");
                assertThat(Stringer.representationOf(context().object()))
                        .isEqualTo("1º·2#{\"Lorem ipsum1\": \"Lorem ipsum dolor sit amet.\", \"Lorem ipsum2\": \"Lorem ipsum dolor sit amet.\"}");
            });
        });

    }

    private List<Long> createLongList() {
        List<Long> longList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            longList.add((long) i);
        }
        return longList;
    }

    private Map<Double, Integer> createLongMap() {
        Map<Double, Integer> longMap = new TreeMap<>();
        for (int i = 0; i < 100; i++) {
            longMap.put((double) i, i);
        }
        return longMap;
    }
}
