package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This type knows how to render a collection
 * Created by kfgodel on 11/09/14.
 */
public class CollectionRenderer implements PartialRenderer<Collection<Object>> {

    public static final CollectionRenderer INSTANCE = new CollectionRenderer();

    @Override
    public String render(Collection<Object> collection) {
        StringBuilder builder = new StringBuilder();
        builder.append(collection.size());
        builder.append(Stringer.CONFIGURATION.getCardinalitySymbol());
        builder.append(Stringer.CONFIGURATION.getOpeningSequenceSymbol());
        addContentTo(builder, collection);
        builder.append(Stringer.CONFIGURATION.getClosingSequenceSymbol());
        return builder.toString();
    }

    private void addContentTo(StringBuilder builder, Collection<Object> collection) {
        int lowToleranceCardinality = Stringer.CONFIGURATION.getCardinalityForLowTolerance();
        int contentSizeLimit = (collection.size() > lowToleranceCardinality)?  Stringer.CONFIGURATION.getLowToleranceSize() : Stringer.CONFIGURATION.getHighToleranceSize();

        AtomicInteger counter = new AtomicInteger(1);
        boolean limitExceeded = collection.stream()
                .map((object) -> Stringer.representationOf(object))
                .anyMatch((objectRepresentation) -> {
                    builder.append(objectRepresentation);
                    boolean isNotLastElement = counter.getAndIncrement() < collection.size();
                    if (isNotLastElement) {
                        //Is not the last, we need a separator
                        builder.append(Stringer.CONFIGURATION.getSequenceElementSeparatorSymbol());
                    }
                    // End if we exceeded the limit
                    return builder.length() > contentSizeLimit;
                });
        boolean beforeTheLastOne = counter.get() <= collection.size();
        if(limitExceeded && beforeTheLastOne){
            //Not all the content made it
            builder.append(Stringer.CONFIGURATION.getTruncatedContentSymbol());
        }
    }

}
