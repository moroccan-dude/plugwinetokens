package com.plugwine.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.plugwine.dao.ConfigurationVariableDao;
import com.plugwine.domain.holder.VariableHolder;
import com.plugwine.domain.model.ConfigurationVariable;
import com.plugwine.domain.model.ConfigurationVariableValue;
import com.plugwine.domain.model.ConfigurationVariableValueId;
import com.plugwine.manager.ConfigurationVariableManager;
import com.plugwine.util.PlugwineAssertionError;


// Note:
// For some unknown reason the transational annotation at the class level does not always work.
// Therefore, if a manager method requires a transaction execution, make sure to annotate it here directly at the method level.
// (i.e do not solely rely on the annotations defined in GenericManagerImpl.
@Service
public class ConfigurationVariableManagerImpl extends GenericManagerImpl<ConfigurationVariable, Long> 
implements ConfigurationVariableManager {

//	@Autowired
//    private ConfigurationVariableDao configurationVariableDao;
//	public void setConfigurationVariableDao(ConfigurationVariableDao configurationVariableDao) 
//	{
//		this.configurationVariableDao = configurationVariableDao;
//	}
	public ConfigurationVariableManagerImpl()
	{
		super();
	}
	
	public ConfigurationVariableManagerImpl(ConfigurationVariableDao dao)
	{
		super(dao);
	}
	
	protected ConfigurationVariableDao getDao() {
        return (ConfigurationVariableDao) getGenericDao();
    }
	
	@Override
	public VariableHolder findVariableByName(String name)
	{
		PlugwineAssertionError.checkNotNull(name,getMessageSource().getMessage("variables.variable.alreadyExist"));
		ConfigurationVariable configurationVariable = getDao().getVariableByName(name);
		if(configurationVariable==null)
			return null;
		
		return new VariableHolder((String)configurationVariable.getName(),formatVariableValues(configurationVariable));
	}
	
	@Override
	public List<VariableHolder> findAllVariables() 
	{
		List<ConfigurationVariable> variables =  getDao().findAllVariables();
		
		if(variables==null || variables.size()<=0)
			return new ArrayList<VariableHolder>();
		
		ArrayList<VariableHolder> tokens = new ArrayList<VariableHolder>(variables.size());
		for (ConfigurationVariable variable : variables)
		{	
			tokens.add(new VariableHolder((String)variable.getName(),formatVariableValues(variable)));
		}
		
		return tokens;
	}
	
	@Override
	public VariableHolder addVariable(String name, String value)
	{
		VariableHolder holder = findVariableByName(name);
		PlugwineAssertionError.checkFound(holder==null,getMessageSource().getMessage("variables.variable.alreadyExist"));
        
        ConfigurationVariable variable = null;
//        try 
//        {
        	ConfigurationVariableValueId valValueId = new ConfigurationVariableValueId();
        	valValueId.setValue(value);
        	valValueId.setServerId(3);
        	valValueId.setConfigurationVariableId(10510);
        	valValueId.setApplicationVersionStageActivityId(16912);
        	
        	ConfigurationVariableValue valValue = new ConfigurationVariableValue();
        	valValue.setId(valValueId);
        	getServiceFactory().getConfigurationVariableValueManager().persist(valValue);
        	
        	variable= new ConfigurationVariable();
        	variable.setName((String)name);
        	variable.getConfigurationVariableValues().add(valValue);
        	
        	variable = getDao().persist(variable);
//        }
//        catch(Exception exception) 
//        {
//        
//        }
        return  new VariableHolder((String)variable.getName(),formatVariableValues(variable));
	}

	@Override
	protected void loadDependencies(ConfigurationVariable entity) {
		if (entity != null) {
			//System.out.println("is inited? " + ((PersistentCollection) entity.getConfigurationVariableValues()).wasInitialized());
            Hibernate.initialize(entity.getConfigurationVariableValues());
            //System.out.println("is inited? " + ((PersistentCollection) entity.getConfigurationVariableValues()).wasInitialized());
        }
		
	}
	
	private String formatVariableValues(ConfigurationVariable variable)
	{
		Set<ConfigurationVariableValue> values = variable.getConfigurationVariableValues();
		String stringVals = "";
		for(ConfigurationVariableValue val : values)
		{
			stringVals += (String)val.getId().getValue() + ","; 
		}
		if (stringVals.endsWith(",")) 
			stringVals = stringVals.substring(0,stringVals.length()-1);
		
		return stringVals;
	}

}
