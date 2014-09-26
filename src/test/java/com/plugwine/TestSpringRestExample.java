package com.plugwine;


import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.plugwine.domain.holder.VariableHolder;
import com.plugwine.web.PlugwineResultModel;
import com.plugwine.web.controller.TokensURIConstants;


public class TestSpringRestExample {

	public static final String SERVER_URI = "http://localhost:8080/PlugwineTokens";
	
	public static HttpHeaders getHeaders(MediaType mediaType)
	{
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(mediaType);
	    headers.setAccept(Arrays.asList(mediaType));
		
		return headers;
	}
	
	public static RestTemplate getRestTemplate()
	{
		RestTemplate restTemplate = new RestTemplate();
		PlugwineResponseErrorHandler errorHandler = new PlugwineResponseErrorHandler();
		restTemplate.setErrorHandler(errorHandler);
		
		return restTemplate;
	}
	public static void main(String args[]){
		
//		testGetDummyEmployee();
//		System.out.println("****************************************");
//		testCreateVariable();
//		System.out.println("****************************************");
//		testGetVariableXML("Section"); // testing xml return
//		System.out.println("****************************************");
//		testGetVariableJson("Sec"); // testing invalid variable, expecting error
//		System.out.println("****************************************");
//		testGetVariableJson("Section"); // testing OK
//		System.out.println("****************************************");
//		testSearchVariableJson("Abcdef"); // testing invalid variable, but no error
//		System.out.println("****************************************");
//		testGetVariable("Section");
//		System.out.println("****************************************");
//		testGetAllVariables();
		System.out.println("****************************************");
		testUpdateVariable(10509);
	}

	private static void testGetAllVariables() {
		System.out.println("Get All Variables --Object--");
		
		//we can't get List<Employee> because JSON convertor doesn't know the type of
		//object in the list and hence convert it to default JSON object type LinkedHashMap
		RestTemplate rt = getRestTemplate();
	    
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(getHeaders(MediaType.APPLICATION_JSON));
	    ResponseEntity<PlugwineResultModel> responseEntity = rt.exchange(SERVER_URI+TokensURIConstants.CTX_VARIABLE, HttpMethod.GET, 
	    		requestEntity, PlugwineResultModel.class);
	    
		//PlugwineResultModel result = rt.getForObject(SERVER_URI+TokensURIConstants.GET_ALL_CONFVARIABLES, PlugwineResultModel.class);
		PlugwineResponseErrorHandler errorHandler = (PlugwineResponseErrorHandler)rt.getErrorHandler();
	    if(errorHandler.hasError())
	    {
	    	System.err.println("==ERROR:==");
	    	System.err.println("ERROR_CODE: " + errorHandler.getErrorDetails().get(PlugwineResponseErrorHandler.CODE_KEY));
	    	System.err.println("ERROR_MESSAGE: " + errorHandler.getErrorDetails().get(PlugwineResponseErrorHandler.BODY_KEY));
	    }
	    else 
	    {
			Object variables = responseEntity.getBody().getData();
			if(variables instanceof List<?>)
			{
				List<Object> the_variables = (List<Object>) variables;
				System.out.println("Total of : "+ the_variables.size() + " variables");
				for(Object var : the_variables)
				{
					System.out.println("variable:"+var);
				}
			}
	    }
	}

	private static void testUpdateVariable(int variableId) {
		System.out.println("Update Variable --JSON--");
	    VariableHolder variable = new VariableHolder();
		variable.setParamId(variableId);
		variable.setParamName("ABC");
		variable.setParamValue("updated abc val2");
		
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(variable, getHeaders(MediaType.APPLICATION_JSON));
	    RestTemplate template = getRestTemplate();
	    ResponseEntity<String> responseEntity = template.exchange(SERVER_URI+TokensURIConstants.CTX_VARIABLE,
	    		HttpMethod.PUT, requestEntity, String.class);
	    //System.out.println(responseEntity);
	    PlugwineResponseErrorHandler errorHandler = (PlugwineResponseErrorHandler)template.getErrorHandler();
	    if(errorHandler.hasError())
	    {
	    	System.err.println("==ERROR:==");
	    	System.err.println("ERROR_CODE: " + errorHandler.getErrorDetails().get(PlugwineResponseErrorHandler.CODE_KEY));
	    	System.err.println("ERROR_MESSAGE: " + errorHandler.getErrorDetails().get(PlugwineResponseErrorHandler.BODY_KEY));
	    }
	    else 
	    {
	    	String newVariable = responseEntity.getBody();
	    	System.out.println("found from jSON - body - : " + newVariable);
		}
	}
	
	
	private static void testCreateVariable() {
//		curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"restCl5\",\"val\":\"restCl Val5\"}" http://localhost:8080/PlugwineTokens/rest/variables/
		System.out.println("Create Variable --JSON--");
	    VariableHolder variable = new VariableHolder();
		String name = "restCl4";
		variable.setParamName(name);
		variable.setParamValue("//restCl Val4");
		
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(variable, getHeaders(MediaType.APPLICATION_JSON));
	    RestTemplate template = getRestTemplate();
	    ResponseEntity<String> responseEntity = template.exchange(SERVER_URI+TokensURIConstants.CTX_VARIABLE,
	    		HttpMethod.POST, requestEntity, String.class);
	    //System.out.println(responseEntity);
	    PlugwineResponseErrorHandler errorHandler = (PlugwineResponseErrorHandler)template.getErrorHandler();
	    if(errorHandler.hasError())
	    {
	    	System.err.println("==ERROR:==");
	    	System.err.println("ERROR_CODE: " + errorHandler.getErrorDetails().get(PlugwineResponseErrorHandler.CODE_KEY));
	    	System.err.println("ERROR_MESSAGE: " + errorHandler.getErrorDetails().get(PlugwineResponseErrorHandler.BODY_KEY));
	    }
	    else 
	    {
	    	String newVariable = responseEntity.getBody();
	    	System.out.println("found from jSON - body - : " + newVariable);
		}
//		RestTemplate restTemplate = new RestTemplate();
		
		//VariableHolder response = restTemplate.postForObject(SERVER_URI+TokensURIConstants.CREATE_VARIABLE, variable, VariableHolder.class);
		//System.out.println("Variable " + name + " created!!" + response);
	}

