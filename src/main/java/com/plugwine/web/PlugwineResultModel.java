package com.plugwine.web;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plugwine.util.Objects;

@XmlRootElement(name = "result")
public class PlugwineResultModel {
	
	@JsonProperty("status")
	private int status;
	
	@JsonProperty(value = "message")
    private String message = null;
 
	@JsonProperty("details")
	private String details = null;

	@JsonProperty("data")
	private Object data = null;
	
    public PlugwineResultModel()
    { /* default constructor */ }
    
    public PlugwineResultModel(int status, String message, String details, Object data) {
    		setStatus(status);
    		setMessage(message);
    		setDetails(details);
    		setData(data);
    }
 

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	
    public static boolean isFailure(PlugwineResultModel result)
	{
		return !isSuccess(result);
	}
	
	public static boolean isSuccess(PlugwineResultModel result)
	{
		if(result==null)
			return false;
		
		if(result.getStatus()==ResultConstants.STATUS_CODE_OK)
			return true;
		
		return false;
	}
	
	public static PlugwineResultModel successResult(Object data)
	{
		PlugwineResultModel resultModel = new PlugwineResultModel();
		resultModel.setStatus(ResultConstants.STATUS_CODE_OK);
		resultModel.setMessage(ResultConstants.MESSAGE_CODE_OK);
		if(data!=null)
			resultModel.setData(data);
		
		return resultModel;
	}
	
	public static PlugwineResultModel successResult()
	{
		return successResult(null);
	}
	
	public static PlugwineResultModel failureResult(int statusCode, String message)
	{
		return failureResult(statusCode, message, null);
	}
	
	public static PlugwineResultModel failureResult(int statusCode, String message, String details)
	{
		PlugwineResultModel resultModel = new PlugwineResultModel();
		resultModel.setStatus(statusCode);
		resultModel.setMessage(message);
		if(details!=null)
			resultModel.setDetails(details);
		return resultModel;
	}
	
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(71, 35);
        builder.append(status);
        builder.append(message);
        builder.append(details);
       
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        return this.equalsValues(object);
    }
    
    boolean equalsValues(Object object) 
    {
        if (object == this) {
            return true;
        } else if (object == null) {
            return false;
        } else if (!(object instanceof PlugwineResultModel)) {
            return false;
        }

        PlugwineResultModel other = (PlugwineResultModel) object;
        if (!Objects.equals(getStatus(), other.getStatus())) {
            return false;
        }
        if (!Objects.equals(getMessage(), other.getMessage())) {
            return false;
        }
        if (!Objects.equals(getDetails(), other.getDetails())) {
            return false;
        }
        
        return true;
    }
}
