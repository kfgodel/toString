package ar.com.kfgodel.v1.tostring.impl.render.renderers.recursive;

import ar.com.kfgodel.v1.tostring.config.StringerConfiguration;
import ar.com.kfgodel.v1.tostring.impl.properties.ObjectField;
import ar.com.kfgodel.v1.tostring.impl.render.buffer.RenderingBuffer;

import java.util.function.BiConsumer;

/**
 * This type represents the action done for objects that render their fields recursively
 * Created by kfgodel on 15/09/14.
 */
public class RecursiveRenderObjectFieldIntoBuffer implements BiConsumer<RenderingBuffer, ObjectField> {

    private StringerConfiguration config;

    @Override
    public void accept(RenderingBuffer buffer, ObjectField objectField) {
        RecursiveRenderIntoBuffer.INSTANCE.accept(buffer, objectField.getFieldName());
        buffer.addPart(config.getKeySeparatorSymbol());
        RecursiveRenderIntoBuffer.INSTANCE.accept(buffer, objectField.getFieldValue());
    }

    public static RecursiveRenderObjectFieldIntoBuffer create(StringerConfiguration config) {
        RecursiveRenderObjectFieldIntoBuffer recursiveRenderObjectFieldIntoBuffer = new RecursiveRenderObjectFieldIntoBuffer();
        recursiveRenderObjectFieldIntoBuffer.config = config;
        return recursiveRenderObjectFieldIntoBuffer;
    }

}
