package ar.com.kfgodel.v1.tostring.impl.render.renderers.collections;

import ar.com.kfgodel.v1.tostring.arrays.ArrayIterator;
import ar.com.kfgodel.v1.tostring.config.StringerConfiguration;
import ar.com.kfgodel.v1.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.RenderingBuffer;
import ar.com.kfgodel.v1.tostring.impl.render.renderers.recursive.RecursiveRenderIntoBuffer;

/**
 * This type know how to render arrays into buffers
 * Created by kfgodel on 14/09/14.
 */
public class ArrayBufferRenderer implements PartialBufferRenderer<Object> {

    private SequenceBufferRenderer sequenceRenderer;
    private CollectionPrefixAction prefixAction;

    @Override
    public RenderingBuffer render(Object value) {
        ArrayIterator arrayIterator = ArrayIterator.create(value);
        int arraySize = arrayIterator.size();
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        prefixAction.accept(buffer, arraySize);
        this.sequenceRenderer.render(buffer, arrayIterator, arraySize);
        return buffer;
    }

    public static ArrayBufferRenderer create(StringerConfiguration config) {
        ArrayBufferRenderer renderer = new ArrayBufferRenderer();
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
