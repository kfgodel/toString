package ar.com.kfgodel.tostring;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.kfgodel.tostring.arrays.ArrayIterator;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * This type tests the definition of the array iterator
 * Created by kfgodel on 14/09/14.
 */
@RunWith(JavaSpecRunner.class)
public class ArrayIteratorTest extends JavaSpec<TestContext> {
    @Override
    public void define() {

        describe("hasNext()", ()->{
            it("returns false for an empty array", ()->{
                ArrayIterator iterator = ArrayIterator.create(new String[0]);
                assertThat(iterator.hasNext()).isFalse();
            });
            it("returns true for an array with one element", ()->{
                ArrayIterator iterator = ArrayIterator.create(new String[]{"Hola"});
                assertThat(iterator.hasNext()).isTrue();
            });
            it("return true for one element array even if called twice", () -> {
                ArrayIterator iterator = ArrayIterator.create(new String[]{"Hola"});
                assertThat(iterator.hasNext()).isTrue();
                assertThat(iterator.hasNext()).isTrue();
            });
            it("returns false after advancing with next for one element array", () -> {
                ArrayIterator iterator = ArrayIterator.create(new String[]{"Hola"});
                iterator.next();
                assertThat(iterator.hasNext()).isFalse();
            });
        });

        describe("next()", ()->{
            it("throws IllegalState for an empty array", ()->{
                ArrayIterator iterator = ArrayIterator.create(new String[0]);
                try {
                    iterator.next();
                    failBecauseExceptionWasNotThrown(IllegalStateException.class);
                } catch (Exception e) {
                    assertThat(e).hasMessage("Array doesn't have next element on index[0]");
                }
            });
            it("returns the first element even if hasNext() wasn't called for one element array", ()->{
                ArrayIterator iterator = ArrayIterator.create(new String[]{"Hola"});
                assertThat(iterator.next()).isEqualTo("Hola");
            });
            it("returns the two elements if called twice for a 2 element array", ()->{
                ArrayIterator iterator = ArrayIterator.create(new String[]{"Hola", "Chau"});
                assertThat(iterator.next()).isEqualTo("Hola");
                assertThat(iterator.next()).isEqualTo("Chau");
            });
        });

        describe("size()", ()->{
            it("returns 0 for an empty array", ()->{
                ArrayIterator iterator = ArrayIterator.create(new String[0]);
                assertThat(iterator.size()).isEqualTo(0);
            });
            it("returns 2 for a 2 element array", ()->{
                ArrayIterator iterator = ArrayIterator.create(new String[]{"Hola", "Chau"});
                assertThat(iterator.size()).isEqualTo(2);
            });
        });

    }
}
