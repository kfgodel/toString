package ar.com.kfgodel.tostring.testobjects;

/**
 * This type serves as testing purposes
 * Created by kfgodel on 10/09/14.
 */
public class SelfReferencingObject {

    private Integer id;
    private SelfReferencingObject referencing;

    public SelfReferencingObject getReferencing() {
        return referencing;
    }

    public void setReferencing(SelfReferencingObject referencing) {
        this.referencing = referencing;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static SelfReferencingObject create(){
        SelfReferencingObject instance = new SelfReferencingObject();
        instance.id = 42;
        instance.referencing = instance;
        return instance;
    }
}
