package com.search.app.exception;

public class DirectoryNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor to assign message to the instance variable
	 *
	 */
	public DirectoryNotFoundException() {
		super("No directory found");
	}
	
	/**
	 * Parameterized constructor to assigned message to the instance 
	 * variable using the parameter
	 * @param message User defined message to assign it to instance variable
	 */
	public DirectoryNotFoundException(String message) {
		super(message);
	}
}
