package com.homecare.GoldenFutureHomeCare.exceptions;


//I create custom exceptions to handle the exceptions and find the error around easily.
public class ConsumerServiceException extends RuntimeException{

	
	private static final long serialVersionUID = 2049475955790738964L;

	public ConsumerServiceException (String message) {
		super(message);
		// I will use this in Controller at Consumer service where I created users.
	}

}
