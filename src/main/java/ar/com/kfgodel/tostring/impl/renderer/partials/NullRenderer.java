package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;

/**
 * This type renders a representation for null
 * Created by kfgodel on 11/09/14.
 */
public class NullRenderer implements PartialRenderer<Void> {

    public static final NullRenderer INSTANCE = new NullRenderer();

    @Override
    public String render(Void value) {
        return "null";
    }
}
