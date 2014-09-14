package ar.com.kfgodel.tostring.impl;

import ar.com.kfgodel.tostring.impl.properties.ObjectField;
import ar.com.kfgodel.tostring.impl.references.CalledReference;
import ar.com.kfgodel.tostring.impl.references.ReferentiableObject;
import ar.com.kfgodel.tostring.impl.renderer.PartialRenderer;
import ar.com.kfgodel.tostring.impl.renderer.RendererPerType;
import ar.com.kfgodel.tostring.impl.renderer.partials.*;

import java.util.*;

/**
 * This type represents a stringer recreation of the state of an object.<br>
 *     The same representation can be shared among different calls to Stringer which
 *     allows recursive resolution
 * Created by kfgodel on 11/09/14.
 */
public class StringerRepresentation {

    /**
     * Mapping between object types and their corresponding renderer
     */
    private static final RendererPerType RENDERER_PER_TYPE = RendererPerType.create();

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
     * Tries to represent the given object if it's a primitive value
     * @param object The object to represent
     * @return A possible default string representation
     */
    private Optional<PendingRendering> treatAsPrimitive(Object object) {
        Optional<PartialRenderer<Object>> primitiveRenderer = RENDERER_PER_TYPE.getBestPrimitiveRendererFor(object);
        return primitiveRenderer.map((bestRenderer)-> PendingRendering.create(bestRenderer, object));
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
            // This object was already represented. We take note of the call being made
            this.getCalledReferences().add(knownReference);
            return ReferenceCallRenderer.INSTANCE.render(knownReference);
        }
        // It's a new object, we create a new reference for it (starting from 1)
        Integer newReferenceNumber = getKnownReferences().size() + 1;
        getKnownReferences().put(object, newReferenceNumber);
        ReferentiableObject referentiable = ReferentiableObject.create(object, newReferenceNumber);
        return treatAsReferentiable(referentiable);
    }

    /**
     * Creates a string representation of the given object, knowing that is not a primitive value
     * @param referentiable The object to represent
     * @return A string representation
     */
    private String treatAsReferentiable(ReferentiableObject referentiable) {
        Object object = referentiable.getObject();
        PartialRenderer<Object> renderer = RENDERER_PER_TYPE.getBestComplexRendererFor(object);
        String objectRepresentation = renderer.render(object);
        CalledReference calledReference = CalledReference.create(referentiable, objectRepresentation);
        return CalledReferenceRenderer.INSTANCE.render(calledReference);
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
