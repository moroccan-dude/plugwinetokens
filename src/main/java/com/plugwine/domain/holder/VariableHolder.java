package com.plugwine.domain.holder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "variable")
public class VariableHolder {

	private String paramName;
	private String paramValue;
	
	public String getParamName() {
		return paramName;
	}

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
	
	public VariableHolder(String paramName,String paramValue)
	{
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
	
	public String toString()
	{
		return this.paramName + "," + this.paramValue;
	}
}
