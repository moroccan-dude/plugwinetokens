package com.plugwine.dao.impl;


import java.util.List;

import com.plugwine.dao.ConfigurationVariableDao;
import com.plugwine.dao.impl.common.GenericDaoHibernate;
import com.plugwine.domain.model.ConfigurationVariable;

public class ConfigurationVariableDaoImpl extends GenericDaoHibernate<ConfigurationVariable, Integer> 
     implements ConfigurationVariableDao
{
	@Override
	public List<ConfigurationVariable> findAllVariables() {
		return findAll();
	}
	
	@Override
	public ConfigurationVariable getVariableByName(String name) {
		return getByUniqueProperty("name",name);
	}
	
}
