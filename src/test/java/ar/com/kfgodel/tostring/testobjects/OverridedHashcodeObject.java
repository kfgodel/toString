package ar.com.kfgodel.tostring.testobjects;

/**
 * This type defines its own hashcode
 * Created by kfgodel on 10/09/14.
 */
public class OverridedHashcodeObject {

    @Override
    public int hashCode() {
        return 3;
    }
}
