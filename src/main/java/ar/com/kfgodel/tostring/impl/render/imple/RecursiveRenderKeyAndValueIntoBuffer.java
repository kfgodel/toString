package ar.com.kfgodel.tostring.impl.render.imple;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.StringerContext;
import ar.com.kfgodel.tostring.impl.render.RenderingBuffer;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This type represents the action done in maps to render its entries into the buffer
 * Created by kfgodel on 15/09/14.
 */
public class RecursiveRenderKeyAndValueIntoBuffer implements BiConsumer<RenderingBuffer, Map.Entry<?,?>> {

    public static final RecursiveRenderKeyAndValueIntoBuffer INSTANCE = new RecursiveRenderKeyAndValueIntoBuffer();

    @Override
    public void accept(RenderingBuffer buffer, Map.Entry<?,?> entry) {
        RecursiveRenderIntoBuffer.INSTANCE.accept(buffer, entry.getKey());
        buffer.addPart(Stringer.CONFIGURATION.getKeySeparatorSymbol());
        RecursiveRenderIntoBuffer.INSTANCE.accept(buffer, entry.getValue());
    }
}
