package ar.com.kfgodel.tostring.impl;

import ar.com.kfgodel.tostring.StringerConfiguration;

/**
 * This type represents the values for default stringer config
 * Created by kfgodel on 11/09/14.
 */
public class DefaultConfiguration implements StringerConfiguration {

    @Override
    public String getStringQuotingSymbol() {
        return "\"";
    }

    @Override
    public String getReferenceCallSymbol() {
        return "§";
    }

    @Override
    public String getCharacterQuotingSymbol() {
        return "'";
    }

    @Override
    public String getReferenceDeclarationSymbol() {
        return "∞·";
    }

    @Override
    public String getCardinalitySymbol() {
        return "#";
    }

    @Override
    public String getOpeningSequenceSymbol() {
        return "[";
    }

    @Override
    public String getClosingSequenceSymbol() {
        return "]";
    }

    @Override
    public int getCardinalityForLowTolerance() {
        return 5;
    }

    @Override
    public int getLowToleranceSize() {
        return 60;
    }

    @Override
    public int getHighToleranceSize() {
        return 80;
    }

    @Override
    public String getSequenceElementSeparatorSymbol() {
        return ", ";
    }

    @Override
    public String getTruncatedContentSymbol() {
        return "...";
    }

    @Override
    public String getOpeningHashSymbol() {
        return "{";
    }

    @Override
    public String getClosingHashSymbol() {
        return "}";
    }

    @Override
    public String getKeySeparatorSymbol() {
        return ": ";
    }

    public static DefaultConfiguration create() {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        return defaultConfiguration;
    }

}
