package ar.com.kfgodel.tostring.impl.render;

import java.util.List;

/**
 * This type represents the buffer in which the rendering is done before the actual string construction.<br>
 *     This allows us to do a 2 pass rendering, adding circular reference numbers in second pass
 * Created by kfgodel on 14/09/14.
 */
public interface RenderingBuffer extends CompositeRenderPart {

    /**
     * Prints the content of this buffer into a String, resolving references between parts
     * @return The string representation of this buffer
     */
    public String printOnString();

    /**
     * Adds a part to include in this buffer to be rendered
     * @param part The part to added in the rendering
     */
    void addPart(Object part);

}
