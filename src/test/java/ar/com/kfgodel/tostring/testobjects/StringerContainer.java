package ar.com.kfgodel.tostring.testobjects;

import ar.com.kfgodel.tostring.ImplementedWithStringer;
import ar.com.kfgodel.tostring.Stringer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
