package com.plugwine.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plugwine.domain.holder.VariableHolder;
import com.plugwine.exceptions.EntityNotFoundException;
import com.plugwine.i18n.IMessageSource;
import com.plugwine.manager.ConfigurationVariableManager;
import com.plugwine.util.PlugwineAssertionError;
import com.plugwine.web.PlugwineResultModel;

/**
 * Handles requests for the ConfigurationVariable Manager.
 */
@Controller
public class TokensController {
	
	private static final Logger logger = LoggerFactory.getLogger(TokensController.class);
	
	@Autowired
	private ConfigurationVariableManager configurationVariableManager;
	
	@RequestMapping(value = TokensURIConstants.CTX_VARIABLE, method = RequestMethod.GET)
	public @ResponseBody PlugwineResultModel getAllVariables() {
		logger.debug("Start getAllTokens.");
		
		List<VariableHolder> allTokens = configurationVariableManager.findAllVariables();
		return PlugwineResultModel.successResult(allTokens);
		//return PlugwineResultModel.successResult(new com.plugwine.domain.holder.PlugwineList<VariableHolder> (allTokens));
	}
	
	/**
	 * if not found, returns an error
	 * @param variableName
	 * @return
	 */
	@RequestMapping(value = TokensURIConstants.CTX_VARIABLE_NAME, method = RequestMethod.GET)
	public @ResponseBody PlugwineResultModel getVariable(@PathVariable("name") String variableName) {
		logger.debug("Start getVariable. name=" + variableName + ".");
		
		VariableHolder variableHolder =  configurationVariableManager.findVariableByName(variableName);
		PlugwineAssertionError.checkNotNull(variableHolder, 
				new EntityNotFoundException(getMessageSource().getMessage("variables.variable.notFound","[name="+variableName+"]")));
		
		return PlugwineResultModel.successResult(variableHolder);
	}
	
	/**
	 * Same as get, but does not return null if not found.
	 * @param variableName
	 * @return
	 */
	@RequestMapping(value = TokensURIConstants.CTX_VARIABLE_SEARCH_NAME, method = RequestMethod.GET)
	public @ResponseBody PlugwineResultModel findVariable(@PathVariable("name") String variableName) {
		logger.debug("Start getVariable. name=" + variableName + ".");
		
		VariableHolder variableHolder =  configurationVariableManager.findVariableByName(variableName);
		return PlugwineResultModel.successResult(variableHolder);
	}
	
	//@Requestbody converts the contents of incoming request body to method's parameter object using the messageconverters
	//Same with @responsebody
	@RequestMapping(value = TokensURIConstants.CTX_VARIABLE, method = RequestMethod.POST)
	public @ResponseBody PlugwineResultModel createVariable(@RequestBody VariableHolder variable) {
		PlugwineAssertionError.checkNotNull(variable,
				new EntityNotFoundException(getMessageSource().getMessage("general.error.missingRequiredParam","variable")));
		
		logger.debug("Start add Variable. name=" + variable.getParamName() + ", value=" + variable.getParamValue() + ".");
		
		VariableHolder variableHolder = configurationVariableManager.addVariable(variable.getParamName(), variable.getParamValue());
		return PlugwineResultModel.successResult(variableHolder);
	}
	
	@RequestMapping(value = TokensURIConstants.CTX_VARIABLE, method = RequestMethod.PUT)
	public @ResponseBody PlugwineResultModel updateVariable(@RequestBody VariableHolder variable) {
		PlugwineAssertionError.checkNotNull(variable,
				new EntityNotFoundException(getMessageSource().getMessage("general.error.missingRequiredParam","variable")));
		logger.debug("Start update Variable. name=" + variable.getParamName() + ", value=" + variable.getParamValue() + ".");
		
		VariableHolder variableHolder =  configurationVariableManager.updateVariable(variable);
		
		return PlugwineResultModel.successResult(variableHolder);
	}
	
	@RequestMapping(value = TokensURIConstants.CTX_VARIABLE, method = RequestMethod.DELETE)
	public @ResponseBody PlugwineResultModel deleteVariable(@RequestBody(required=false) VariableHolder variable) {
		PlugwineAssertionError.checkNotNull(variable,
				new EntityNotFoundException(getMessageSource().getMessage("general.error.missingRequiredParam","variable")));
		logger.debug("Start delete Variable. name=" + variable.getParamName() + ", value=" + variable.getParamValue() + ".");
		
		VariableHolder variableHolder =  configurationVariableManager.deleteVariable(variable.getParamName());
		
		return PlugwineResultModel.successResult(variableHolder);
	}
	
	private IMessageSource getMessageSource()
	{
		return configurationVariableManager.getMessageSource();
	}
}
