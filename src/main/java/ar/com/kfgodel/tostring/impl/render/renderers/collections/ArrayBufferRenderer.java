package ar.com.kfgodel.tostring.impl.render.renderers.collections;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.arrays.ArrayIterator;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.buffer.RenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.renderers.recursive.RecursiveRenderIntoBuffer;

/**
 * This type know how to render arrays into buffers
 * Created by kfgodel on 14/09/14.
 */
public class ArrayBufferRenderer implements PartialBufferRenderer<Object> {

    private SequenceBufferRenderer sequenceRenderer;

    @Override
    public RenderingBuffer render(Object value) {
        ArrayIterator arrayIterator = ArrayIterator.create(value);
        int arraySize = arrayIterator.size();
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        CollectionPrefixAction.INSTANCE.accept(buffer, arraySize);
        this.sequenceRenderer.render(buffer, arrayIterator, arraySize);
        return buffer;
    }

    public static ArrayBufferRenderer create() {
        ArrayBufferRenderer renderer = new ArrayBufferRenderer();
        renderer.sequenceRenderer = SequenceBufferRenderer.create(
                Stringer.CONFIGURATION.getOpeningSequenceSymbol(),
                Stringer.CONFIGURATION.getClosingSequenceSymbol(),
                RecursiveRenderIntoBuffer.INSTANCE,
                false);
        return renderer;
    }

}
