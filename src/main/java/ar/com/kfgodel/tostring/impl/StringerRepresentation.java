package ar.com.kfgodel.tostring.impl;

import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;
import ar.com.kfgodel.tostring.impl.renderer.partials.*;

import java.lang.reflect.Array;
import java.util.*;

/**
 * This type represents a stringer recreation of the state of an object.<br>
 *     The same representation can be shared among different calls to Stringer which
 *     allows recursive resolution
 * Created by kfgodel on 11/09/14.
 */
public class StringerRepresentation {

    private IdentityHashMap<Object, Integer> knownReferences;
    private Set<Integer> calledReferences;

    /**
     * Returns the representation of the given object
     * @param object The object to represent
     * @return The created state representation
     */
    public String represent(Object object) {
        //Because primitive values can't do circular references and they don't require extra resources
        // for conversion, we check that first case to optimize memory and processor
        Optional<PendingRendering> pendingRendering = treatAsPrimitive(object);
        if(pendingRendering.isPresent()){
            // It was a primitive value
            return pendingRendering.get().resolve();
        }
        // It's a complex object
        return representPossibleCircularRef(object);


    }

    /**
     * Creates the representation of the object that is not a primitive value and can have circular
     * references
     * @param object The object to represent
     * @return The representation
     */
    private String representPossibleCircularRef(Object object) {
        // Let's check if we know the object
        Integer knownReference = getKnownReferences().get(object);
        if(knownReference != null){
            // This object was already represented. We take not of the call
            this.getCalledReferences().add(knownReference);
            return ReferenceCallRenderer.INSTANCE.render(knownReference);
        }
        // It's a new object, we create a new reference for it
        Integer newReferenceNumber = getKnownReferences().size();
        getKnownReferences().put(object, newReferenceNumber);
        ReferentiableObject referentiable = ReferentiableObject.create(object, newReferenceNumber);
        return treatAsReferentiable(referentiable);
    }

    /**
     * Tries to represent the given object if it's a primitive value
     * @param object The object to represent
     * @return A possible default string representation
     */
    private Optional<PendingRendering> treatAsPrimitive(Object object) {
        PartialRenderer<?> bestRenderer = null;
        if(object == null){
            bestRenderer = NullRenderer.INSTANCE;
        } else if(object instanceof Number) {
            bestRenderer = NumberRenderer.INSTANCE;
        } else if(object instanceof  Character){
            bestRenderer = CharRenderer.INSTANCE;
        } else if(object instanceof CharSequence){
            bestRenderer = CharSequenceRenderer.INSTANCE;
        } else {
            //Not renderable as primitive
            return Optional.empty();
        }
        return Optional.of(PendingRendering.create(bestRenderer, object));
    }

    /**
     * Creates a string representation of the given object, knowing that is not a primitive value
     * @param referentiable The object to represent
     * @return A string representation
     */
    private String treatAsReferentiable(ReferentiableObject referentiable) {
        Object object = referentiable.getObject();
        PartialRenderer<Object> renderer = pickBestRendererFor(object);
        String objectRepresentation = renderer.render(object);
        if(this.doesNotIncludeAReferenceCallTo(referentiable)){
            // There was no cyclic reference. No need to append ref number
            return objectRepresentation;
        }
        CalledReference calledReference = CalledReference.create(referentiable, objectRepresentation);
        return CalledReferenceRenderer.INSTANCE.render(calledReference);
    }

    /**
     * Selects the best renderer for a referentiable object
     * @param object The object to render
     * @return The best renderer
     */
    private PartialRenderer<Object> pickBestRendererFor(Object object) {
        if(object.getClass().isArray()){
            return (PartialRenderer) ArrayRenderer.INSTANCE;
        } else if(object instanceof Collection){
            return (PartialRenderer) CollectionRenderer.INSTANCE;
        } else if (object instanceof Map){
            return (PartialRenderer) MapRenderer.INSTANCE;
        } else if (object instanceof Map.Entry){
            return (PartialRenderer) MapEntryRenderer.INSTANCE;
        }
        return ObjectRenderer.INSTANCE;
    }

    /**
     * Indicates if the given object was referenced and a call to its references was made in this representation
     * @param referentiable The object to check as a reference
     * @return False is there was no cyclic reference to the object
     */
    private boolean doesNotIncludeAReferenceCallTo(ReferentiableObject referentiable) {
        Integer objectRefNumber = referentiable.getReferenceNumber();
        return !getCalledReferences().contains(objectRefNumber);
    }

    public Set<Integer> getCalledReferences() {
        if (calledReferences == null) {
            calledReferences = new HashSet<>();
        }
        return calledReferences;
    }

    public IdentityHashMap<Object, Integer> getKnownReferences() {
        if (knownReferences == null) {
            knownReferences = new IdentityHashMap<Object, Integer>();
        }
        return knownReferences;
    }

    public static StringerRepresentation create(){
        StringerRepresentation representation = new StringerRepresentation();
        return representation;
    }
}
