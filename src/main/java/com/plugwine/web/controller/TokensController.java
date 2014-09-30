package com.plugwine.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static com.plugwine.web.controller.TokensURIConstants.CTX_VARIABLE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plugwine.domain.dto.VariableDto;
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
	
	@RequestMapping(value = CTX_VARIABLE, method = GET)
	public @ResponseBody PlugwineResultModel getAllVariables() {
		logger.debug("Start getAllTokens.");
		
		List<VariableDto> allTokens = configurationVariableManager.findAllVariables();
		return PlugwineResultModel.successResult(allTokens);
		//return PlugwineResultModel.successResult(new com.plugwine.domain.holder.PlugwineList<variableDto> (allTokens));
	}
	
	/**
	 * if not found, returns an error
	 * @param variableName
	 * @return
	 */
	@RequestMapping(value = CTX_VARIABLE + "/{name}", method = GET)
	public @ResponseBody PlugwineResultModel getVariable(@PathVariable("name") String variableName) {
		logger.debug("Start getVariable. name=" + variableName + ".");
		
		VariableDto variableDto =  configurationVariableManager.findVariableByName(variableName);
		PlugwineAssertionError.checkNotNull(variableDto, 
				new EntityNotFoundException(getMessageSource().getMessage("variables.variable.notFound","[name="+variableName+"]")));
		
		return PlugwineResultModel.successResult(variableDto);
	}
	
	/**
	 * Same as get, but does not return null if not found.
	 * @param variableName
	 * @return
	 */
	@RequestMapping(value = CTX_VARIABLE + "/search/{name}", method = GET)
	public @ResponseBody PlugwineResultModel findVariable(@PathVariable("name") String variableName) {
		logger.debug("Start getVariable. name=" + variableName + ".");
		
		VariableDto variableDto =  configurationVariableManager.findVariableByName(variableName);
		return PlugwineResultModel.successResult(variableDto);
	}
	
	//@Requestbody converts the contents of incoming request body to method's parameter object using the messageconverters
	//Same with @responsebody
	@RequestMapping(value = CTX_VARIABLE, method = POST)
	public @ResponseBody PlugwineResultModel createVariable(@RequestBody VariableDto variable) {
		PlugwineAssertionError.checkNotNull(variable,
				new EntityNotFoundException(getMessageSource().getMessage("general.error.missingRequiredParam","variable")));
		
		logger.debug("Start add Variable. name=" + variable.getParamName() + ", value=" + variable.getParamValue() + ".");
		
		VariableDto variableDto = configurationVariableManager.addVariable(variable.getParamName(), variable.getParamValue());
		return PlugwineResultModel.successResult(variableDto);
	}
	
	@RequestMapping(value = CTX_VARIABLE, method = PUT)
	public @ResponseBody PlugwineResultModel updateVariable(@RequestBody VariableDto variable) {
		PlugwineAssertionError.checkNotNull(variable,
				new EntityNotFoundException(getMessageSource().getMessage("general.error.missingRequiredParam","variable")));
		logger.debug("Start update Variable. name=" + variable.getParamName() + ", value=" + variable.getParamValue() + ".");
		
		VariableDto variableDto =  configurationVariableManager.updateVariable(variable);
		
		return PlugwineResultModel.successResult(variableDto);
	}
	
	@RequestMapping(value = CTX_VARIABLE, method = DELETE)
	public @ResponseBody PlugwineResultModel deleteVariable(@RequestBody(required=false) VariableDto variable) {
		PlugwineAssertionError.checkNotNull(variable,
				new EntityNotFoundException(getMessageSource().getMessage("general.error.missingRequiredParam","variable")));
		logger.debug("Start delete Variable. name=" + variable.getParamName() + ", value=" + variable.getParamValue() + ".");
		
		VariableDto variableDto =  configurationVariableManager.deleteVariable(variable.getParamName());
		
		return PlugwineResultModel.successResult(variableDto);
	}
	
	private IMessageSource getMessageSource()
	{
		return configurationVariableManager.getMessageSource();
	}
}
