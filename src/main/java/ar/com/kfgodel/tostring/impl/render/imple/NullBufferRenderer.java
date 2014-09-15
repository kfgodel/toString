package ar.com.kfgodel.tostring.impl.render.imple;

import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.RenderingBuffer;

/**
 * This type knows how to render null values
 * Created by kfgodel on 15/09/14.
 */
public class NullBufferRenderer implements PartialBufferRenderer<Void> {
    @Override
    public RenderingBuffer render(Void value) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        buffer.addPart("null");
        return buffer;
    }
}
