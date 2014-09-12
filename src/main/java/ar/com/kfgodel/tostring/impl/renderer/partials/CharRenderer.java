package ar.com.kfgodel.tostring.impl.renderer.partials;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;

/**
 * This type knows how to render a character
 * Created by kfgodel on 11/09/14.
 */
public class CharRenderer implements PartialRenderer<Character> {

    public static final CharRenderer INSTANCE = new CharRenderer();


    @Override
    public String render(Character character) {
        String quotingSymbol = Stringer.CONFIGURATION.getCharacterQuotingSymbol();
        StringBuilder builder = new StringBuilder(1 + 2 * quotingSymbol.length());
        builder.append(quotingSymbol);
        builder.append(character);
        builder.append(quotingSymbol);
        return builder.toString();
    }
}
