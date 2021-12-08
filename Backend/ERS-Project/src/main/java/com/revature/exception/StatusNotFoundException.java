package com.revature.exception;

public class StatusNotFoundException extends Exception{

	public StatusNotFoundException() {
		super();
	}

	public StatusNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StatusNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public StatusNotFoundException(String message) {
		super(message);
	}

	public StatusNotFoundException(Throwable cause) {
		super(cause);
	}

}
