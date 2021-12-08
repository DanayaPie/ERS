package com.revature.exception;

public class ReimbursementNotFoundException extends Exception{

	public ReimbursementNotFoundException() {
		super();
	}

	public ReimbursementNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReimbursementNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimbursementNotFoundException(String message) {
		super(message);
	}

	public ReimbursementNotFoundException(Throwable cause) {
		super(cause);
	}

}
