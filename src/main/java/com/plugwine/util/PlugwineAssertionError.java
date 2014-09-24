package com.plugwine.util;

import org.springframework.http.HttpStatus;

import com.plugwine.exceptions.IllegalArgumentFoundException;

/**
 * Simple static methods to be called at the start of your own methods to verify correct arguments and state. If the Precondition fails, an {@link HttpStatus} code is thrown
 */
public final class PlugwineAssertionError {

    private PlugwineAssertionError() {
        throw new AssertionError();
    }
    
    /**
     * Check if some value was found, otherwise throw exception.
     * 
     * @param expression
     *            has value true if found, otherwise false
     * @throws IllegalArgumentFoundException
     *             if expression is false, means value not found.
     */
    public static void checkFound(final boolean expression) {
        if (!expression) {
            throw new IllegalArgumentFoundException();
        }
    }
    
    /**
     * 
     * @param expression
     * @param errorMessage
     */
    public static void checkFound(final boolean expression , String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentFoundException(errorMessage);
        }
    }

    /**
     * Check if some object is not null, otherwise throw exception.
     * 
     * @param expression
     *            has value true if found, otherwise false
     * @throws IllegalArgumentFoundException
     *             if expression is false, means value not found.
     */
    public static <T> T checkNotNull(final T object) {
        if (object == null) {
            throw new IllegalArgumentFoundException();
        }

        return object;
    }
    
    /**
     * Check if some object is not null, otherwise throw exception.
     * 
     * @param expression
     *            has value true if found, otherwise false
     * @param errorMessage
     *            the exception error message in case of the assertion fails.  
     * @throws IllegalArgumentFoundException
     *             if expression is false, means value not found.
     */
    public static <T> T checkNotNull(final T object, String errorMessage) {
        if (object == null) {
            throw new IllegalArgumentFoundException(errorMessage);
        }

        return object;
    }
    
    /**
     * 
     * @param object
     * @param ex
     * @return
     */
    public static <T> T checkNotNull(final T object, RuntimeException ex) {
        if (object == null) {
            throw ex;
        }

        return object;
    }

}
