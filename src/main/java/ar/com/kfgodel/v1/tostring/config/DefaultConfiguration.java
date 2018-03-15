package ar.com.kfgodel.v1.tostring.config;

/**
 * This type represents the values for default stringer config
 * Created by kfgodel on 11/09/14.
 */
public class DefaultConfiguration implements StringerConfiguration {

    private RendererPerType rendererPerType;

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
        return "º·";
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

    @Override
    public String getOpeningIdSymbol() {
        return "«";
    }

    @Override
    public String getClosingIdSymbol() {
        return "»";
    }

    @Override
    public int calculateSizeLimitFor(int elementCount) {
        int lowToleranceCardinality = this.getCardinalityForLowTolerance();
        if(elementCount > lowToleranceCardinality){
            return this.getLowToleranceSize();
        }
        return this.getHighToleranceSize();
    }

    @Override
    public RendererPerType getRendererPerType() {
        return rendererPerType;
    }

    public static DefaultConfiguration create() {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.rendererPerType = RendererPerType.create(defaultConfiguration);
        return defaultConfiguration;
    }

}
