package com.plugwine.manager.impl;


import org.springframework.stereotype.Service;

import com.plugwine.dao.ConfigurationVariableValueDao;
import com.plugwine.domain.model.ConfigurationVariableValue;
import com.plugwine.manager.ConfigurationVariableValueManager;


@Service
public class ConfigurationVariableValueManagerImpl extends GenericManagerImpl<ConfigurationVariableValue, Long> 
implements ConfigurationVariableValueManager {

	public ConfigurationVariableValueManagerImpl()
	{
		super();
	}
	
	public ConfigurationVariableValueManagerImpl(ConfigurationVariableValueDao dao)
	{
		super(dao);
	}
	
	protected ConfigurationVariableValueDao getDao() {
        return (ConfigurationVariableValueDao) getGenericDao();
    }

	@Override
	protected void loadDependencies(ConfigurationVariableValue entity) {
	}
}
