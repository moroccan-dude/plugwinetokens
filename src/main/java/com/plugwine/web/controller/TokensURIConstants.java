package com.plugwine.web.controller;

public class TokensURIConstants {
	// always use nouns in the api (as opposed to verbs!)
	public static final String CTX_VARIABLE = "/rest/variable/";
	public static final String SEARCH_CTX_VARIABLE = "/rest/search/variable/";
	
	
//	public static final String DUMMY_EMP = "/rest/emp/dummy";
	public static final String GET_VARIABLE = CTX_VARIABLE + "{name}";
	public static final String SEARCH_VARIABLE = SEARCH_CTX_VARIABLE + "{name}";
	public static final String GET_ALL_CONFVARIABLES = "/rest/variables";
	public static final String CREATE_VARIABLE = CTX_VARIABLE + "create";
//	public static final String DELETE_EMP = "/rest/emp/delete/{id}";
}