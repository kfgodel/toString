package ar.com.kfgodel.tostring.impl.render.imple;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.RenderingBuffer;

/**
 * This type knows how to render characters into buffers
 * Created by kfgodel on 14/09/14.
 */
public class CharBufferRenderer implements PartialBufferRenderer<Character> {

    @Override
    public RenderingBuffer render(Character value) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        String quotingSymbol = Stringer.CONFIGURATION.getCharacterQuotingSymbol();
        buffer.addPart(quotingSymbol);
        buffer.addPart(value);
        buffer.addPart(quotingSymbol);
        return buffer;
    }

    public static CharBufferRenderer create() {
        CharBufferRenderer charBufferRenderer = new CharBufferRenderer();
        return charBufferRenderer;
    }

}
