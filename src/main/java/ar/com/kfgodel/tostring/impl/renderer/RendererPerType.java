package ar.com.kfgodel.tostring.impl.renderer;

import ar.com.kfgodel.tostring.impl.PendingRendering;
import ar.com.kfgodel.tostring.impl.properties.ObjectField;
import ar.com.kfgodel.tostring.impl.render.PartialBufferRenderer;
import ar.com.kfgodel.tostring.impl.render.imple.*;
import ar.com.kfgodel.tostring.impl.renderer.partials.*;

import javax.lang.model.type.NullType;
import java.lang.reflect.Array;
import java.util.*;

/**
 * This type knows which renderer mus be used according to an object type
 * Created by kfgodel on 13/09/14.
 */
public class RendererPerType {

    private Map<Class<?>, PartialBufferRenderer<Object>> primitiveRenderers;
    private Map<Class<?>, PartialBufferRenderer<Object>> complexRenderers;
    private PartialBufferRenderer<Object> objectRenderer;

    public static RendererPerType create() {
        RendererPerType rendererPerType = new RendererPerType();
        rendererPerType.initializeMappings();
        return rendererPerType;
    }

    /**
     * Defines the mapping between classes and renderers
     */
    private void initializeMappings() {
        this.initializePrimitiveMappings();
        this.initializeComplexMappings();
        this.objectRenderer = ObjectBufferRenderer.create();
    }

    /**
     * Defines mapping between complex types and their renderers
     */
    private void initializeComplexMappings() {
        this.complexRenderers = new LinkedHashMap<>();
        this.complexRenderers.put(Array.class,(PartialBufferRenderer) ArrayBufferRenderer.create());
        this.complexRenderers.put(Collection.class,(PartialBufferRenderer) CollectionBufferRenderer.create());
        this.complexRenderers.put(Map.class,(PartialBufferRenderer) MapBufferRenderer.create());
    }

    /**
     * Defines mappings between primitive types and their renderers
     */
    private void initializePrimitiveMappings() {
        this.primitiveRenderers = new LinkedHashMap<>();
        this.primitiveRenderers.put(Void.class, (PartialBufferRenderer) NullBufferRenderer.create());
        this.primitiveRenderers.put(Number.class, (PartialBufferRenderer) NumberBufferRenderer.create());
        this.primitiveRenderers.put(Character.class, (PartialBufferRenderer) CharBufferRenderer.create());
        this.primitiveRenderers.put(CharSequence.class, (PartialBufferRenderer) CharSequenceBufferRenderer.create());
    }

    /**
     * Returns the best primitive renderer for the given object type (if object is considered primitive)
     * @param object The object to render
     * @return The optional found rendere, or an empty optional if object is not a primitive
     */
    public Optional<PartialBufferRenderer<Object>> getBestPrimitiveRendererFor(Object object) {
        Class<?> objectType;
        if(object == null){
            // Null doesn't have a class. We force one
            objectType = Void.class;
        } else{
          objectType = object.getClass();
        }
        return findBestRendererIn(this.primitiveRenderers, objectType);
    }

    /**
     * Looks for the best renderer in the given map, iterating its entries to find a supertype of the given objectType
     * @param mappingsMap The mappings to look into
     * @param objectType The type to find a renderer for
     * @return The best renderer found for a super type of the given type, or an empty optional
     */
    private Optional<PartialBufferRenderer<Object>> findBestRendererIn(Map<Class<?>, PartialBufferRenderer<Object>> mappingsMap, Class<?> objectType) {
        Set<Map.Entry<Class<?>, PartialBufferRenderer<Object>>> mappings = mappingsMap.entrySet();
        for (Map.Entry<Class<?>, PartialBufferRenderer<Object>> mapping : mappings) {
            Class<?> mappedType = mapping.getKey();
            if(mappedType.isAssignableFrom(objectType)){
                PartialBufferRenderer<Object> bestRenderer = mapping.getValue();
                return Optional.of(bestRenderer);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns the best renderer for given object according to its type and selecting it from
     * the complex renderers
     * @param object The object to render
     * @return The selected best renderer
     */
    public PartialBufferRenderer<Object> getBestComplexRendererFor(Object object) {
        Class<?> objectType = object.getClass();
        if(objectType.isArray()){
            // Arrays don't have a common ancestor, we force one
            objectType = Array.class;
        }
        Optional<PartialBufferRenderer<Object>> bestRender = findBestRendererIn(this.complexRenderers, objectType);
        return bestRender.orElse(this.objectRenderer);
    }
}
