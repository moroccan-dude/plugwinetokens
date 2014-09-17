package com.plugwine.manager;

import java.util.List;

import com.plugwine.domain.holder.TokenHolder;
import com.plugwine.domain.model.ConfigurationVariable;

public interface ConfigurationVariableManager extends GenericManager<ConfigurationVariable, Long> {

	public List<TokenHolder> findAllTokens(String componentId); 
	
}
