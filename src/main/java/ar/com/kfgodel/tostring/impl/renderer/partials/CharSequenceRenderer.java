package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;

/**
 * This type knows how to render a char sequence (string)
 * Created by kfgodel on 11/09/14.
 */
public class CharSequenceRenderer implements PartialRenderer<CharSequence> {

    public static final CharSequenceRenderer INSTANCE = new CharSequenceRenderer();


    @Override
    public String render(CharSequence charSeq) {
        //We want the strings to be double quoted
        String quotingSymbol = Stringer.CONFIGURATION.getStringQuotingSymbol();
        StringBuilder builder = new StringBuilder(charSeq.length() + 2 * quotingSymbol.length());
        builder.append(quotingSymbol);
        builder.append(charSeq);
        builder.append(quotingSymbol);
        return builder.toString();
    }
}
