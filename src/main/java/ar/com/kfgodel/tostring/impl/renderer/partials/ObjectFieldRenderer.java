package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.properties.ObjectField;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;

/**
 * This type knows how to render a field and its value
 * Created by kfgodel on 13/09/14.
 */
public class ObjectFieldRenderer implements PartialRenderer<ObjectField> {

    public static final ObjectFieldRenderer INSTANCE = new ObjectFieldRenderer();

    @Override
    public String render(ObjectField objectField) {
        StringBuilder builder = new StringBuilder();
        builder.append(Stringer.representationOf(objectField.getFieldName()));
        builder.append(Stringer.CONFIGURATION.getKeySeparatorSymbol());
        builder.append(Stringer.representationOf(objectField.getFieldValue()));
        return builder.toString();
    }
}
