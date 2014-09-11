package ar.com.kfgodel.tostring.testobjects;

/**
 * This type represents the class that has its own toString implementation
 * Created by kfgodel on 10/09/14.
 */
public class CustomToStringObject {

    @Override
    public String toString() {
        return "TaDa!";
    }

    public static CustomToStringObject create(){
        return new CustomToStringObject();
    };
}