	private static void testGetVariable(String variableName) 
	{
		System.out.println("Get Variable for variableName " + variableName + " --Object--");
		//curl -H "Content-type:application/json" -H "Accept:application/json" http://localhost:8080/PlugwineTokens/rest/variable/Section"
		PlugwineResultModel result = getRestTemplate().getForObject(SERVER_URI+TokensURIConstants.CTX_VARIABLE+variableName, PlugwineResultModel.class);
		System.out.println("found: " + result);
	}
	
	private static void testGetVariableJson(String variableName) 
	{
		System.out.println("Get Variable for variableName '" + variableName + "' (JSON)");
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(getHeaders(MediaType.APPLICATION_JSON));
	    RestTemplate template = getRestTemplate();
	    ResponseEntity<String> responseEntity = template.exchange(SERVER_URI+TokensURIConstants.CTX_VARIABLE+variableName, HttpMethod.GET, requestEntity, 
	    		String.class);
	    PlugwineResponseErrorHandler errorHandler = (PlugwineResponseErrorHandler)template.getErrorHandler();
	    if(errorHandler.hasError())
	    {
	    	System.err.println("==ERROR:==");
	    	System.err.println("ERROR_CODE: " + errorHandler.getErrorDetails().get(PlugwineResponseErrorHandler.CODE_KEY));
	    	System.err.println("ERROR_MESSAGE: " + errorHandler.getErrorDetails().get(PlugwineResponseErrorHandler.BODY_KEY));
	    }
	    else 
	    {
	    	String variable = responseEntity.getBody();
	    	System.out.println("found from jSON - body - : " + variable);
		}
	}
	
	private static void testSearchVariableJson(String variableName) 
	{
		System.out.println("Search Variable for variableName '" + variableName + "' (JSON)");
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(getHeaders(MediaType.APPLICATION_JSON));
	    RestTemplate template = getRestTemplate();
	    ResponseEntity<String> responseEntity = template.exchange(SERVER_URI+TokensURIConstants.CTX_VARIABLE_SEARCH+variableName, HttpMethod.GET, requestEntity, 
	    		String.class);
	    PlugwineResponseErrorHandler errorHandler = (PlugwineResponseErrorHandler)template.getErrorHandler();
	    if(errorHandler.hasError())
	    {
	    	System.err.println("==ERROR:==");
	    	System.err.println("ERROR_CODE: " + errorHandler.getErrorDetails().get(PlugwineResponseErrorHandler.CODE_KEY));
	    	System.err.println("ERROR_MESSAGE: " + errorHandler.getErrorDetails().get(PlugwineResponseErrorHandler.BODY_KEY));
	    }
	    else 
	    {
	    	String variable = responseEntity.getBody();
	    	System.out.println("found from jSON - body - : " + variable);
		}
	}
	
	
	private static void testGetVariableXML(String variableName) 
	{
		System.out.println("Get Variable for variableName " + variableName + " --XML--");
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(getHeaders(MediaType.APPLICATION_XML));
	    
	    RestTemplate template = getRestTemplate();
	    ResponseEntity<String> responseEntity = template.exchange(SERVER_URI+TokensURIConstants.CTX_VARIABLE+variableName, HttpMethod.GET, requestEntity, 
	    		String.class);
	    
	    String variable = responseEntity.getBody();
	    System.out.println("found from XML - body - : " + variable);
	}
	
//
//	private static void testGetDummyEmployee() {
//		RestTemplate restTemplate = new RestTemplate();
//		Employee emp = restTemplate.getForObject(SERVER_URI+EmpRestURIConstants.DUMMY_EMP, Employee.class);
//		printEmpData(emp);
//	}
//	
//	public static void printEmpData(Employee emp){
//		System.out.println("ID="+emp.getId()+",Name="+emp.getName()+",CreatedDate="+emp.getCreatedDate());
//	}
}
