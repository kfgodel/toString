package ar.com.kfgodel.tostring.impl;

import ar.com.kfgodel.tostring.Stringer;

import java.util.IdentityHashMap;
import java.util.Optional;

/**
 * This type represents a stringer recreation of the state of an object.<br>
 *     The same representation can be shared among different calls to Stringer which
 *     allows recursive resolution
 * Created by kfgodel on 11/09/14.
 */
public class StringerRepresentation {

    private IdentityHashMap<Object, Integer> knownReferences;

    /**
     * Returns the representation of the given object
     * @param object The object to represent
     * @return The created state representation
     */
    public String represent(Object object) {
        //Because primitive values can't do circular references and they don't require extra resources
        // for conversion, we check that case first to optimize memory and processor
        Optional<String> represented = treatAsPrimitive(object);
        if(represented.isPresent()){
            // It was a primitive value after all
            return represented.get();
        }

        //It's not a primitive value, it can do circular references let's check that next
        Integer knownReference = getKnownReferences().get(object);
        if(knownReference != null){
            //It was an already represented object
            return treatAsCircularRef(knownReference);
        }
        Integer newReference = getKnownReferences().size();
        getKnownReferences().put(object, newReference);
        return treatAsNonPrimitive(object);
    }

    /**
     * Tries to represent the given object if it's a primitive value
     * @param object The object to represent
     * @return A possible default string representation
     */
    private Optional<String> treatAsPrimitive(Object object) {
        if(object == null){
            return Optional.of(Stringer.CONFIGURATION.renderNull());
        }
        if(object instanceof Number){
            Number asNumber = (Number) object;
            return Optional.of(Stringer.CONFIGURATION.renderNumber(asNumber));
        }
        if(object instanceof CharSequence){
            CharSequence charSeq = (CharSequence) object;
            return Optional.of(Stringer.CONFIGURATION.renderCharSequence(charSeq));
        }
        return Optional.empty();
    }

    /**
     * Creates a string representation of the given reference
     * @param knownReference The reference number
     * @return The string reference representation
     */
    private String treatAsCircularRef(Integer knownReference) {
        return Stringer.CONFIGURATION.renderCircularReference(knownReference);
    }

    /**
     * Creates a string representation of the given object, knowing that is not a primitive value
     * @param object The object to represent
     * @return A string representation
     */
    private String treatAsNonPrimitive(Object object) {
        return null;
    }

    public IdentityHashMap<Object, Integer> getKnownReferences() {
        if (knownReferences == null) {
            knownReferences = new IdentityHashMap<Object, Integer>();
        }
        return knownReferences;
    }

    public static StringerRepresentation create(){
        return new StringerRepresentation();
    }
}
