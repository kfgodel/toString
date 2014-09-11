package ar.com.kfgodel.tostring;

/**
 * This type represents the configuration of Stringer
 * Created by kfgodel on 11/09/14.
 */
public interface StringerConfiguration {
    /**
     * Returns the string corresponding to given number
     * @param asNumber The number to render
     * @return The string to use as value for the number
     */
    String renderNumber(Number asNumber);

    /**
     * Returns the string corresponding to the given value
     * @param charSeq The sequence to render
     * @return The string that represents the sequence
     */
    String renderCharSequence(CharSequence charSeq);

    /**
     * Returns the string that represents the call to a previous reference
     * @param knownReference The number that identifies the duplicate object
     * @return The String with the call
     */
    String renderCircularReference(Integer knownReference);

    /**
     * @return The representation for null
     */
    String renderNull();
}
