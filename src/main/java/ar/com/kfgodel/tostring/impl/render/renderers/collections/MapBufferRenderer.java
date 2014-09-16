package ar.com.kfgodel.tostring.impl.render.renderers.collections;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.buffer.RenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.renderers.recursive.RecursiveRenderKeyAndValueIntoBuffer;

import java.util.Map;

/**
 * This type knows how to render a map into a buffer
 * Created by kfgodel on 14/09/14.
 */
public class MapBufferRenderer implements PartialBufferRenderer<Map<Object, Object>> {

    private SequenceBufferRenderer sequenceRenderer;

    @Override
    public RenderingBuffer render(Map<Object, Object> value) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        int mapSize = value.size();
        CollectionPrefixAction.INSTANCE.accept(buffer, mapSize);
        sequenceRenderer.render(buffer, value.entrySet().iterator(), mapSize);
        return buffer;
    }

    public static MapBufferRenderer create() {
        MapBufferRenderer renderer = new MapBufferRenderer();
        renderer.sequenceRenderer = SequenceBufferRenderer.create(
                Stringer.CONFIGURATION.getOpeningHashSymbol(),
                Stringer.CONFIGURATION.getClosingHashSymbol(),
                RecursiveRenderKeyAndValueIntoBuffer.INSTANCE,
                false);
        return renderer;
    }
}
