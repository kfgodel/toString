package ar.com.kfgodel.v1.tostring.testobjects;

import ar.com.kfgodel.v1.tostring.ImplementedWithStringer;
import ar.com.kfgodel.v1.tostring.Stringer;

/**
 * This class represents a contained object with a toString implementation using Stringer
 * Created by kfgodel on 17/09/14.
 */
public class StringerContained {

    private Integer id;

    private StringerContainer container;

    public void setContainer(StringerContainer container) {
        this.container = container;
    }

    public static StringerContained create(Integer id) {
        StringerContained contained = new StringerContained();
        contained.id = id;
        return contained;
    }

    @Override
    @ImplementedWithStringer
    public String toString() {
        return Stringer.representationOf(this);
    }


}
