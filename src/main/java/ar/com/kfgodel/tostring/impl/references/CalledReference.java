package ar.com.kfgodel.tostring.impl.references;

/**
 * This type represents a string representation that was referenced
 * Created by kfgodel on 11/09/14.
 */
public class CalledReference {

    private Integer referenceNumber;
    private String representation;

    public Integer getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Integer referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getRepresentation() {
        return representation;
    }

    public void setRepresentation(String representation) {
        this.representation = representation;
    }

    public static CalledReference create(ReferentiableObject referentiable, String representation) {
        CalledReference calledReference = new CalledReference();
        calledReference.representation = representation;
        calledReference.referenceNumber = referentiable.getReferenceNumber();
        return calledReference;
    }

}
