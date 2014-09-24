package com.plugwine.exceptions;

import com.plugwine.web.ResultConstants;

public final class IllegalArgumentFoundException extends ValidationException  {

    private static int EX_CODE = ResultConstants.INVALID_ARGUMENT_ERROR_CODE;
    
    private static final long serialVersionUID = -4424121578067361781L;
    
	public IllegalArgumentFoundException() {
		this(null,null);
    }

	public IllegalArgumentFoundException(String message) {
    	this(message,null);
    }
    
    public IllegalArgumentFoundException(String message, Throwable cause) 
    {
    	super(message, cause);
        setCode(EX_CODE);
    }
}
