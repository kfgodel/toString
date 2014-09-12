package ar.com.kfgodel.tostring.impl;

import java.util.function.Function;

/**
 * This type represents the context of a Stringer representation
 * Created by kfgodel on 11/09/14.
 */
public class StringerContext {

    private static final ThreadLocal<StringerRepresentation> THREAD_CONTEXT = new ThreadLocal<>();

    /**
     * Executes given action with current representation, creating one if there's none.<br>
     * @param action Action to execute with active representation
     * @return The result of the action
     */
    public static<T> T doWithActiveRepresentation(Function<StringerRepresentation, T> action) {
        StringerRepresentation existingRepresentation = THREAD_CONTEXT.get();
        if(existingRepresentation != null){
            //We don't need to create one
            return executeOn(existingRepresentation, action);
        }
        return executeWithNewRepresentation(action);

    }

    /**
     * Creates a new representation in context and executes the action in it
     * @param action The action to execute
     * @param <T> Type of expected result
     * @return The result of the action
     */
    private static <T> T executeWithNewRepresentation(Function<StringerRepresentation, T> action) {
        //There's no previous. We create and set it in context for the duration of the action
        StringerRepresentation createdRepresentation = StringerRepresentation.create();
        THREAD_CONTEXT.set(createdRepresentation);
        try{
            return executeOn(createdRepresentation, action);
        }finally {
            THREAD_CONTEXT.remove();
        }
    }

    /**
     * Applies the given action on the representation and returns the result
     * @param existingRepresentation The representation to use
     * @param action The action to apply
     * @param <T> Type of result
     * @return The result of the action
     */
    private static <T> T executeOn(StringerRepresentation existingRepresentation, Function<StringerRepresentation, T> action) {
        return action.apply(existingRepresentation);
    }


}
