package com.revature.exception;

public class ReimbursementNotFoundException extends Exception{

	// Extension have serialVersionUID thus anything that extends Exception should have serialVersionUID
	private static final long serialVersionUID = 8218505039454055038L;

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
