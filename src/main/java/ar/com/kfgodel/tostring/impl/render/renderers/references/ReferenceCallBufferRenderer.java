package ar.com.kfgodel.tostring.impl.render.renderers.references;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.buffer.RenderingBuffer;

/**
 * This type knows how to render reference calls into buffers
 * Created by kfgodel on 15/09/14.
 */
public class ReferenceCallBufferRenderer implements PartialBufferRenderer<Integer> {
    @Override
    public RenderingBuffer render(Integer knownReference) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        buffer.addPart(Stringer.CONFIGURATION.getReferenceCallSymbol());
        buffer.addPart(knownReference);
        return buffer;
    }

    public static ReferenceCallBufferRenderer create() {
        ReferenceCallBufferRenderer referenceCallBufferRenderer = new ReferenceCallBufferRenderer();
        return referenceCallBufferRenderer;
    }

}
