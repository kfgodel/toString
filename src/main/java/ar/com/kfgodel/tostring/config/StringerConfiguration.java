package ar.com.kfgodel.tostring.config;

/**
 * This type represents the configuration of Stringer
 * Created by kfgodel on 11/09/14.
 */
public interface StringerConfiguration {
    /**
     * @return The symbol used to quote strings in the representations
     */
    String getStringQuotingSymbol();

    /**
     * @return The symbol used to make call to references in parts of the representation
     */
    String getReferenceCallSymbol();

    /**
     * @return The symbol used to quote characters
     */
    String getCharacterQuotingSymbol();

    /**
     * @return THe symbol used to declare a reference in the representation
     */
    String getReferenceDeclarationSymbol();

    /**
     * @return The symbol used to express the size of a collection
     */
    String getCardinalitySymbol();

    /**
     * @return The symbols used to start a sequence
     */
    String getOpeningSequenceSymbol();

    /**
     * @return The symbol used to end a sequence
     */
    String getClosingSequenceSymbol();

    /**
     * @return The number of elements a collection must have to be heavily compacted
     */
    int getCardinalityForLowTolerance();

    /**
     * @return Amount of chars allowed for heavily compacted representations
     */
    int getLowToleranceSize();


    /**
     * @return Amount of chars allowed for normal representations
     */
    int getHighToleranceSize();

    /**
     * @return The symbol used to separate sequence elements
     */
    String getSequenceElementSeparatorSymbol();

    /**
     * @return The symbol used to indicate an incomplete content
     */
    String getTruncatedContentSymbol();

    /**
     * @return The symbol used to start a hash of pairs
     */
    String getOpeningHashSymbol();

    /**
     * @return The symbol used to end a hash of pairs
     */
    String getClosingHashSymbol();

    /**
     * @return Symbol used to separate key from value
     */
    String getKeySeparatorSymbol();

    /**
     * @return The symbol used to start a discriminator id
     */
    String getOpeningIdSymbol();

    /**
     * @return The symbol used to end a discriminator id
     */
    String getClosingIdSymbol();

    /**
     * Calculates which limit to be used in size according to the amount of elements to be represented
     * @param elementCount The amount of elements to represent
     * @return The limit in characters to use
     */
    int calculateSizeLimitFor(int elementCount);

    /**
     * Returns the renderer pertype configuration
     * @return Renderer per type mappings object
     */
    RendererPerType getRendererPerType();
}
