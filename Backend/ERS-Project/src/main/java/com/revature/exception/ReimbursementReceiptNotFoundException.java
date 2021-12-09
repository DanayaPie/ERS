package com.revature.exception;

public class ReimbursementReceiptNotFoundException extends Exception {

	// Extension have serialVersionUID thus anything that extends Exception should have serialVersionUID
	private static final long serialVersionUID = -3168527391472662391L;

	public ReimbursementReceiptNotFoundException() {
		super();
	}

	public ReimbursementReceiptNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReimbursementReceiptNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimbursementReceiptNotFoundException(String message) {
		super(message);
	}

	public ReimbursementReceiptNotFoundException(Throwable cause) {
		super(cause);
	}

}
