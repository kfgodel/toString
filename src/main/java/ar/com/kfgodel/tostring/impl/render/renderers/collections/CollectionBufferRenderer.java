package ar.com.kfgodel.tostring.impl.render.renderers.collections;

import ar.com.kfgodel.tostring.config.StringerConfiguration;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.buffer.RenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.renderers.recursive.RecursiveRenderIntoBuffer;

import java.util.Collection;

/**
 * This type represents a type renderer that can represent a specific type into a buffer
 * Created by kfgodel on 14/09/14.
 */
public class CollectionBufferRenderer implements PartialBufferRenderer<Collection> {

    private SequenceBufferRenderer sequenceRenderer;
    private CollectionPrefixAction prefixAction;

    @Override
    public RenderingBuffer render(Collection collection) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        int collectionSize = collection.size();
        prefixAction.accept(buffer, collectionSize);
        this.sequenceRenderer.render(buffer, collection.iterator(), collectionSize);
        return buffer;
    }

    public static CollectionBufferRenderer create(StringerConfiguration config) {
        CollectionBufferRenderer renderer = new CollectionBufferRenderer();
        renderer.prefixAction = CollectionPrefixAction.create(config);
        renderer.sequenceRenderer = SequenceBufferRenderer.create(
                config.getOpeningSequenceSymbol(),
                config.getClosingSequenceSymbol(),
                RecursiveRenderIntoBuffer.INSTANCE,
                false,
                config);
        return renderer;
    }

}
