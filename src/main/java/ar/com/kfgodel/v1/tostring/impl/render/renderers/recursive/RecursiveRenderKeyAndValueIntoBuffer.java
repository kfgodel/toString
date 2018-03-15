package ar.com.kfgodel.v1.tostring.impl.render.renderers.recursive;

import ar.com.kfgodel.v1.tostring.config.StringerConfiguration;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.RenderingBuffer;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This type represents the action done in maps to render its entries into the buffer
 * Created by kfgodel on 15/09/14.
 */
public class RecursiveRenderKeyAndValueIntoBuffer implements BiConsumer<RenderingBuffer, Map.Entry<?,?>> {

    private StringerConfiguration config;

    @Override
    public void accept(RenderingBuffer buffer, Map.Entry<?,?> entry) {
        RecursiveRenderIntoBuffer.INSTANCE.accept(buffer, entry.getKey());
        buffer.addPart(config.getKeySeparatorSymbol());
        RecursiveRenderIntoBuffer.INSTANCE.accept(buffer, entry.getValue());
    }

    public static RecursiveRenderKeyAndValueIntoBuffer create(StringerConfiguration config) {
        RecursiveRenderKeyAndValueIntoBuffer renderKeyAndValueIntoBuffer = new RecursiveRenderKeyAndValueIntoBuffer();
        renderKeyAndValueIntoBuffer.config = config;
        return renderKeyAndValueIntoBuffer;
    }

}
