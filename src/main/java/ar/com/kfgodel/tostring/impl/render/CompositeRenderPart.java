package ar.com.kfgodel.tostring.impl.render;

import java.util.List;

/**
 * This type represents a a render part that is composed of subparts
 * Created by kfgodel on 14/09/14.
 */
public interface CompositeRenderPart {
    /**
     * @return The parts that compose this composite (they can be composites too)
     */
    List<Object> getParts();

    /**
     * @return The estimated size that this buffer will require in the final string
     */
    int getEstimatedSize();

}
