package ar.com.kfgodel.tostring.impl;

import ar.com.kfgodel.tostring.config.StringerConfiguration;
import ar.com.kfgodel.tostring.impl.references.RepresentationReferences;
import ar.com.kfgodel.tostring.impl.references.IdentityReferences;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.buffer.RenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.buffer.ListRenderingBuffer;
import ar.com.kfgodel.tostring.impl.render.renderers.references.OptionalReferenceNumberPart;

import java.util.*;

/**
 * This type represents a stringer recreation of the state of an object.<br>
 *     The same representation can be shared among different calls to Stringer which
 *     allows recursive resolution
 * Created by kfgodel on 11/09/14.
 */
public class StringerRepresentation {

    private RepresentationReferences references;
    private StringerConfiguration config;


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
            PartialBufferRenderer<Integer> referenceCallRenderer = config.getRendererPerType().getReferenceCallRenderer();
            return referenceCallRenderer.render(previousReference.get());
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
        PartialBufferRenderer<Object> renderer = config.getRendererPerType().getBestComplexRendererFor(object);
        RenderingBuffer objectRepresentation = renderer.render(object);
        OptionalReferenceNumberPart optionalReference = OptionalReferenceNumberPart.create(object, this.references, this.config);
        ListRenderingBuffer buffer = ListRenderingBuffer.create();
        buffer.addPart(optionalReference);
        buffer.addPart(objectRepresentation);
        return buffer;
    }

    public static StringerRepresentation create(StringerConfiguration config){
        StringerRepresentation representation = new StringerRepresentation();
        representation.references = IdentityReferences.create();
        representation.config = config;
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
        Optional<PartialBufferRenderer<Object>> primitiveRenderer = config.getRendererPerType().getBestPrimitiveRendererFor(object);
        if(primitiveRenderer.isPresent()){
            // It was a primitive value
            return primitiveRenderer.get().render(object);
        }
        // It's a complex object
        return representPossibleCircularRef(object);
    }
}
