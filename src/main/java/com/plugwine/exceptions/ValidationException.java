package com.plugwine.exceptions;

import com.plugwine.web.ResultConstants;

/** 
 * Basic API Validation exception
 *
 */
public class ValidationException extends PlugwineException 
{
	private static final long serialVersionUID = 8729941491995675879L;

	private static int EX_CODE = ResultConstants.STATUS_VALIDATION_ERROR_CODE;
	
	public ValidationException() {
		this(null,null);
    }

	public ValidationException(String message) {
    	this(message,null);
    }
    
    public ValidationException(String message, Throwable cause) 
    {
    	super(message, cause);
        setCode(EX_CODE);
    }

}
