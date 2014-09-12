package ar.com.kfgodel.tostring.impl.renderer;

/**
 * This type serves as the base contract for string renderers used in portions of the string representation.<br>
 *     There are diferent renderers that can be used for primitive values
 * Created by kfgodel on 11/09/14.
 */
public interface PartialRenderer<T> {

    /**
     * Generates a string representation of the given value
     * @param value The valur to render (if any)
     * @return The rendered representation of the value
     */
    String render(T value);
}
