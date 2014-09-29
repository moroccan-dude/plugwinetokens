package com.plugwine.dao;


import java.util.List;

import com.plugwine.dao.common.GenericDao;
import com.plugwine.domain.model.ConfigurationVariable;

public interface ConfigurationVariableDao extends GenericDao<ConfigurationVariable, Integer> 
{
	List<ConfigurationVariable> findAllVariables();
	
	ConfigurationVariable getVariableByName(String name);
}
