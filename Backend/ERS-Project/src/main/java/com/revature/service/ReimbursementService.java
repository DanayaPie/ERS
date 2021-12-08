package com.revature.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.constant.UserRoleConstants;
import com.revature.dao.ReimbursementDAO;
import com.revature.exception.InvalidParameterException;
import com.revature.exception.ReimbursementNotFoundException;
import com.revature.exception.StatusNotFoundException;
import com.revature.model.Reimbursement;
import com.revature.model.User;

public class ReimbursementService {

	private Logger logger = LoggerFactory.getLogger(ReimbursementService.class);

	private ReimbursementDAO reimbDao;

	List<String> validStatusType = Arrays.asList("Pending", "Approved", "Denied");

	public ReimbursementService() {
		this.reimbDao = new ReimbursementDAO();
	}

	public List<Reimbursement> getReimbursements(User currentlyLoggedInUser) throws SQLException, ParseException {
		logger.info("ReimbursementService.getReimbursements() invoked");

		List<Reimbursement> reimbursements = null;

		/*-
		 * 	Possibilities
		 * 	1. logged in user is a finance manager
		 * 	2. logged in user is an employee
		 */

		// 1.
		if (currentlyLoggedInUser.getUserRole().equals(UserRoleConstants.FINANCE_MANAGER)) {
			logger.info("user is a finance manager");

			reimbursements = this.reimbDao.getAllReimbursements();

			// 2.
		} else if (currentlyLoggedInUser.getUserRole().equals(UserRoleConstants.EMPLOYEE)) {
			logger.info("user is an employee");

			reimbursements = this.reimbDao.getReimbByUserId(currentlyLoggedInUser.getUserId());
		}

		return reimbursements;
	}

	public List<Reimbursement> getReimbursementsByStatus(User currentlyLoggedInUser, String statusRequested)
			throws SQLException, StatusNotFoundException, ReimbursementNotFoundException {
		logger.info("ReimbursementService.getReimbursementsByStatus() invoked");

		List<Reimbursement> reimbursements = null;

		/*-
		 * 	Possibilities
		 * 	1. logged in user is a finance manager
		 * 	2. logged in user is an employee
		 */

		// 1.

		if (statusRequested != null) {
			statusRequested = statusRequested.substring(0, 1).toUpperCase() + statusRequested.substring(1);

		}

		if (currentlyLoggedInUser.getUserRole().equals(UserRoleConstants.FINANCE_MANAGER)) {
			logger.info("user is a finance manager");

			/*-
			 * 	statusRequested possibilities 
			 * 	1. status is null 
			 * 	2. status is no null
			 */
			if (statusRequested == null || !validStatusType.contains(statusRequested)) {
				logger.info("status is null");

				throw new StatusNotFoundException("Status not found.");

			} else if (statusRequested != null) {
				logger.info("status is not null");

				reimbursements = this.reimbDao.getReimbursementsByStatus(statusRequested);

				if (reimbursements.size() == 0) {
					throw new ReimbursementNotFoundException(
							"Reimbursement(s) with status " + statusRequested + " not found.");
				}
			}

			// 2.
		} else if (currentlyLoggedInUser.getUserRole().equals(UserRoleConstants.EMPLOYEE)) {
			logger.info("user is an employee");

			reimbursements = this.reimbDao.getReimbByUserId(currentlyLoggedInUser.getUserId());
		}

		return reimbursements;
	}

	public Reimbursement addReimbursement(User currentlyLoggedInUser, Double amount, String type, String description,
			String mimeType, InputStream receipt) throws InvalidParameterException, SQLException {
		logger.info("ReimbursementService.addReimbursement() invoked");

		// set status to pending when add new reimbursement
		String status = "Pending";

		// set userId that logged in to 'authorId'
		int authorId = currentlyLoggedInUser.getUserId();

		Reimbursement addedReimbursement = this.reimbDao.addReimbursement(amount, status, type, description,
				receipt, authorId);

		return addedReimbursement;
	}
}
