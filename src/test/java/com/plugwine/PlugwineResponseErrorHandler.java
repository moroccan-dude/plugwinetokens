package com.plugwine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;



public class PlugwineResponseErrorHandler implements ResponseErrorHandler 
{
	private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();
	
	Map<String, Object> properties = new HashMap<String, Object>();
	static public final String CODE_KEY    = "code";
	static public final String BODY_KEY    = "body";
	static public final String HEADER_KEY  = "header";
	
	public Map<String, Object> getErrorDetails()
	{
		return properties;
	}
	
	public boolean hasError()
	{
		String status =  (String) properties.get(CODE_KEY);
		try 
		{
			return (status!=null && Integer.parseInt(status) != HttpStatus.SC_OK);	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return true;
		}
		
	}
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return errorHandler.hasError(response);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException 
	{
		String theString = IOUtils.toString(response.getBody());
	    
	    properties.put(CODE_KEY, response.getStatusCode().toString());
	    properties.put(BODY_KEY, theString);
	    properties.put(HEADER_KEY, response.getHeaders());
	    //properties.
	    //System.out.println(properties.toString());
	   // exception.setProperties(properties);
	    //throw exception;
	}
}
