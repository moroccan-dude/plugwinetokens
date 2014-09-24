package com.plugwine.exceptions;

/**
 * A <code>BusinessException</code> is an exception thrown usually from the manager layer
 */
public class BusinessException extends Exception {
	
	   /**
	     * 
	     */
	    private static final long serialVersionUID = -6300416886473575282L;
	    private String errorField;
	    
	    /**
	     * Default Constructor
	     */
	    public BusinessException() {
	    }

	    /**
	     * Constructor
	     * 
	     * @param cause Throwable
	     */
	    public BusinessException(Throwable cause) {
	        super(cause.getMessage(), cause);
	    }
	    
	    /**
	     * Constructor
	     * 
	     * @param field field that caused exception
	     * @param message Message
	     */
	    public BusinessException(String field, String message) {
	        super(message);
	        this.errorField = field;
	    }

	    /**
	     * Constructor
	     * 
	     * @param message Message
	     */
	    public BusinessException(String message) {
	        super(message);
	    }

	    /**
	     * Constructor
	     * 
	     * @param message Message
	     * @param cause Exception
	     */
	    public BusinessException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    
	    /**
	      * @return the field
	      */
	    public String getErrorField() {
	    	return errorField;
	    }

}
