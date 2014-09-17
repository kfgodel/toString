package ar.com.kfgodel.tostring.impl.references;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * This type represens the representation references made to objects
 * Created by kfgodel on 16/09/14.
 */
public class IdentityReferences implements RepresentationReferences {

    private Map<Object, Integer> referenceNumbers;
    private int nextAssignableNumber;

    @Override
    public Optional<Integer> getNumberUsedToReference(Object referentiable) {
        Integer referenceNumber = this.referenceNumbers.get(referentiable);
        return Optional.ofNullable(referenceNumber);
    }

    @Override
    public Optional<Integer> makeReferenceTo(Object object) {
        Integer previousNumber = this.referenceNumbers.get(object);
        if(previousNumber != null){
            // There's already a reference and a call to that reference in the representation
            return Optional.of(previousNumber);
        }
        if(this.referenceNumbers.containsKey(object)){
            // There's already a reference, but not a call, this is the first one
            Integer assignedNumber = nextAssignableNumber++;
            this.referenceNumbers.put(object, assignedNumber);
            return Optional.of(assignedNumber);
        }
        // There's no previous reference
        this.referenceNumbers.put(object, null);
        return Optional.empty();
    }

    public static IdentityReferences create() {
        IdentityReferences setReferences = new IdentityReferences();
        setReferences.referenceNumbers = new IdentityHashMap<>();
        setReferences.nextAssignableNumber = 1;
        return setReferences;
    }

}
