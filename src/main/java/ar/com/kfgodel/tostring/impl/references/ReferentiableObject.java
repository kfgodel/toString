package ar.com.kfgodel.tostring.impl.references;

/**
 * This type represents an object that has a number reference to be used in cyclic representation
 * Created by kfgodel on 11/09/14.
 */
public class ReferentiableObject {

    private Object object;
    private Integer referenceNumber;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Integer getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Integer referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public static ReferentiableObject create(Object object, Integer refNumber) {
        ReferentiableObject referentiable = new ReferentiableObject();
        referentiable.object = object;
        referentiable.referenceNumber = refNumber;
        return referentiable;
    }


}
