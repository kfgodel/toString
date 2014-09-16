package ar.com.kfgodel.tostring.impl;

import ar.com.kfgodel.tostring.impl.references.RepresentationReferences;
import ar.com.kfgodel.tostring.impl.references.IdentityReferences;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.RenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.imple.ListRenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.imple.OptionalReferenceNumberPart;
import ar.com.kfgodel.tostring.impl.render.imple.ReferenceCallBufferRenderer;
import ar.com.kfgodel.tostring.impl.renderer.RendererPerType;

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

    /**
     * Renderer for references calls
     */
    private static final ReferenceCallBufferRenderer REFERENCE_CALL_BUFFER_RENDERER = ReferenceCallBufferRenderer.create();

    private RepresentationReferences references;


    /**
     * Returns the representation of the given object as a String
     * @param object The object to represent
     * @return The created state representation
     */
    public String represent(Object object) {
        RenderingBuffer rendered = this.render(object);
        return rendered.printOnString();
    }

    /**
     * Creates the representation of the object that is not a primitive value and can have circular
     * references
     * @param object The object to represent
     * @return The representation
     */
    private RenderingBuffer representPossibleCircularRef(Object object) {
        // Let's check if we know the object
        Optional<Integer> previousReference = this.references.makeReferenceTo(object);
        if(previousReference.isPresent()){
            // This object was already represented. We take note of the call being made
            return REFERENCE_CALL_BUFFER_RENDERER.render(previousReference.get());
        }

        // It's a new representation
        return createReferentiableRepresentation(object);
    }

    /**
     * Creates a representation of the object with an optional reference number if the object is referenced
     * @param object The object to represent
     * @return The buffer with the representation
     */
    private RenderingBuffer createReferentiableRepresentation(Object object) {
        PartialBufferRenderer<Object> renderer = RENDERER_PER_TYPE.getBestComplexRendererFor(object);
        RenderingBuffer objectRepresentation = renderer.render(object);
        OptionalReferenceNumberPart optionalReference = OptionalReferenceNumberPart.create(object, this.references);
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        buffer.addPart(optionalReference);
        buffer.addPart(objectRepresentation);
        return buffer;
    }

    public static StringerRepresentation create(){
        StringerRepresentation representation = new StringerRepresentation();
        representation.references = IdentityReferences.create();
        return representation;
    }

    /**
     * Represents the given object into a buffer according to this representation state and configuration
     * @param object The object to represent
     * @return The buffer containing the object representation
     */
    public RenderingBuffer render(Object object) {
        //Because primitive values can't do circular references and they don't require extra resources
        // for conversion, we check that first case to optimize memory and processor
        Optional<PartialBufferRenderer<Object>> primitiveRenderer = RENDERER_PER_TYPE.getBestPrimitiveRendererFor(object);
        if(primitiveRenderer.isPresent()){
            // It was a primitive value
            return primitiveRenderer.get().render(object);
        }
        // It's a complex object
        return representPossibleCircularRef(object);
    }
}
