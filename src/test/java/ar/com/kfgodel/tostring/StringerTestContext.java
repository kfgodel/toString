package ar.com.kfgodel.tostring;


import ar.com.dgarcia.javaspec.api.contexts.TestContext;

import java.util.function.Supplier;

/**
 * This type defines the api for a Stringer test context to be reused in test
 * Created by kfgodel on 09/09/14.
 */
public interface StringerTestContext extends TestContext {

    Stringer stringer();
    void stringer(Supplier<Stringer> definition);

    Object object();
    void object(Supplier<Object> definition);
}
