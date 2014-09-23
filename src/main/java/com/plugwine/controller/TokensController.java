package com.plugwine.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plugwine.domain.holder.TokenHolder;
import com.plugwine.domain.holder.VariableHolder;
import com.plugwine.manager.ConfigurationVariableManager;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class TokensController {
	
	private static final Logger logger = LoggerFactory.getLogger(TokensController.class);
	
	@Autowired
	private ConfigurationVariableManager configurationVariableManager;
	
	@RequestMapping(value = TokensURIConstants.GET_ALL_CONFVARIABLES, method = RequestMethod.GET)
	public @ResponseBody List<VariableHolder> getAllVariables() {
		logger.info("Start getAllTokens.");
		List<VariableHolder> allTokens = configurationVariableManager.findAllVariables();
		
		return allTokens;
	}
	
	
	@RequestMapping(value = TokensURIConstants.GET_VARIABLE, method = RequestMethod.GET)
	public @ResponseBody VariableHolder getVariable(@PathVariable("name") String variableName) {
		logger.info("Start getVariable. name=" + variableName + ".");
		
		return configurationVariableManager.findVariableByName(variableName);
	}
}
