package ar.com.kfgodel.v1.tostring.impl.render.renderers.primitives;

import ar.com.kfgodel.v1.tostring.config.StringerConfiguration;
import ar.com.kfgodel.v1.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.RenderingBuffer;

/**
 * This type knows how to render characters into buffers
 * Created by kfgodel on 14/09/14.
 */
public class CharBufferRenderer implements PartialBufferRenderer<Character> {

    private StringerConfiguration config;

    @Override
    public RenderingBuffer render(Character value) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        String quotingSymbol = config.getCharacterQuotingSymbol();
        buffer.addPart(quotingSymbol);
        buffer.addPart(value);
        buffer.addPart(quotingSymbol);
        return buffer;
    }

    public static CharBufferRenderer create(StringerConfiguration config) {
        CharBufferRenderer charBufferRenderer = new CharBufferRenderer();
        charBufferRenderer.config = config;
        return charBufferRenderer;
    }

}
