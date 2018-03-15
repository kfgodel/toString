package ar.com.kfgodel.v1.tostring;


import ar.com.dgarcia.javaspec.api.contexts.TestContext;

import java.util.function.Supplier;

/**
 * This type defines the api for a Stringer test context to be reused in test
 * Created by kfgodel on 09/09/14.
 */
public interface OldStringerTestContext extends TestContext {

    OldStringer stringer();
    void stringer(Supplier<OldStringer> definition);

    Object object();
    void object(Supplier<Object> definition);
}
