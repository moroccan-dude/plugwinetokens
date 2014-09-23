package com.plugwine.dao;


import java.util.List;

import com.plugwine.domain.model.ConfigurationVariable;

public interface ConfigurationVariableDao extends GenericDao<ConfigurationVariable, Long> 
{
	List<ConfigurationVariable> findAllVariables();
	
	ConfigurationVariable getVariableByName(String name);
}
