package ar.com.kfgodel.tostring.impl.render.renderers.primitives;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.buffer.RenderingBuffer;

/**
 * This type knows how to render strings into buffers
 * Created by kfgodel on 15/09/14.
 */
public class CharSequenceBufferRenderer implements PartialBufferRenderer<CharSequence> {
    @Override
    public RenderingBuffer render(CharSequence value) {
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        String quotingSymbol = Stringer.CONFIGURATION.getStringQuotingSymbol();
        buffer.addPart(quotingSymbol);
        buffer.addPart(value);
        buffer.addPart(quotingSymbol);
        return buffer;
    }

    public static CharSequenceBufferRenderer create() {
        CharSequenceBufferRenderer charSequenceBufferRenderer = new CharSequenceBufferRenderer();
        return charSequenceBufferRenderer;
    }

}