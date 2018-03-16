package ar.com.kfgodel.v1.tostring.impl.render.renderers.collections;

import ar.com.kfgodel.v1.tostring.config.OldStringerConfiguration;
import ar.com.kfgodel.v1.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.RenderingBuffer;
import ar.com.kfgodel.v1.tostring.impl.render.renderers.recursive.RecursiveRenderKeyAndValueIntoBuffer;

import java.util.Map;

/**
 * This type knows how to render a map into a buffer
 * Created by kfgodel on 14/09/14.
 */
public class MapBufferRenderer implements PartialBufferRenderer<Map<Object, Object>> {

    private SequenceBufferRenderer sequenceRenderer;
    private CollectionPrefixAction prefixAction;

    @Override
    public RenderingBuffer render(Map<Object, Object> value) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        int mapSize = value.size();
        prefixAction.accept(buffer, mapSize);
        sequenceRenderer.render(buffer, value.entrySet().iterator(), mapSize);
        return buffer;
    }

    public static MapBufferRenderer create(OldStringerConfiguration config) {
        MapBufferRenderer renderer = new MapBufferRenderer();
        renderer.prefixAction = CollectionPrefixAction.create(config);
        renderer.sequenceRenderer = SequenceBufferRenderer.create(
                config.getOpeningHashSymbol(),
                config.getClosingHashSymbol(),
                RecursiveRenderKeyAndValueIntoBuffer.create(config),
                false,
                config);
        return renderer;
    }
}
