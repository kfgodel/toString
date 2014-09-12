package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;

import java.lang.reflect.Array;

/**
 * This type knows how to render an array
 * Created by kfgodel on 11/09/14.
 */
public class ArrayRenderer implements PartialRenderer<Object> {

    public static final ArrayRenderer INSTANCE = new ArrayRenderer();

    @Override
    public String render(Object array) {
        StringBuilder builder = new StringBuilder();
        int arrayLength = Array.getLength(array);
        builder.append(arrayLength);
        builder.append(Stringer.CONFIGURATION.getCardinalitySymbol());
        builder.append(Stringer.CONFIGURATION.getOpeningSequenceSymbol());
        addContentTo(builder, array, arrayLength);
        builder.append(Stringer.CONFIGURATION.getClosingSequenceSymbol());
        return builder.toString();
    }

    private void addContentTo(StringBuilder builder, Object array, int arrayLength) {
        int lowToleranceCardinality = Stringer.CONFIGURATION.getCardinalityForLowTolerance();
        int contentSizeLimit = (arrayLength > lowToleranceCardinality)?  Stringer.CONFIGURATION.getLowToleranceSize() : Stringer.CONFIGURATION.getHighToleranceSize();
        for (int i = 0; i < arrayLength; i++) {
            Object element = Array.get(array, i);
            String elementRepresentation = Stringer.representationOf(element);
            builder.append(elementRepresentation);
            boolean isNotLastElement = i < arrayLength - 1;
            if(isNotLastElement){
                //Is not the last, we need a separator
                builder.append(Stringer.CONFIGURATION.getSequenceElementSeparatorSymbol());
            }
            boolean limitExceeded = builder.length() > contentSizeLimit;
            if(isNotLastElement && limitExceeded){
                //We cannot add more
                builder.append(Stringer.CONFIGURATION.getTruncatedContentSymbol());
                break;
            }
        }
    }
}
