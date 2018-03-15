package ar.com.kfgodel.v1.tostring.impl.render.renderers.primitives;

import ar.com.kfgodel.v1.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.RenderingBuffer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.SingleStringBuffer;

/**
 * This type knows how to render null values
 * Created by kfgodel on 15/09/14.
 */
public class NullBufferRenderer implements PartialBufferRenderer<Void> {

    private SingleStringBuffer cachedBuffer;

    @Override
    public RenderingBuffer render(Void value) {
        return cachedBuffer;
    }

    public static NullBufferRenderer create() {
        NullBufferRenderer nullBufferRenderer = new NullBufferRenderer();
        nullBufferRenderer.cachedBuffer = SingleStringBuffer.create("null");
        return nullBufferRenderer;
    }

}
