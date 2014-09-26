package com.plugwine.domain.holder;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "variable")
public class VariableHolder {

	@JsonProperty("id")
	private Integer paramId;
	@JsonProperty("name")
	private String paramName;
	@JsonProperty("val")
	private String paramValue;
	
	public Integer getParamId() {
		return paramId;
	}

	@XmlElement(name="id")
	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}
	
	public String getParamName() {
		return paramName;
	}

	//By default a JAXB (JSR-222) implementation examines the public accessor methods. 
	@XmlElement(name="name")
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	@XmlElement(name="value")
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public VariableHolder()
	{
		/*Default constructor  */
	}
	
	public VariableHolder(Integer paramId, String paramName,String paramValue)
	{
		this.paramId = paramId;
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
	
	public String toString()
	{
		return "{" + this.paramId + "," + this.paramName + "," + this.paramValue + "}";
	}
}
