package ar.com.kfgodel.tostring.impl.render.renderers.collections;

import ar.com.kfgodel.tostring.config.StringerConfiguration;
import ar.com.kfgodel.tostring.impl.render.buffer.RenderingBuffer;

import java.util.function.BiConsumer;

/**
 * This type represents the action done in collections to prefix the size
 * Created by kfgodel on 15/09/14.
 */
public class CollectionPrefixAction implements BiConsumer<RenderingBuffer, Integer> {

    private StringerConfiguration config;

    @Override
    public void accept(RenderingBuffer buffer, Integer collectionSize) {
        buffer.addPart(collectionSize);
        buffer.addPart(config.getCardinalitySymbol());
    }

    public static CollectionPrefixAction create(StringerConfiguration config) {
        CollectionPrefixAction collectionPrefixAction = new CollectionPrefixAction();
        collectionPrefixAction.config = config;
        return collectionPrefixAction;
    }


}
