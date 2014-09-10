package ar.com.kfgodel.tostring;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * This type tests and defines behavior for collection representation
 * Created by kfgodel on 10/09/14.
 */
@RunWith(JavaSpecRunner.class)
public class CollectionRepresentationTest extends JavaSpec<StringerTestContext> {
    @Override
    public void define() {

        describe("arrays, lists and sets", ()->{
            it("are represented using enclosing brackets");
            it("elements are separated with a comma and a space");
            it("are truncated if their representation exceeds 60 chars");
            it("are truncated if they have more than 5 elements and their representation exceeds 40 chars");
            it("are not truncated if they have 5 or less elements and don't exceed 60 chars");
        });

        describe("maps", ()->{
            it("are represented with curly braces and key/value pairs");
            it("key is separated from value with a colon and a space");
            it("entries are separated with a comma and a space");
            it("number keys are represented as numbers");
            it("are truncated if their representation exceeds 60 chars");
            it("are truncated if they have more than 5 elements and their representation exceeds 40 chars");
            it("are not truncated if they have 5 or less elements and don't exceed 60 chars");
        });

    }
}
