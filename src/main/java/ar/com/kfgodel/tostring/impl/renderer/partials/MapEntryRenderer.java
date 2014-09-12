package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;
import com.sun.javafx.collections.MappingChange;

import java.util.Map;

/**
 * This type knows how to render map entries
 * Created by kfgodel on 11/09/14.
 */
public class MapEntryRenderer implements PartialRenderer<Map.Entry> {

    public static final MapEntryRenderer INSTANCE = new MapEntryRenderer();

    @Override
    public String render(Map.Entry entry) {
        StringBuilder builder = new StringBuilder();
        builder.append(Stringer.representationOf(entry.getKey()));
        builder.append(Stringer.CONFIGURATION.getKeySeparatorSymbol());
        builder.append(Stringer.representationOf(entry.getValue()));
        return builder.toString();
    }
}
