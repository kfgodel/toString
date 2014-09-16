package ar.com.kfgodel.tostring.impl.render.renderers.references;

import ar.com.kfgodel.tostring.Stringer;
import ar.com.kfgodel.tostring.impl.references.RepresentationReferences;
import ar.com.kfgodel.tostring.impl.render.buffer.DelayedPartitionable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This type represents an optional reference number used for referentiable objects as part of their representation
 * Created by kfgodel on 14/09/14.
 */
public class OptionalReferenceNumberPart implements DelayedPartitionable {


    private RepresentationReferences references;
    private Object referentiable;

    @Override
    public List<Object> getParts() {
        Optional<Integer> assignedNumber = references.getNumberUsedToReference(referentiable);
        return assignedNumber
                .map((referenceNumber) -> createRefNumDeclarationParts(referenceNumber))
                .orElse(Collections.emptyList());
    }

    @Override
    public int getEstimatedSize() {
        int totalSize = 0;
        List<Object> optionalParts = getParts();
        for (Object optionalPart : optionalParts) {
            totalSize += ((String)optionalPart).length();
        }
        return totalSize;
    }

    /**
     * Creates a string representation of the reference declaration
     * @param referenceNumber The number that identifies the declaration
     * @return The list of parts to represent the reference declaration
     */
    private List<Object> createRefNumDeclarationParts(Integer referenceNumber) {
        ArrayList<Object> parts = new ArrayList<Object>(2);
        parts.add(String.valueOf(referenceNumber));
        parts.add(Stringer.CONFIGURATION.getReferenceDeclarationSymbol());
        return parts;
    }

    public static OptionalReferenceNumberPart create(Object referentiable, RepresentationReferences references) {
        OptionalReferenceNumberPart referencePart = new OptionalReferenceNumberPart();
        referencePart.references = references;
        referencePart.referentiable = referentiable;
        return referencePart;
    }

}
