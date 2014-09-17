package com.plugwine;


import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.plugwine.controller.TokensURIConstants;


public class TestSpringRestExample {

	public static final String SERVER_URI = "http://localhost:8080/PlugwineTokens";
	
	public static void main(String args[]){
		
//		testGetDummyEmployee();
//		System.out.println("*****");
//		testCreateEmployee();
//		System.out.println("*****");
//		testGetEmployee();
//		System.out.println("*****");
		testGetAllTokens();
	}

	private static void testGetAllTokens() {
		RestTemplate restTemplate = new RestTemplate();
		//we can't get List<Employee> because JSON convertor doesn't know the type of
		//object in the list and hence convert it to default JSON object type LinkedHashMap
		List<Object> tokens = restTemplate.getForObject(SERVER_URI+TokensURIConstants.GET_ALL_TOKENS, List.class);
		System.out.println(tokens.size());
		for(Object elem : tokens)
		{
			System.out.println("elem:"+elem);
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
//	private static void testGetEmployee() {
//		RestTemplate restTemplate = new RestTemplate();
//		Employee emp = restTemplate.getForObject(SERVER_URI+"/rest/emp/1", Employee.class);
//		printEmpData(emp);
//	}
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
