package ar.com.kfgodel.tostring.impl.references;

import java.util.Optional;

/**
 * This type represents the record of references between objects made in the representation
 * Created by kfgodel on 14/09/14.
 */
public interface RepresentationReferences {
    /**
     * Returns the number assigned to the given object when used as a reference made by other object
     * @param referentiable The object whose reference number will be retrieved
     * @return The number assigned to identify it, or empty optional if object was not referenced from others
     */
    Optional<Integer> getNumberUsedToReference(Object referentiable);
}
