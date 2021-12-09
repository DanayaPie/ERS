package com.revature.exception;

public class StatusNotFoundException extends Exception{

	// Extension have serialVersionUID thus anything that extends Exception should have serialVersionUID
	private static final long serialVersionUID = 4494529205359168666L;

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
