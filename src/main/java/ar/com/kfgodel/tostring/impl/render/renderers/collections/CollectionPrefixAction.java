package ar.com.kfgodel.tostring.impl.render.renderers.collections;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.render.buffer.RenderingBuffer;

import java.util.function.BiConsumer;

/**
 * This type represents the action done in collections to prefix the size
 * Created by kfgodel on 15/09/14.
 */
public class CollectionPrefixAction implements BiConsumer<RenderingBuffer, Integer> {

    public static final CollectionPrefixAction INSTANCE = new CollectionPrefixAction();

    @Override
    public void accept(RenderingBuffer buffer, Integer collectionSize) {
        buffer.addPart(collectionSize);
        buffer.addPart(Stringer.CONFIGURATION.getCardinalitySymbol());
    }

}
