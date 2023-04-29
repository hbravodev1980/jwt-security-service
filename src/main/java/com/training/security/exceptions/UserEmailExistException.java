package com.training.security.exceptions;

public class UserEmailExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserEmailExistException(String message) {
		super(message);
	}

}
