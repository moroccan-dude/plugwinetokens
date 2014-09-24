package com.plugwine.manager;

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

  
}
