package com.plugwine.manager.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.plugwine.dao.ConfigurationVariableDao;
import com.plugwine.domain.holder.VariableHolder;
import com.plugwine.domain.model.Component;
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
public class ConfigurationVariableManagerImpl extends GenericManagerImpl<ConfigurationVariable, Integer> 
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
		
		return new VariableHolder(configurationVariable.getId(),(String)configurationVariable.getName(),formatVariableValues(configurationVariable));
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
			tokens.add(new VariableHolder(variable.getId(),(String)variable.getName(),formatVariableValues(variable)));
		}
		
		return tokens;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
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
        	valValue = getServiceFactory().getConfigurationVariableValueManager().persist(valValue);
        	
        	variable= new ConfigurationVariable();
        	variable.setName(name);
        	variable.getConfigurationVariableValues().add(valValue);
        	Component component = getServiceFactory().getComponentManager().get(3951);
        	variable.setComponent(component);
        	variable.setDescription("some Dummy Descr");
        	variable.setIsDeleted(false);
        	variable.setIsParameter(true);
        	variable.setIsSystem(false);
        	variable.setTypeId(1);
        	variable = getDao().persist(variable);
//        }
//        catch(Exception exception) 
//        {
//        
//        }
        return  new VariableHolder(variable.getId(),(String)variable.getName(),formatVariableValues(variable));
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

	/**
     * {@inheritDoc}
     */
    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public VariableHolder updateVariable(VariableHolder variableHolder) {
    	ConfigurationVariable model = get(variableHolder.getParamId());
    	PlugwineAssertionError.checkNotNull(model,getMessageSource().getMessage("variables.variable.notFound",variableHolder.getParamName()));
    	
    	/* update the name */
    	model.setName(variableHolder.getParamName());
 		/* update the value */
    	Set<ConfigurationVariableValue> confVariableValues = model.getConfigurationVariableValues();
    	Iterator<ConfigurationVariableValue>  valIterator = confVariableValues.iterator();
    	if(valIterator.hasNext())
    	{
        	ConfigurationVariableValue value = valIterator.next();
        	value.getId().setValue(variableHolder.getParamValue());
    	}
    	ConfigurationVariable updated = updateEntityFromModel(model);
    	
    	Iterator<ConfigurationVariableValue> updatedVal = updated.getConfigurationVariableValues().iterator();
    	String updateValue = null;
    	if(updatedVal.hasNext())
    	{
    		updateValue = (String)updatedVal.next().getId().getValue();
    	}
    	return new VariableHolder(updated.getId(), (String)updated.getName(),updateValue); 
	}
	
	/**
     * {@inheritDoc}
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ConfigurationVariable updateEntityFromModel(ConfigurationVariable model) {
    	ConfigurationVariable entity = loadEntityFromModel(model);
        if (entity == null) {
            return null;
        }
        mergeModelIntoEntity(model, entity);
        return persist(entity);
    }

    
    private ConfigurationVariable loadEntityFromModel(ConfigurationVariable model) {
        if (model.getId() != null) { /* re-attach it */
            return get(model.getId());
        } else {
            return new ConfigurationVariable();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void mergeModelIntoEntity(ConfigurationVariable model, ConfigurationVariable entity) {
//    	entity.setComponent(model.getComponent());
//    	entity.setDescription(model.getDescription());
//    	entity.setIsDeleted(model.isIsDeleted());
//    	entity.setIsParameter(model.isIsParameter());
//    	entity.setIsSystem(model.isIsSystem());
//    	entity.setTypeId(model.getTypeId());
    	// only update the values
    	Set<ConfigurationVariableValue> values = model.getConfigurationVariableValues();
//    	List<Integer> valueIds = new ArrayList<Integer>();
    	//Set<ConfigurationVariableValue> newValues = new HashSet<ConfigurationVariableValue>();
    	for(ConfigurationVariableValue vals : values)
    	{
    		ConfigurationVariableValue confVariableValue = getServiceFactory().getConfigurationVariableValueManager().getByUniqueProperty("id", vals.getId().getId());
    		confVariableValue.getId().setValue(vals.getId().getValue());
    		//confVariableValue.getId().setServerId(vals.getId().getServerId());
    		//newValues.add(confVariableValue);
    		entity.getConfigurationVariableValues().add(confVariableValue);
    	}
//    	List<ConfigurationVariableValue> confVariableValues = getServiceFactory().getConfigurationVariableValueManager().getAllByIds(valueIds);
//    	for(ConfigurationVariableValue valsToUpdate : confVariableValues)
//    	{
//    		confVariableValues.c
//    	}
    	
    	//if (newValues != null) {
         //    entity.setConfigurationVariableValues(new HashSet<ConfigurationVariableValue>(newValues));
    	//}
    }
}
