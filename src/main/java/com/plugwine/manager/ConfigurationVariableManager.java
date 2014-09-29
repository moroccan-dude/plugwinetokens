package com.plugwine.manager;

import java.util.List;

import com.plugwine.domain.dto.VariableDto;
import com.plugwine.domain.model.ConfigurationVariable;


public interface ConfigurationVariableManager extends GenericManager<ConfigurationVariable, Integer> {

	public List<VariableDto> findAllVariables(); 
	
	public VariableDto findVariableByName(String name);
	
	public VariableDto addVariable(String name, String value);
	
	public VariableDto updateVariable(VariableDto variable);
	
	public VariableDto deleteVariable(String name);
	
}
