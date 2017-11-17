package com.search.app.exception;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * This is Exception handler class. 
 * This class handles all the exception thrown from the application 
 * and provide necessary message to the end user
 * 
 * @author Shadyaab_Akhtar
 * @version 1.0.0
 * @since 2017-11-15
 */

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@Autowired
    private Environment env;
	
	/**
	 * This method will only be called if any IOException is thrown
	 * It return status along with the useful information to the user
	 */
	@ExceptionHandler(IOException.class)
	public ResponseEntity<Object> handlerIOException(){
		String errorMsg = env.getProperty("error.IOException");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
	}
	
	/**
	 * This method will only be called if no result is found for which client requested
	 * It return status along with the useful information to the user
	 */
	@ExceptionHandler(NoResultFoundException.class)
	public ResponseEntity<Object> handlerNoResultFoundException(){
		String errorMsg = env.getProperty("error.NoResultFound");
		return ResponseEntity.status(HttpStatus.OK).body(errorMsg);
	}
	
	/**
	 * This method will only be called if search term provided is illegal or empty
	 * It return status along with the useful information to the user
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handlerIllegalArgumentException(){
		String errorMsg = env.getProperty("error.InvalidSearchTerm");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
	}
	
	/**
	 * This method will only be called when there is no matching handler method is found
	 * It return status along with the useful information to the user
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Object> handlerNoHandlerFoundException(){
		String errorMsg = env.getProperty("error.NoHandlerFound");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
	}
	
	/**
	 * This method will only be called when there is no directory with that name present
	 * It return status along with the useful information to the user
	 */
	@ExceptionHandler(DirectoryNotFoundException.class)
	public ResponseEntity<Object> handlerDirectoryNotFoundException(){
		String errorMsg = env.getProperty("error.DirectoryNotFound");;
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
	}
	
	/**
	 * This method will only be called if for that directory access is denied
	 * It return status along with the useful information to the user
	 */
	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<Object> handlerSecurityException(){
		String errorMsg = env.getProperty("error.AccessDenied");;
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMsg);
	}
	
	/**
	 * This method will only be called if some error occurred other than specified above
	 * It return status along with the useful information to the user
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handlerException(){
		String errorMsg = env.getProperty("error.GenericException");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
	}
	
	
	
	
}
