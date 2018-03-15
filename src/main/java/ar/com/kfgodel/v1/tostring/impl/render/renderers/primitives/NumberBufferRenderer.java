package ar.com.kfgodel.v1.tostring.impl.render.renderers.primitives;

import ar.com.kfgodel.v1.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.RenderingBuffer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.SingleStringBuffer;

/**
 * This type knows how to render numbers into buffers
 * Created by kfgodel on 15/09/14.
 */
public class NumberBufferRenderer implements PartialBufferRenderer<Number> {

    @Override
    public RenderingBuffer render(Number value) {
        return SingleStringBuffer.create(value);
    }

    public static NumberBufferRenderer create() {
        NumberBufferRenderer numberBufferRenderer = new NumberBufferRenderer();
        return numberBufferRenderer;
    }

}
