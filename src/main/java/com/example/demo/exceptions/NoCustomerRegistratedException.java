package com.example.demo.exceptions;

public class NoCustomerRegistratedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NoCustomerRegistratedException(String message) {
		super(message);
	}
}
