package ar.com.kfgodel.tostring.impl.properties;

import ar.com.kfgodel.tostring.Stringer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This type represents the rendering process of an object render, and holds the state needed to filter properties
 * Created by kfgodel on 13/09/14.
 */
public class ObjectRendering {

    private Object object;
    private List<Field> objectFields;
    private List<RenderedObjectField> renderedFields;

    public void process(StringBuilder builder){
        int lowToleranceCardinality = Stringer.CONFIGURATION.getCardinalityForLowTolerance();
        int contentSizeLimit = (objectFields.size() > lowToleranceCardinality)?  Stringer.CONFIGURATION.getLowToleranceSize() : Stringer.CONFIGURATION.getHighToleranceSize();
        int charsLeft = contentSizeLimit - builder.length();
        boolean omittingNulls = false;
        boolean contentWasTruncated = false;
        for (int i = 0; i < objectFields.size(); i++) {
            Field field = objectFields.get(i);
            ObjectField objectField = ObjectField.create(object, field);
            if(omittingNulls && objectField.hasNullValue()){
                // It's a value we don't need to represent
                continue;
            }
            RenderedObjectField renderedField = RenderedObjectField.create(objectField);
            charsLeft -= renderedField.getRepresentationSize();
            renderedFields.add(renderedField);
            if(charsLeft < 0){
                //We don't have space left to render
                if(!omittingNulls){
                    // We can gain some extra space removing null representations
                    int reducedSpace = this.removeRenderedNulls();
                    charsLeft += reducedSpace;
                    if(reducedSpace > 0 && charsLeft > 0){
                        //We gained something, we can keep adding fields
                        omittingNulls = true;
                        continue;
                    }
                }
                boolean isLastElement = i == objectFields.size() - 1;
                contentWasTruncated = !isLastElement;
                break;
            }
        }
        for (int i = 0; i < renderedFields.size(); i++) {
            RenderedObjectField rendered = renderedFields.get(i);
            builder.append(rendered.getRepresentation());
            boolean isNotLastElement = !(i == renderedFields.size() -1);
            if (isNotLastElement) {
                //Is not the last, we need a separator
                builder.append(Stringer.CONFIGURATION.getSequenceElementSeparatorSymbol());
            }
        }
        if(contentWasTruncated){
            //Not all the content made it
            builder.append(Stringer.CONFIGURATION.getSequenceElementSeparatorSymbol());
            builder.append(Stringer.CONFIGURATION.getTruncatedContentSymbol());
        }

    }

    /**
     * Removes the null values that were rendered and returns the space gained
     * @return The amount of reduced space
     */
    private int removeRenderedNulls() {
        int freedSpace = 0;
        for (Iterator<RenderedObjectField> iterator = renderedFields.iterator(); iterator.hasNext(); ) {
            RenderedObjectField rendered = iterator.next();
            if(rendered.isForNullValue()){
                freedSpace += rendered.getRepresentationSize();
                iterator.remove();
            }
        }
        return freedSpace;
    }


    public static ObjectRendering create(Object object, List<Field> objectFields) {
        ObjectRendering objectRendering = new ObjectRendering();
        objectRendering.object = object;
        objectRendering.objectFields = objectFields;
        objectRendering.renderedFields = new ArrayList<>();
        return objectRendering;
    }

}
