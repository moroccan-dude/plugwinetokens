package com.plugwine.web;


import javax.persistence.EntityExistsException;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.plugwine.exceptions.EntityNotFoundException;
import com.plugwine.exceptions.PlugwineException;
import com.plugwine.exceptions.ValidationException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	
    public ControllerExceptionHandler() {
        super();
    }

    /**  400 ***/

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
        //return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        return failureResponseEntity(HttpStatus.BAD_REQUEST, ResultConstants.STATUS_CONSTRAINT_VIOLATION_EXCEPTION,
        			"ConstraintViolationException",ex );
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
        //return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        return failureResponseEntity(HttpStatus.BAD_REQUEST, ResultConstants.STATUS_DATA_INTEGRITY_EXCEPTION, 
        		"DataIntegrityViolationException", ex);
        
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        // ex.getCause() instanceof JsonMappingException, JsonParseException // for additional information later on
        //return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
        return failureResponseEntity(HttpStatus.BAD_REQUEST, ResultConstants.STATUS_PARSING_EXCEPTION, 
        		"HttpMessageNotReadableException", ex );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return failureResponseEntity(HttpStatus.BAD_REQUEST, ResultConstants.INVALID_ARGUMENT_ERROR_CODE, 
    			"Illegal Argument found", ex );
    }

    // Custom exception handling
    @ExceptionHandler(value = {  ValidationException.class })
    protected ResponseEntity<Object> handleParamsNotValid(final RuntimeException ex, final WebRequest request) {
        return failureResponseEntity(HttpStatus.BAD_REQUEST, (ValidationException)ex );
    }

    @ExceptionHandler(value = {  EntityNotFoundException.class })
    protected ResponseEntity<Object> handleEntityNotFound(final RuntimeException ex, final WebRequest request) {
        return failureResponseEntity(HttpStatus.BAD_REQUEST, (EntityNotFoundException)ex );
    }
    /***  403  ***/

    //TBD
    
    /*** 404 ***/
    
    @ExceptionHandler({ClassNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
    	return failureResponseEntity(HttpStatus.NOT_FOUND, ResultConstants.STATUS_RESOURCE_NOT_FOUND,
    			"Resource Not found", ex);
    }
  
    /*** 409 ***/
    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class, EntityExistsException.class })
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        //final String bodyOfResponse = "Data Error!";//TODO to localize at some point.
        //return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
        return failureResponseEntity(HttpStatus.CONFLICT, ResultConstants.STATUS_DATA_ACCESS_EXCEPTION,
        		"Data Error", ex );
    }

    /*** 412 ***/

    //TBD
    
    /*** 500 ***/

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    /*500*/public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        return failureResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResultConstants.STATUS_INTERNAL_ERROR_EXCEPTION,
        		"Internal error" ,ex );
        //return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    //all other exceptions!
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

    	  return failureResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResultConstants.STATUS_UNKNOWN_EXCEPTION,
          		"Unexpected internal error" ,ex );
	}
    
    private ResponseEntity<Object> failureResponseEntity(HttpStatus httpStatus, PlugwineException ex)
    {
    	logger.debug("HTTP Status:" + httpStatus.toString() + " - ResponseCode: "+ ex.getCode() + " - message: " + ex.getMessage(),ex);
    	
    	return new ResponseEntity<Object>(PlugwineResultModel.failureResult(ex.getCode(), ex.getMessage()), 
         		new HttpHeaders(), httpStatus);
        //return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    private ResponseEntity<Object> failureResponseEntity(HttpStatus httpStatus, int responseCode, String message, Exception ex)
    {
    	if(ex!=null && ex.getMessage()!=null)
    		message =  ex.getMessage();
    	
    	if(ex!=null)
    		logger.error("HTTP Status:" + httpStatus.toString() + " - ResponseCode: "+ responseCode + " - message: " + message,ex);
    	else
    		logger.error("HTTP Status:" + httpStatus.toString() + " - ResponseCode: "+ responseCode + " - message: " + message);
    	
    	return new ResponseEntity<Object>(PlugwineResultModel.failureResult(responseCode, message), 
         		new HttpHeaders(), httpStatus);
        //return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
