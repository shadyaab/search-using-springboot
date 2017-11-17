package com.search.app.exception;

/**
 * This class is used to create user defined exception 
 * i.e when there is no result found
 * 
 * @author Shadyaab_Akhtar
 * @version 1.0.0
 * @since 2017-11-15
 */
public class NoResultFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor to assign message to the instance variable
	 *
	 */
	public NoResultFoundException() {
		super("No file found");
	}
	
	/**
	 * Parameterized constructor to assigned message to the instance 
	 * variable using the parameter
	 * @param message User defined message to assign it to instance variable
	 */
	public NoResultFoundException(String message) {
		super(message);
	}

}
