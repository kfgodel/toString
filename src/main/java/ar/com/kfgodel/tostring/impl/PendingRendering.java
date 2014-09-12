package ar.com.kfgodel.tostring.impl;

import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;

/**
 * This type represents a basic value representation that can be included in a more complex representation
 * Created by kfgodel on 11/09/14.
 */
public class PendingRendering {

    private PartialRenderer<Object> renderer;
    private Object renderable;

    /**
     * Solves this representation using the rendered on the value and getting the result
     * @return The rendered representation
     */
    public String resolve() {
        return renderer.render(renderable);
    }

    public static PendingRendering create(PartialRenderer<?> renderer, Object value) {
        PendingRendering rendering = new PendingRendering();
        rendering.renderable = value;
        rendering.renderer = (PartialRenderer<Object>) renderer;
        return rendering;
    }
}
