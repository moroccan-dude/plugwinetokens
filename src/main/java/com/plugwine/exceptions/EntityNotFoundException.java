package com.plugwine.exceptions;

import com.plugwine.web.ResultConstants;

public class EntityNotFoundException extends PlugwineException {

	private static final long serialVersionUID = 433947715367142444L;

	private static int EX_CODE = ResultConstants.STATUS_ENTITY_NOT_FOUND_ERROR_CODE;
	
	public EntityNotFoundException() {
		this(null,null);
    }

	public EntityNotFoundException(String message) {
    	this(message,null);
    }
    
    public EntityNotFoundException(String message, Throwable cause) 
    {
    	super(message, cause);
        setCode(EX_CODE);
    }
    
    @Override
    public int getCode()
	{
		return EX_CODE;
	}
}
