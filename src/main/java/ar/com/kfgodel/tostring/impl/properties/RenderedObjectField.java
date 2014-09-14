package ar.com.kfgodel.tostring.impl.properties;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.renderer.partials.ObjectFieldRenderer;

/**
 * This type represents and object field whose representation has already been done, and we can know its size
 * Created by kfgodel on 13/09/14.
 */
public class RenderedObjectField {

    private ObjectField objectField;
    private String representation;

    public String getRepresentation() {
        return representation;
    }

    public static RenderedObjectField create(ObjectField objectField) {
        RenderedObjectField rendered = new RenderedObjectField();
        rendered.objectField = objectField;
        rendered.representation = ObjectFieldRenderer.INSTANCE.render(objectField);
        return rendered;
    }

    public int getRepresentationSize() {
        return this.representation.length();
    }

    public boolean isForNullValue() {
        return this.objectField.hasNullValue();
    }
}
