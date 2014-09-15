package ar.com.kfgodel.tostring.impl.render.imple;

import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.RenderingBuffer;

/**
 * This type knows how to render numbers into buffers
 * Created by kfgodel on 15/09/14.
 */
public class NumberBufferRenderer implements PartialBufferRenderer<Number> {
    @Override
    public RenderingBuffer render(Number value) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        buffer.addPart(value);
        return buffer;
    }
}
