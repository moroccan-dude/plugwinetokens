package com.plugwine.manager.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.plugwine.dao.ConfigurationVariableDao;
import com.plugwine.domain.dto.VariableDto;
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

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationVariableManagerImpl.class);

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
	public VariableDto findVariableByName(String name)
	{
		PlugwineAssertionError.checkNotNull(name,getMessageSource().getMessage("variables.variable.alreadyExist"));
		ConfigurationVariable configurationVariable = getDao().getVariableByName(name);
		if(configurationVariable==null)
			return null;

		return new VariableDto(configurationVariable.getId(),(String)configurationVariable.getName(),formatVariableValues(configurationVariable));
	}

	@Override
	public List<VariableDto> findAllVariables()
	{
		List<ConfigurationVariable> variables =  getDao().findAllVariables();

		if(variables==null || variables.size()<=0)
			return new ArrayList<VariableDto>();

		ArrayList<VariableDto> tokens = new ArrayList<VariableDto>(variables.size());
		for (ConfigurationVariable variable : variables)
		{
			tokens.add(new VariableDto(variable.getId(),(String)variable.getName(),formatVariableValues(variable)));
		}

		return tokens;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public VariableDto deleteVariable(String varName)
	{
		ConfigurationVariable configurationVariable = getDao().getVariableByName(varName);
		PlugwineAssertionError.checkNotNull(configurationVariable,getMessageSource().getMessage("variables.variable.notFound",varName));

		getDao().delete(configurationVariable);

		return new VariableDto(configurationVariable.getId(),(String)configurationVariable.getName(),formatVariableValues(configurationVariable));
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public VariableDto addVariable(String name, String value)
	{
		VariableDto holder = findVariableByName(name);
		PlugwineAssertionError.checkFound(holder==null,getMessageSource().getMessage("variables.variable.alreadyExist"));

        ConfigurationVariable variable = null;



    	//valValue = getServiceFactory().getConfigurationVariableValueManager().persist(valValue);

    	variable= new ConfigurationVariable();
    	variable.setName(name);

    	Component component = getServiceFactory().getComponentManager().get(3951);
    	variable.setComponent(component);
    	variable.setDescription("some Dummy Descr");
    	variable.setIsDeleted(false);
    	variable.setIsParameter(true);
    	variable.setIsSystem(false);
    	variable.setTypeId(1);
    	variable = getDao().persist(variable);

    	ConfigurationVariableValueId valValueId = new ConfigurationVariableValueId();
          //hack
          int sId = 5;//default
          try {
          	String serverId = name.substring(name.length()-3, name.length());
          	sId = Integer.parseInt(serverId);
  		} catch (Exception  e) {
  			logger.warn("Hack of sId failed...make sure your variable name ends with a 3 digit number",e);
  		}

      	valValueId.setServerId(sId);
      	valValueId.setConfigurationVariableId(variable.getId());
      	valValueId.setApplicationVersionStageActivityId(16912);

    	ConfigurationVariableValue valValue = new ConfigurationVariableValue();
    	valValue.setId(valValueId);
    	valValue.setValue(value);
    	variable.getConfigurationVariableValues().add(valValue);
    	valValue = getServiceFactory().getConfigurationVariableValueManager().persist(valValue);

    	return  new VariableDto(variable.getId(),(String)variable.getName(),formatVariableValues(variable));
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
			stringVals += (String)val.getValue() + ",";
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
	public VariableDto updateVariable(VariableDto variableDto) {
    	ConfigurationVariable model = get(variableDto.getParamId());
    	PlugwineAssertionError.checkNotNull(model,getMessageSource().getMessage("variables.variable.notFound",variableDto.getParamName()));

    	/* update the name */
    	model.setName(variableDto.getParamName());
 		/* update the value */
    	Set<ConfigurationVariableValue> confVariableValues = model.getConfigurationVariableValues();
    	Iterator<ConfigurationVariableValue>  valIterator = confVariableValues.iterator();
    	if(valIterator.hasNext())
    	{
        	ConfigurationVariableValue value = valIterator.next();
        	value.setValue(variableDto.getParamValue());
    	}
    	ConfigurationVariable updated = updateEntityFromModel(model);

    	Iterator<ConfigurationVariableValue> updatedVal = updated.getConfigurationVariableValues().iterator();
    	String updateValue = null;
    	if(updatedVal.hasNext())
    	{
    		updateValue = (String)updatedVal.next().getValue();
    	}
    	return new VariableDto(updated.getId(), (String)updated.getName(),updateValue);
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
    	Set<ConfigurationVariableValue> newValues = model.getConfigurationVariableValues();
    	Set<ConfigurationVariableValue> newVarValues = new HashSet<ConfigurationVariableValue>();
    	for(ConfigurationVariableValue vals : newValues)
    	{
    		ConfigurationVariableValue confVariableValue = getServiceFactory().getConfigurationVariableValueManager().getByUniqueProperty("id", vals.getId().getId());
    		confVariableValue.setValue(vals.getValue());
    		newVarValues.add(confVariableValue);
    	}

    	if (newVarValues != null) {
             entity.setConfigurationVariableValues(new HashSet<ConfigurationVariableValue>(newVarValues));
    	}
    }
}
