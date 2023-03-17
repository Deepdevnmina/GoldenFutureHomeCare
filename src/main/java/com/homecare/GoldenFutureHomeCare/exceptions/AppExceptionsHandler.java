package com.homecare.GoldenFutureHomeCare.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.homecare.GoldenFutureHomeCare.response.ErrorMessage;

//This is created to use our custom exceptions throughout the project

@ControllerAdvice
public class AppExceptionsHandler {

	@ExceptionHandler(value = { ConsumerServiceException.class })
	public ResponseEntity<Object> handleConsumerServiceException(ConsumerServiceException ex, WebRequest request) {
		 ErrorMessage errorMessage = new ErrorMessage(new Date(),ex.getMessage());
		//Before creating a ErrorMessageClass in response
		//return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<>(errorMessage.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	//This format is created to handle NollpointerException.We need to make changes to our controller class to use this.
	/*  @ExceptionHandler(value = {Exception.class}) public ResponseEntity<Object>
	  handleOtherExceptions(Exception ex, WebRequest request) { ErrorMessage
	  errorMessage = new ErrorMessage(new Date(), ex.getMessage());
	  
	  return new ResponseEntity<>(errorMessage, new HttpHeaders(),
	  HttpStatus.INTERNAL_SERVER_ERROR); }
	*/

}
