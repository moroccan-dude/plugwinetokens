package com.plugwine.web;

import org.apache.http.HttpStatus;

public class ResultConstants 
{

	public static String MESSAGE_CODE_OK                      = "Success";
	public static String MESSAGE_CODE_NOK                     = "Failure";
	
	public static int STATUS_CODE_OK                                 = HttpStatus.SC_OK;  // 200
	
	/* exceptions */
	public static int STATUS_INTERNAL_ERROR_EXCEPTION                = 500;
	public static int STATUS_UNKNOWN_EXCEPTION                       = 501;
//	public static int STATUS_UNKNOWN_EXCEPTION_GENERATE_SIGNATURE    = 501;
//	public static int STATUS_UNKNOWN_EXCEPTION_FETCHING_ACCOUNT      = 502;
//	public static int STATUS_UNKNOWN_EXCEPTION_FETCHING_CLIENT       = 503;
//	public static int STATUS_UNKNOWN_EXCEPTION_FETCHING_USERACCOUNT  = 504;
//	public static int STATUS_UNKNOWN_EXCEPTION_FETCH_OR_CREATE_APIKEY= 506;
//	public static int STATUS_UNKNOWN_EXCEPTION_CREATE_APIKEY         = 507;
	
	/* generic */
	public static int STATUS_PARSING_EXCEPTION                       = HttpStatus.SC_BAD_REQUEST;         // 400
	public static int STATUS_RESOURCE_NOT_FOUND 					 = HttpStatus.SC_NOT_FOUND;           // 404
	public static int METHOD_NOT_ALLOWED                             = HttpStatus.SC_METHOD_NOT_ALLOWED;  // 405
	
	
	/* common error codes */
	public static int STATUS_VALIDATION_ERROR_CODE                   = 4000;
	public static int INVALID_ARGUMENT_ERROR_CODE                    = 4001;
	public static int STATUS_CONSTRAINT_VIOLATION_EXCEPTION          = 4002;
	public static int STATUS_DATA_INTEGRITY_EXCEPTION                = 4003;
	public static int STATUS_DATA_ACCESS_EXCEPTION                   = 4004;
	public static int STATUS_ENTITY_NOT_FOUND_ERROR_CODE             = 4005;
//	public static int EMPTY_SIGNATURE_ERROR_CODE                     = 4001;
//	public static int EMPTY_TIMESTAMP_ERROR_CODE                     = 4002;
//	public static int INVALID_LOGIN_ERROR_CODE                       = 4003;
//	public static int INVALID_TIMESTAMP_AFTER_NOW_ERROR_CODE         = 4004;
//	public static int INVALID_TIMESTAMP_TOO_OLD_ERROR_CODE           = 4005;
//	public static int INVALID_TIMESTAMP_FORMAT_ERROR_CODE            = 4006;
//	public static int INVALID_SIGNATURE_MISMATCH_ERROR_CODE          = 4007;
//	public static int INVALID_SIGNATURE_API_KEY_NOT_FOUND            = 4008; /* API Key of the user not found in our system! */
//	public static int INVALID_SIGNATURE_API_KEY_INVALID_FORMAT       = 4009; /* API Key of the user has the wrong format in our system! */
//	public static int INSUFFICIENT_PRIVILEGES_ERROR_CODE             = 4010;

	
}
