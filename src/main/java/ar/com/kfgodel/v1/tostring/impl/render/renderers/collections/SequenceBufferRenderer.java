package ar.com.kfgodel.v1.tostring.impl.render.renderers.collections;

import ar.com.kfgodel.v1.tostring.config.OldStringerConfiguration;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.RenderingBuffer;

import java.util.Iterator;
import java.util.function.BiConsumer;

/**
 * This type knows how to render a sequence of elements (represented by an iterator and a size)
 * Created by kfgodel on 14/09/14.
 */
public class SequenceBufferRenderer {

    private String openingSequenceSymbol;
    private String closingSequenceSymbol;
    private BiConsumer<RenderingBuffer, Object> actionPerElement;
    private boolean bodyIsOptional;
    private OldStringerConfiguration config;


    /**
     * Renders the content of the iterator limited by size restrictions into a buffer
     * @param elementIterator The element iterator to take elements from
     * @param sequenceSize The size of the sequence (needed because iterator doesn't have size)
     * @return  The buffer that contains the rendered parts
     */
    public void render(RenderingBuffer buffer, Iterator<?> elementIterator, int sequenceSize){
        if(bodyIsOptional && sequenceSize == 0){
            return;
        }
        buffer.addPart(openingSequenceSymbol);
        renderInto(buffer, elementIterator, sequenceSize);
        buffer.addPart(closingSequenceSymbol);
    }


    /**
     * Adds to the given buffer the rendered elements of the iterator as a sequence, limiting the elements added
     * to size restrictions on the configuration
     * @param buffer The buffer to add the rendered elements
     * @param elementIterator The iterator to take elements from
     * @param sequenceSize The size of the sequence
     */
    private void renderInto(RenderingBuffer buffer, Iterator<?> elementIterator, int sequenceSize) {
        int contentSizeLimit = config.calculateSizeLimitFor(sequenceSize);
        boolean alreadyRenderedFirst = false;
        while(elementIterator.hasNext()){
            if (alreadyRenderedFirst) {
                // After the first element we add separators between elements
                buffer.addPart(config.getSequenceElementSeparatorSymbol());
            } else {
                alreadyRenderedFirst = true;
            }
            boolean limitReached = buffer.getEstimatedSize() >= contentSizeLimit;
            if(limitReached){
                // We will skip the rest because space limitations
                buffer.addPart(config.getTruncatedContentSymbol());
                break;
            }
            Object object = elementIterator.next();
            this.actionPerElement.accept(buffer, object);
        }
    }

    public static SequenceBufferRenderer create( String openingSequenceSymbol, String closingSequenceSymbol, BiConsumer<RenderingBuffer, ?> actionPerElement, boolean elementsAreOptional, OldStringerConfiguration config ) {
        SequenceBufferRenderer renderer = new SequenceBufferRenderer();
        renderer.openingSequenceSymbol = openingSequenceSymbol;
        renderer.closingSequenceSymbol = closingSequenceSymbol;
        renderer.actionPerElement = (BiConsumer<RenderingBuffer, Object>) actionPerElement;
        renderer.bodyIsOptional = elementsAreOptional;
        renderer.config = config;
        return renderer;
    }

}