package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;

/**
 * This type knows how to render a typical object
 * Created by kfgodel on 11/09/14.
 */
public class ObjectRenderer implements PartialRenderer<Object> {

    public static final ObjectRenderer INSTANCE = new ObjectRenderer();

    @Override
    public String render(Object value) {
        return null;
    }
}
