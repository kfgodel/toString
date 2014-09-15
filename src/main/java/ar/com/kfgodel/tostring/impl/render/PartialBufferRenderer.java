package ar.com.kfgodel.tostring.impl.render;

/**
 * This type represents a type renderer that can represent a specific type into a buffer
 * Created by kfgodel on 14/09/14.
 */
public interface PartialBufferRenderer<T> {

    /**
     * Renders the state of the value into the given buffer
     * @param value The value to render (if any)
     * @return The buffer that contains the rendered value
     */
    RenderingBuffer render(T value);

}
