package com.plugwine.manager.impl;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.plugwine.dao.ConfigurationVariableDao;
import com.plugwine.domain.holder.TokenHolder;
import com.plugwine.domain.model.ConfigurationVariable;
import com.plugwine.manager.ConfigurationVariableManager;

@Service
public class ConfigurationVariableManagerImpl extends GenericManagerImpl<ConfigurationVariable, Long> 
implements ConfigurationVariableManager {

	@Autowired
    private ConfigurationVariableDao configurationVariableDao;
	public void setConfigurationVariableDao(ConfigurationVariableDao configurationVariableDao) 
	{
		this.configurationVariableDao = configurationVariableDao;
	}

	@Override
	public List<TokenHolder> findAllTokens(String componentId) 
	{
		return configurationVariableDao.findAllTokens(componentId);
	}

	@Override
	protected void loadDependencies(ConfigurationVariable entity) {
		// TODO Auto-generated method stub
		
	}

}
