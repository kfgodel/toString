package ar.com.kfgodel.tostring.impl.renderer;

import ar.com.kfgodel.tostring.impl.PendingRendering;
import ar.com.kfgodel.tostring.impl.properties.ObjectField;
import ar.com.kfgodel.tostring.impl.renderer.partials.*;

import javax.lang.model.type.NullType;
import java.lang.reflect.Array;
import java.util.*;

/**
 * This type knows which renderer mus be used according to an object type
 * Created by kfgodel on 13/09/14.
 */
public class RendererPerType {

    private Map<Class<?>, PartialRenderer<Object>> primitiveRenderers;
    private Map<Class<?>, PartialRenderer<Object>> complexRenderers;

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
    }

    /**
     * Defines mapping between complex types and their renderers
     */
    private void initializeComplexMappings() {
        this.complexRenderers = new LinkedHashMap<>();
        this.complexRenderers.put(Array.class,(PartialRenderer) ArrayRenderer.INSTANCE);
        this.complexRenderers.put(Collection.class,(PartialRenderer) CollectionRenderer.INSTANCE);
        this.complexRenderers.put(Map.class,(PartialRenderer) MapRenderer.INSTANCE);
        this.complexRenderers.put(Map.Entry.class, (PartialRenderer) MapEntryRenderer.INSTANCE);
        this.complexRenderers.put(ObjectField.class, (PartialRenderer) ObjectFieldRenderer.INSTANCE);
    }

    /**
     * Defines mappings between primitive types and their renderers
     */
    private void initializePrimitiveMappings() {
        this.primitiveRenderers = new LinkedHashMap<>();
        this.primitiveRenderers.put(Void.class, (PartialRenderer) NullRenderer.INSTANCE);
        this.primitiveRenderers.put(Number.class, (PartialRenderer) NumberRenderer.INSTANCE);
        this.primitiveRenderers.put(Character.class, (PartialRenderer) CharRenderer.INSTANCE);
        this.primitiveRenderers.put(CharSequence.class, (PartialRenderer) CharSequenceRenderer.INSTANCE);
    }

    /**
     * Returns the best primitive renderer for the given object type (if object is considered primitive)
     * @param object The object to render
     * @return The optional found rendere, or an empty optional if object is not a primitive
     */
    public Optional<PartialRenderer<Object>> getBestPrimitiveRendererFor(Object object) {
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
    private Optional<PartialRenderer<Object>> findBestRendererIn(Map<Class<?>, PartialRenderer<Object>> mappingsMap, Class<?> objectType) {
        Set<Map.Entry<Class<?>, PartialRenderer<Object>>> mappings = mappingsMap.entrySet();
        for (Map.Entry<Class<?>, PartialRenderer<Object>> mapping : mappings) {
            Class<?> mappedType = mapping.getKey();
            if(mappedType.isAssignableFrom(objectType)){
                PartialRenderer<Object> bestRenderer = mapping.getValue();
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
    public PartialRenderer<Object> getBestComplexRendererFor(Object object) {
        Class<?> objectType = object.getClass();
        if(objectType.isArray()){
            // Arrays don't have a common ancestor, we force one
            objectType = Array.class;
        }
        Optional<PartialRenderer<Object>> bestRender = findBestRendererIn(this.complexRenderers, objectType);
        return bestRender.orElse(ObjectRenderer.INSTANCE);
    }
}
