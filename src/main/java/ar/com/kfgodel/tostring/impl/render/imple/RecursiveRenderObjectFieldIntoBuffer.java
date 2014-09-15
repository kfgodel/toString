package ar.com.kfgodel.tostring.impl.render.imple;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.properties.ObjectField;
import ar.com.kfgodel.tostring.impl.render.RenderingBuffer;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This type represents the action done for objects that render their fields recursively
 * Created by kfgodel on 15/09/14.
 */
public class RecursiveRenderObjectFieldIntoBuffer implements BiConsumer<RenderingBuffer, ObjectField> {

    public static final RecursiveRenderObjectFieldIntoBuffer INSTANCE = new RecursiveRenderObjectFieldIntoBuffer();

    @Override
    public void accept(RenderingBuffer buffer, ObjectField objectField) {
        RecursiveRenderIntoBuffer.INSTANCE.accept(buffer, objectField.getFieldName());
        buffer.addPart(Stringer.CONFIGURATION.getKeySeparatorSymbol());
        RecursiveRenderIntoBuffer.INSTANCE.accept(buffer, objectField.getFieldValue());
    }
}
