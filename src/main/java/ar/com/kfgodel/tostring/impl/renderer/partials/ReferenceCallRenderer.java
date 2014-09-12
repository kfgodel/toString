package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;

/**
 * This type renders a call to a circular reference occurring in a representation
 * Created by kfgodel on 11/09/14.
 */
public class ReferenceCallRenderer implements PartialRenderer<Integer> {

    public static final ReferenceCallRenderer INSTANCE = new ReferenceCallRenderer();

    @Override
    public String render(Integer knownReference) {
        StringBuilder builder = new StringBuilder(2);
        builder.append(Stringer.CONFIGURATION.getReferenceCallSymbol());
        builder.append(knownReference);
        return builder.toString();
    }
}
