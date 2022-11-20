package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(value = { URLException.class })
	public ResponseEntity<Object> handleURLException(URLException ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(value = { NoCustomerRegistratedException.class })
	public ResponseEntity<Object> handleNoCustomerRegistratedException(NoCustomerRegistratedException ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(value = { URLConnectionException.class })
	public ResponseEntity<Object> handleURLConnectionException(URLConnectionException ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(value = { StreamDeserializationException.class })
	public ResponseEntity<Object> handleStreamDeserializationException(StreamDeserializationException ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(value = { TransactionSystemException.class })
	public ResponseEntity<Object> handleTransactionSystemException(TransactionSystemException ex) {
		return new ResponseEntity<Object>("Could not save the customer/post codes!", HttpStatus.INTERNAL_SERVER_ERROR);

	}
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleException(Exception ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
}
