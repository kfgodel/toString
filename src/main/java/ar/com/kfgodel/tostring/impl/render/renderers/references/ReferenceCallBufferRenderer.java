package ar.com.kfgodel.tostring.impl.render.renderers.references;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.StringerConfiguration;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.buffer.RenderingBuffer;

/**
 * This type knows how to render reference calls into buffers
 * Created by kfgodel on 15/09/14.
 */
public class ReferenceCallBufferRenderer implements PartialBufferRenderer<Integer> {
    private StringerConfiguration config;

    @Override
    public RenderingBuffer render(Integer knownReference) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        buffer.addPart(config.getReferenceCallSymbol());
        buffer.addPart(knownReference);
        return buffer;
    }

    public static ReferenceCallBufferRenderer create(StringerConfiguration config) {
        ReferenceCallBufferRenderer referenceCallBufferRenderer = new ReferenceCallBufferRenderer();
        referenceCallBufferRenderer.config = config;
        return referenceCallBufferRenderer;
    }

}
