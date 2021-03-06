package com.revature.constant;

public final class EndpointConstants {
	
	// Authentication's Endpoints
	public static final String POST_LOGIN = "/login";
	public static final String POST_LOGOUT = "/logout";
	public static final String GET_CHECKLOGINSTATUS = "/checkloginstatus";
	
	// Reimbursement's Endpoints
	public static final String GET_REIMBURSEMENTS = "/reimbursements";
	public static final String GET_REIMBURSEMENTS_BYSTATUS = "/reimbursements/{status}/status";
	public static final String POST_REIMBURSEMENTS = "/reimbursements";
	public static final String GET_RECEIPT = "/reimbursements/{reimbId}/receipt";
	public static final String PATCH_REIMBURSEMENTS = "/reimbursements/{reimbId}";

	// User's Endpoints
	public static final String POST_SIGNUP = "/signup";
}