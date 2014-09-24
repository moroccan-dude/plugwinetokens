package com.plugwine.manager;

import java.util.List;

import com.plugwine.domain.holder.VariableHolder;
import com.plugwine.domain.model.ConfigurationVariable;


public interface ConfigurationVariableManager extends GenericManager<ConfigurationVariable, Long> {

	public List<VariableHolder> findAllVariables(); 
	
	public VariableHolder findVariableByName(String name);
	
	public VariableHolder addVariable(String name, String value); 
}
