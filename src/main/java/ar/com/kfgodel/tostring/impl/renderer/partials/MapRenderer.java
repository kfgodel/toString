package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This type knows how to render a map
 * Created by kfgodel on 11/09/14.
 */
public class MapRenderer implements PartialRenderer<Map<Object, Object>> {

    public static final MapRenderer INSTANCE = new MapRenderer();

    @Override
    public String render(Map<Object, Object> map) {
        StringBuilder builder = new StringBuilder();
        builder.append(map.size());
        builder.append(Stringer.CONFIGURATION.getCardinalitySymbol());
        builder.append(Stringer.CONFIGURATION.getOpeningHashSymbol());
        addContentTo(builder, map);
        builder.append(Stringer.CONFIGURATION.getClosingHashSymbol());
        return builder.toString();
    }

    private void addContentTo(StringBuilder builder, Map<Object, Object> map) {
        int lowToleranceCardinality = Stringer.CONFIGURATION.getCardinalityForLowTolerance();
        int contentSizeLimit = (map.size() > lowToleranceCardinality)?  Stringer.CONFIGURATION.getLowToleranceSize() : Stringer.CONFIGURATION.getHighToleranceSize();

        AtomicInteger counter = new AtomicInteger(1);
        boolean limitExceeded = map.entrySet().stream()
                .map((entry) -> MapEntryRenderer.INSTANCE.render(entry))
                .anyMatch((objectRepresentation) -> {
                    builder.append(objectRepresentation);
                    boolean isNotLastElement = counter.getAndIncrement() < map.size();
                    if (isNotLastElement) {
                        //Is not the last, we need a separator
                        builder.append(Stringer.CONFIGURATION.getSequenceElementSeparatorSymbol());
                    }
                    // End if we exceeded the limit
                    return builder.length() > contentSizeLimit;
                });
        boolean beforeTheLastOne = counter.get() <= map.size();
        if(limitExceeded && beforeTheLastOne){
            //Not all the content made it
            builder.append(Stringer.CONFIGURATION.getTruncatedContentSymbol());
        }
    }
}
