package ar.com.kfgodel.tostring.impl.render.renderers.recursive;

import ar.com.kfgodel.tostring.impl.StringerContext;
import ar.com.kfgodel.tostring.impl.render.buffer.RenderingBuffer;

import java.util.function.BiConsumer;

/**
 * This type represents the recursive rendering action done in collections
 * Created by kfgodel on 15/09/14.
 */
public class RecursiveRenderIntoBuffer implements BiConsumer<RenderingBuffer, Object> {

    public static final RecursiveRenderIntoBuffer INSTANCE = new RecursiveRenderIntoBuffer();

    @Override
    public void accept(RenderingBuffer buffer, Object object) {
        RenderingBuffer representationBuffer = StringerContext.doWithActiveRepresentation((representation) -> representation.render(object));
        buffer.addPart(representationBuffer);
    }
}