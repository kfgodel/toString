package ar.com.kfgodel.tostring;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

/**
 * This type defines tests for object representations
 * Created by kfgodel on 10/09/14.
 */
@RunWith(JavaSpecRunner.class)
public class ObjectRepresentationTest extends JavaSpec<StringerTestContext> {
    @Override
    public void define() {
        describe("object discriminator", ()->{
            it("uses native hashcode even if class overrides it");
            it("uses hex string to shorten hashcode number");
        });
        describe("object fields", ()->{
            it("are ordered in declaration order");
            it("null value are included if small representation");
            it("null value are omitted if big representation");
        });
    }
}
