package com.plugwine.manager.common;

import com.plugwine.manager.ComponentManager;
import com.plugwine.manager.ConfigurationVariableManager;
import com.plugwine.manager.ConfigurationVariableValueManager;

/**
 * The Service Factory allows the manager layer to access all managers
 * 
 **/
public class ServiceFactory {

    /**
     * The ConfigurationVariable Manager
     */
    private ConfigurationVariableManager configurationVariableManager;
    /**
     * The ConfigurationVariableValue Manager
     */
    private ConfigurationVariableValueManager configurationVariableValueManager;
    /**
     * 
     * @return
     */
    private ComponentManager componentManager;

    public ConfigurationVariableManager getConfigurationVariableManager() {
        return configurationVariableManager;
    }

    public void setConfigurationVariableManager(ConfigurationVariableManager configurationVariableManager) {
        this.configurationVariableManager = configurationVariableManager;
        this.configurationVariableManager.setServiceFactory(this);
    }

    public ConfigurationVariableValueManager getConfigurationVariableValueManager() {
        return configurationVariableValueManager;
    }

    public void setConfigurationVariableValueManager(ConfigurationVariableValueManager configurationVariableValueManager) {
        this.configurationVariableValueManager = configurationVariableValueManager;
        this.configurationVariableValueManager.setServiceFactory(this);
    }

	public ComponentManager getComponentManager() {
		return componentManager;
	}

	public void setComponentManager(ComponentManager componentManager) {
		this.componentManager = componentManager;
		 this.componentManager.setServiceFactory(this);
	}

  
}
