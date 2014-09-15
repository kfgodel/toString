package ar.com.kfgodel.tostring.impl.render;

/**
 * This type represents a string render of a value or object representation.<br>
 *     A render is used to reflect state of an object into a StringBuilder for final String result
 * Created by kfgodel on 14/09/14.
 */
public interface StringRender {

    /**
     * Reflects this render as string into the given StringBuilder
     * @param buffer The builder to print this render as String
     */
    public void printOn(RenderingBuffer buffer);
}
