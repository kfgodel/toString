package ar.com.kfgodel.tostring.impl;

import ar.com.kfgodel.tostring.StringerConfiguration;

/**
 * This type represents the values for default stringer config
 * Created by kfgodel on 11/09/14.
 */
public class DefaultConfiguration implements StringerConfiguration {

    public static DefaultConfiguration create(){
        return new DefaultConfiguration();
    }

    @Override
    public String renderNumber(Number asNumber) {
        return String.valueOf(asNumber);
    }

    @Override
    public String renderCharSequence(CharSequence charSeq) {
        //We want the strings to be double quoted
        StringBuilder builder = new StringBuilder(charSeq.length() + 2);
        builder.append("\"");
        builder.append(charSeq);
        builder.append("\"");
        return builder.toString();
    }

    @Override
    public String renderCircularReference(Integer knownReference) {
        StringBuilder builder = new StringBuilder(2);
        builder.append("§");
        builder.append(knownReference);
        return builder.toString();
    }

    @Override
    public String renderNull() {
        return "null";
    }
}
