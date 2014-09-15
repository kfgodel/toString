package ar.com.kfgodel.tostring.impl.render.imple;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.RenderingBuffer;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;
import ar.com.kfgodel.tostring.impl.renderer.partials.MapEntryRenderer;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This type knows how to render a map into a buffer
 * Created by kfgodel on 14/09/14.
 */
public class MapBufferRenderer implements PartialBufferRenderer<Map<Object, Object>> {

    private SequenceBufferRenderer sequenceRenderer;

    @Override
    public RenderingBuffer render(Map<Object, Object> value) {
        return sequenceRenderer.render(value.entrySet().iterator(), value.size());
    }

    public static MapBufferRenderer create() {
        MapBufferRenderer renderer = new MapBufferRenderer();
        renderer.sequenceRenderer = SequenceBufferRenderer.create(
                Stringer.CONFIGURATION.getOpeningHashSymbol(),
                Stringer.CONFIGURATION.getClosingHashSymbol(),
                RecursiveRenderKeyAndValueIntoBuffer.INSTANCE);
        return renderer;
    }
}
