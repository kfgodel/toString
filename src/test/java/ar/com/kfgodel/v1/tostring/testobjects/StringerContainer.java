package ar.com.kfgodel.v1.tostring.testobjects;

import ar.com.kfgodel.v1.tostring.ImplementedWithStringer;
import ar.com.kfgodel.v1.tostring.Stringer;

import java.util.ArrayList;
import java.util.List;

/**
 * This type represents a container object with toString implemented with Stringer
 * Created by kfgodel on 17/09/14.
 */
public class StringerContainer {

    private Integer id;
    private List<StringerContained> elements;

    public static StringerContainer create(Integer id) {
        StringerContainer container = new StringerContainer();
        container.elements = new ArrayList<>();
        container.id = id;
        return container;
    }


    @Override
    @ImplementedWithStringer
    public String toString() {
        return Stringer.representationOf(this);
    }

    public void add(StringerContained contained) {
        this.elements.add(contained);
        contained.setContainer(this);
    }
}
