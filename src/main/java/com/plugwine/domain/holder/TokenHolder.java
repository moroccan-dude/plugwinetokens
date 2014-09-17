package com.plugwine.domain.holder;


public class TokenHolder {
	
	private String paramName;
	private String paramValue;
	private String stageName;
	private String releasePathName;
	private String componentName;
	private String templateName;
	
	public TokenHolder(String paramName,String paramValue, String stageName,
			String releasePathName,String componentName, String templateName)
	{
		this.paramName = paramName;
		this.paramValue = paramValue;
		this.stageName = stageName;
		this.releasePathName = releasePathName;
		this.componentName = componentName;
		this.templateName = templateName;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getReleasePathName() {
		return releasePathName;
	}

	public void setReleasePathName(String releasePathName) {
		this.releasePathName = releasePathName;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
}
