package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;

/**
 * This knows how to render numbers
 * Created by kfgodel on 11/09/14.
 */
public class NumberRenderer implements PartialRenderer<Number> {

    public static final NumberRenderer INSTANCE = new NumberRenderer();

    @Override
    public String render(Number value) {
        return String.valueOf(value);
    }
}
