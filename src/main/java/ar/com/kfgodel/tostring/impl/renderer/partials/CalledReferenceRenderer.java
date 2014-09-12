package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.CalledReference;
import ar.com.kfgodel.tostring.impl.ReferentiableObject;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;

/**
 * This type renders a referentiable object by indicating a reference number for its representation
 * Created by kfgodel on 11/09/14.
 */
public class CalledReferenceRenderer implements PartialRenderer<CalledReference> {

    public static final CalledReferenceRenderer INSTANCE = new CalledReferenceRenderer();


    @Override
    public String render(CalledReference called) {
        StringBuilder builder = new StringBuilder();
        builder.append(called.getReferenceNumber());
        builder.append(Stringer.CONFIGURATION.getReferenceDeclarationSymbol());
        builder.append(called.getRepresentation());
        return builder.toString();
    }
}
