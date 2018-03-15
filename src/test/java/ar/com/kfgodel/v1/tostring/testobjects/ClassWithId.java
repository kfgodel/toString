package ar.com.kfgodel.v1.tostring.testobjects;

/**
 * This type represents a class that has an ID attribute
 * Created by kfgodel on 10/09/14.
 */
public class ClassWithId {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static ClassWithId create(Long id){
        ClassWithId created = new ClassWithId();
        created.id = id;
        return created;
    }
}
