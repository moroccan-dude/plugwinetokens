package com.plugwine;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.plugwine.domain.dto.VariableDto;
import com.plugwine.web.PlugwineResultModel;
import com.plugwine.web.controller.TokensURIConstants;
import com.sun.corba.se.spi.activation._ActivatorImplBase;


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

		// BUG for delete entity!! and REST TEMPLATE.
//
//	    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory() {
//	        @Override
//	        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
////	            if (HttpMethod.DELETE == httpMethod) {
////	                return new HttpEntityEnclosingDeleteRequest(uri);
////	            }
//	            return super.createHttpUriRequest(httpMethod, uri);
//	        }
//	    });


		return restTemplate;
	}
	public static void main(String args[])
	{
		System.out.println("****************************************");
//		testCreateVariable("restC200"); // create (POST)
//		System.out.println("****************************************");
//		testGetVariableXML("Section"); // testing xml return
//		System.out.println("****************************************");
//		testGetVariableJson("Sec"); // testing invalid variable, expecting error
//		System.out.println("****************************************");
//		testGetVariableJson("Section"); // testing GET/{name} OK, JSON content-type
//		System.out.println("****************************************");
//		testSearchVariableJson("Abcdef"); // testing invalid variable, but no error
//		System.out.println("****************************************");
//		testGetVariable("Section"); // testing GET/{name} OK, no content-type, testing default
//		System.out.println("****************************************");
//		testGetAllVariables(); // testing GET ALL 
//		System.out.println("****************************************");
//		testUpdateVariable(10509); // testing PUT
//		System.out.println("****************************************");
//		testDeleteVariable("restC202"); // testing DELETE
//		System.out.println("****************************************");
//		testGetVariableJsonFR("Sec"); // testing invalid variable, expecting error in French
		System.out.println("****************************************");
		testGetVariableJsonCORS(); // testing CORS support
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
	    VariableDto variable = new VariableDto();
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



	private static void testDeleteVariable(String name) {
//		curl -X DELETE -H "Content-Type: application/json" -d "{\"name\":\"restC200\",\"val\":\"toto\",\"id\":\"123\"}" http://localhost:8080/PlugwineTokens/rest/variables
		//System.setProperty("sun.net.client.defaultReadTimeout","10000");
		System.out.println("Delete Variable --JSON--");
		VariableDto variable = new VariableDto();
		variable.setParamName(name);
		variable.setParamValue("toto");
		variable.setParamId(1111);
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(variable, getHeaders(MediaType.APPLICATION_JSON));
	    RestTemplate template = getRestTemplate();
	    ResponseEntity<String> responseEntity = template.exchange(SERVER_URI+TokensURIConstants.CTX_VARIABLE,
	    		HttpMethod.DELETE, requestEntity, String.class);
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

		//VariableDto response = restTemplate.postForObject(SERVER_URI+TokensURIConstants.CREATE_VARIABLE, variable, VariableDto.class);
		//System.out.println("Variable " + name + " created!!" + response);
	}

	private static void testCreateVariable(String name) {
//		curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"restCl5\",\"val\":\"restCl Val5\"}" http://localhost:8080/PlugwineTokens/rest/variables/
		System.out.println("Create Variable --JSON--");
	    VariableDto variable = new VariableDto();
		variable.setParamName(name);
		variable.setParamValue("//restCl Val99");
		variable.setParamId(1111);

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

		//VariableDto response = restTemplate.postForObject(SERVER_URI+TokensURIConstants.CREATE_VARIABLE, variable, VariableDto.class);
		//System.out.println("Variable " + name + " created!!" + response);
	}

	private static void testGetVariable(String variableName)
	{
		System.out.println("Get Variable for variableName " + variableName + " --Object--");
		//curl -H "Content-type:application/json" -H "Accept:application/json" http://localhost:8080/PlugwineTokens/rest/variable/Section"
		PlugwineResultModel result = getRestTemplate().getForObject(SERVER_URI+TokensURIConstants.CTX_VARIABLE+"/"+variableName, PlugwineResultModel.class);
		System.out.println("found: " + result);
	}

	
	private static void testGetVariableJsonCORS()
	{
		System.out.println("-------------Testing case of error....-------------");
		testCor("Sec");
		System.out.println("-------------Testing case of success....-------------");
		testCor("Section");
	}
	
	private static void testCor(String variableName)
	{
		System.out.println("Get Variable for variableName '" + variableName + "' (JSON)");
		HttpHeaders headers = getHeaders(MediaType.APPLICATION_JSON);
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
	    RestTemplate template = getRestTemplate();
	    ResponseEntity<String> responseEntity = template.exchange(SERVER_URI+TokensURIConstants.CTX_VARIABLE+"/"+variableName, HttpMethod.GET, requestEntity,
	    		String.class);
	    PlugwineResponseErrorHandler errorHandler = (PlugwineResponseErrorHandler)template.getErrorHandler();
	    if(errorHandler.hasError())
	    {
	    	System.out.println("ERROR!");
	    	System.err.println("ERROR_CODE: " + errorHandler.getErrorDetails().get(PlugwineResponseErrorHandler.CODE_KEY));
	    	System.err.println("ERROR_MESSAGE: " + errorHandler.getErrorDetails().get(PlugwineResponseErrorHandler.BODY_KEY));
	    }
	    else 
	    {
	    	System.out.println("SUCCESS!" + responseEntity.getBody());
		}
	    System.out.println("Response Headers: ");
	    
	    HttpHeaders headers2 = responseEntity.getHeaders();
	    Iterator<String> itHeader = headers2.keySet().iterator();
	    while(itHeader.hasNext())
	    {
	    	String headerName = itHeader.next();
	    	System.out.println(headerName + "--> " + headers2.get(headerName));
	    }
	}
	
	
	private static void testGetVariableJsonFR(String variableName)
	{
		System.out.println("Get Variable for variableName '" + variableName + "' (JSON)");
		HttpHeaders headers = getHeaders(MediaType.APPLICATION_JSON);
		headers.add("accept-language", "fr_FR");
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
	    RestTemplate template = getRestTemplate();
	    ResponseEntity<String> responseEntity = template.exchange(SERVER_URI+TokensURIConstants.CTX_VARIABLE+"/"+variableName, HttpMethod.GET, requestEntity,
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

	private static void testGetVariableJson(String variableName)
	{
		System.out.println("Get Variable for variableName '" + variableName + "' (JSON)");
		HttpHeaders headers = getHeaders(MediaType.APPLICATION_JSON);
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
	    RestTemplate template = getRestTemplate();
	    ResponseEntity<String> responseEntity = template.exchange(SERVER_URI+TokensURIConstants.CTX_VARIABLE+"/"+variableName, HttpMethod.GET, requestEntity,
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
	    ResponseEntity<String> responseEntity = template.exchange(SERVER_URI+TokensURIConstants.CTX_VARIABLE+"/"+variableName, HttpMethod.GET, requestEntity,
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
