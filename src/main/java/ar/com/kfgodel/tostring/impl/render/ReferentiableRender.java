package ar.com.kfgodel.tostring.impl.render;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.references.RepresentationReferences;

import java.util.Optional;

/**
 * This type represents the render of a referentiaable object, which may need a reference number indicator
 * Created by kfgodel on 14/09/14.
 */
public class ReferentiableRender implements StringRender {

    private Object referentiable;

    private RepresentationReferences references;

    private StringRender referentiableRender;

    @Override
    public void printOn(StringBuilder builder) {
        Optional<Integer> assignedNumber = references.getNumberUsedToReference(referentiable);
        assignedNumber.ifPresent((referenceNumber)-> printReferenceNumberOn(builder, referenceNumber));
        referentiableRender.printOn(builder);
    }

    /**
     * Adds the reference number as part of the referentiable representation
     * @param builder The builder to write to
     * @param referenceNumber The number used to identify the the referentiable instance
     */
    private void printReferenceNumberOn(StringBuilder builder, Integer referenceNumber) {
        builder.append(referenceNumber);
        builder.append(Stringer.CONFIGURATION.getReferenceDeclarationSymbol());
    }

    public static ReferentiableRender create(Object referentiable, StringRender referentiableRender, RepresentationReferences references) {
        ReferentiableRender render = new ReferentiableRender();
        render.referentiable = referentiable;
        render.references = references;
        render.referentiableRender = referentiableRender;
        return render;
    }

}
