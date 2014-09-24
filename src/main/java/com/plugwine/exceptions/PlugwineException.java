package com.plugwine.exceptions;

public class PlugwineException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 558763330453003871L;
	private int code;
	
	public PlugwineException()
	{}
	
	public PlugwineException(int code)
	{
		setCode(code);
	}

    public PlugwineException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PlugwineException(final String message) {
        super(message);
    }

    public PlugwineException(final Throwable cause) {
        super(cause);
    }
    
	public int getCode()
	{
		return this.code;
	}
	
	protected void setCode(int code)
	{
		this.code = code;
	}
}
