package ar.com.kfgodel.v1.tostring.testobjects;

/**
 * This type represents an object that references others
 * Created by kfgodel on 10/09/14.
 */
public class CircularReferencingObject {

    private Integer id;
    private CircularReferencingObject referencing;

    public CircularReferencingObject getReferencing() {
        return referencing;
    }

    public void setReferencing(CircularReferencingObject referencing) {
        this.referencing = referencing;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static CircularReferencingObject create(Integer id){
        CircularReferencingObject instance = new CircularReferencingObject();
        instance.id = id;
        return instance;
    }

}
