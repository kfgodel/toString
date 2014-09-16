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

    /**
     * Registers a new call made to the given object returning an identifier is teh object was already referenced in the representation
     * @param object The object to register as referenced in the representation
     * @return The reference number given for this object on previous reference
     */
    Optional<Integer> makeReferenceTo(Object object);
}
