package com.plugwine.domain.model;

// Generated Jun 9, 2014 11:41:12 AM by Hibernate Tools 3.4.0.CR1

/**
 * ConfigurationVariableValue generated by hbm2java
 */
public class ConfigurationVariableValue implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9169454773860096276L;
	private ConfigurationVariableValueId id;
	private ApplicationVersionStageActivity applicationVersionStageActivity;
	private ConfigurationVariable configurationVariable;

	public ConfigurationVariableValue() {
	}

	public ConfigurationVariableValue(ConfigurationVariableValueId id,
			ApplicationVersionStageActivity applicationVersionStageActivity,
			ConfigurationVariable configurationVariable) {
		this.id = id;
		this.applicationVersionStageActivity = applicationVersionStageActivity;
		this.configurationVariable = configurationVariable;
	}

	public ConfigurationVariableValueId getId() {
		return this.id;
	}

	public void setId(ConfigurationVariableValueId id) {
		this.id = id;
	}

	public ApplicationVersionStageActivity getApplicationVersionStageActivity() {
		return this.applicationVersionStageActivity;
	}

	public void setApplicationVersionStageActivity(
			ApplicationVersionStageActivity applicationVersionStageActivity) {
		this.applicationVersionStageActivity = applicationVersionStageActivity;
	}

	public ConfigurationVariable getConfigurationVariable() {
		return this.configurationVariable;
	}

	public void setConfigurationVariable(
			ConfigurationVariable configurationVariable) {
		this.configurationVariable = configurationVariable;
	}

	@Override
    public String toString() {
		if(getId()==null) 
			return null;
		return getClass().getSimpleName() + "[id:" +  getId();
      //  return getClass().getSimpleName() + "[id:" + getId().getId() + "], [value:" + getId().getValue() + "]";
    }
}
