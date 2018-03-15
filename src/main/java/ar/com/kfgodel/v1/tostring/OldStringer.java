package ar.com.kfgodel.v1.tostring;

import ar.com.kfgodel.v1.tostring.config.DefaultConfiguration;
import ar.com.kfgodel.v1.tostring.config.StringerConfiguration;
import ar.com.kfgodel.v1.tostring.impl.StringerContext;

/**
 * This type represents the API facade for getting a String representation of objects
 * Created by kfgodel on 09/09/14.
 */
public interface OldStringer {

    /**
     * The global configuration used when doing the representations
     */
    public static StringerConfiguration CONFIGURATION = DefaultConfiguration.create();

    /**
     * Creates a String representation of given object.<br>
     * This conversion follows certain rules to create a compact but useful default String representation of any object
     * @param object The object to be represented
     * @return The String representation of the object state
     */
    public static String representationOf(Object object){
        return StringerContext.doWithActiveRepresentation(
                (representation) ->
                        representation.represent(object)
        );
    }
}
