package com.plugwine.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plugwine.domain.holder.TokenHolder;
import com.plugwine.manager.ConfigurationVariableManager;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class TokensController {
	
	private static final Logger logger = LoggerFactory.getLogger(TokensController.class);
	
	@Autowired
	private ConfigurationVariableManager configurationVariableManager;
//	public void setConfigurationVariableManager(ConfigurationVariableManager configurationVariableManager)
//	{
//		this.configurationVariableManager = configurationVariableManager;
//	}
	
	@RequestMapping(value = TokensURIConstants.GET_ALL_TOKENS, method = RequestMethod.GET)
	public @ResponseBody List<TokenHolder> getAllTokens(String componentId) {
		logger.info("Start getAllTokens.");
		List<TokenHolder> allTokens = configurationVariableManager.findAllTokens(componentId);
		
		return allTokens;
	}
}
