package ar.com.kfgodel.v1.tostring.impl.render.renderers.references;

import ar.com.kfgodel.v1.tostring.config.OldStringerConfiguration;
import ar.com.kfgodel.v1.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.RenderingBuffer;

/**
 * This type knows how to render reference calls into buffers
 * Created by kfgodel on 15/09/14.
 */
public class ReferenceCallBufferRenderer implements PartialBufferRenderer<Integer> {
    private OldStringerConfiguration config;

    @Override
    public RenderingBuffer render(Integer knownReference) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        buffer.addPart(config.getReferenceCallSymbol());
        buffer.addPart(knownReference);
        return buffer;
    }

    public static ReferenceCallBufferRenderer create(OldStringerConfiguration config) {
        ReferenceCallBufferRenderer referenceCallBufferRenderer = new ReferenceCallBufferRenderer();
        referenceCallBufferRenderer.config = config;
        return referenceCallBufferRenderer;
    }

}
