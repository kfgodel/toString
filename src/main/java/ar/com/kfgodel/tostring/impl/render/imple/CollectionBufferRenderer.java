package ar.com.kfgodel.tostring.impl.render.imple;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.StringerContext;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.RenderingBuffer;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiConsumer;

/**
 * This type represents a type renderer that can represent a specific type into a buffer
 * Created by kfgodel on 14/09/14.
 */
public class CollectionBufferRenderer implements PartialBufferRenderer<Collection> {

    private SequenceBufferRenderer sequenceRenderer;

    @Override
    public RenderingBuffer render(Collection collection) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        int collectionSize = collection.size();
        CollectionPrefixAction.INSTANCE.accept(buffer, collectionSize);
        this.sequenceRenderer.render(buffer, collection.iterator(), collectionSize);
        return buffer;
    }

    public static CollectionBufferRenderer create() {
        CollectionBufferRenderer renderer = new CollectionBufferRenderer();
        renderer.sequenceRenderer = SequenceBufferRenderer.create(
                Stringer.CONFIGURATION.getOpeningSequenceSymbol(),
                Stringer.CONFIGURATION.getClosingSequenceSymbol(),
                RecursiveRenderIntoBuffer.INSTANCE,
                false);
        return renderer;
    }

}
