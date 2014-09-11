package ar.com.kfgodel.tostring.testobjects;

import java.util.List;

/**
 * This type represents a class that has its own faulty toString implementation
 * Created by kfgodel on 10/09/14.
 */
public class FaultyToStringObject {

    private Integer id;
    private List<String> usedInToString;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getUsedInToString() {
        return usedInToString;
    }

    public void setUsedInToString(List<String> usedInToString) {
        this.usedInToString = usedInToString;
    }

    public static FaultyToStringObject create(){
        FaultyToStringObject instance = new FaultyToStringObject();
        instance.id = 42;
        return instance;
    };

    @Override
    public String toString() {
        return "size: " + usedInToString.size();
    }
}
