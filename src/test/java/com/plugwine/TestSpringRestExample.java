package com.plugwine;


import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.plugwine.controller.TokensURIConstants;
import com.plugwine.domain.holder.TokenHolder;
import com.plugwine.domain.holder.VariableHolder;


public class TestSpringRestExample {

	public static final String SERVER_URI = "http://localhost:8080/PlugwineTokens";
	
	public static void main(String args[]){
		
//		testGetDummyEmployee();
//		System.out.println("*****");
//		testCreateEmployee();
		testGetVariableXML("Section");
		System.out.println("****************************************");
		testGetVariableJson("Section");
		System.out.println("****************************************");
		testGetVariable("Section");
		System.out.println("****************************************");
		testGetAllVariables();
	}

	private static void testGetAllVariables() {
		System.out.println("Get All Variables --Object--");
		
		RestTemplate restTemplate = new RestTemplate();
		//we can't get List<Employee> because JSON convertor doesn't know the type of
		//object in the list and hence convert it to default JSON object type LinkedHashMap
		List<Object> variables = restTemplate.getForObject(SERVER_URI+TokensURIConstants.GET_ALL_CONFVARIABLES, List.class);
		System.out.println("Total of : "+ variables.size() + " variables");
		for(Object var : variables)
		{
			System.out.println("variable:"+var);
		}
	}

//	private static void testCreateEmployee() {
//		RestTemplate restTemplate = new RestTemplate();
//		Employee emp = new Employee();
//		emp.setId(1);emp.setName("Pankaj Kumar");
//		Employee response = restTemplate.postForObject(SERVER_URI+EmpRestURIConstants.CREATE_EMP, emp, Employee.class);
//		printEmpData(response);
//	}
//
	private static void testGetVariable(String variableName) 
	{
		System.out.println("Get Variable for variableName " + variableName + " --Object--");
		//curl -H "Content-type:application/json" -H "Accept:application/json" http://localhost:8080/PlugwineTokens/rest/variable/Section"
		RestTemplate restTemplate = new RestTemplate();
		VariableHolder variable = restTemplate.getForObject(SERVER_URI+TokensURIConstants.CONTEXT+variableName, VariableHolder.class);
		System.out.println("found: " + variable);
	}
	
	private static void testGetVariableJson(String variableName) 
	{
		System.out.println("Get Variable for variableName " + variableName + " --JSON--");
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	    HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
	    RestTemplate template = new RestTemplate();
	    ResponseEntity<String> responseEntity = template.exchange(SERVER_URI+TokensURIConstants.CONTEXT+variableName, HttpMethod.GET, requestEntity, 
	    		String.class);
	    
	    String variable = responseEntity.getBody();
	    System.out.println("found from jSON - body - : " + variable);
	}
	
	private static void testGetVariableXML(String variableName) 
	{
		System.out.println("Get Variable for variableName " + variableName + " --XML--");
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_XML);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
	    
	    RestTemplate template = new RestTemplate();
	    ResponseEntity<String> responseEntity = template.exchange(SERVER_URI+TokensURIConstants.CONTEXT+variableName, HttpMethod.GET, requestEntity, 
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
