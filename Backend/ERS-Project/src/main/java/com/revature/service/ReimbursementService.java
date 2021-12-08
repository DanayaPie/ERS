package com.revature.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.constant.UserRoleConstants;
import com.revature.dao.ReimbursementDAO;
import com.revature.exception.InvalidParameterException;
import com.revature.exception.ReimbursementNotFoundException;
import com.revature.exception.ReimbursementReceiptNotFoundException;
import com.revature.exception.StatusNotFoundException;
import com.revature.exception.UnauthorizedException;
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

	public Reimbursement addReimbursement(User currentlyLoggedInUser, String amountAdded, String type,
			String description, String mimeType, InputStream receipt) throws InvalidParameterException, SQLException {
		logger.info("ReimbursementService.addReimbursement() invoked");

		// parse amount to double
		Double amount = Double.parseDouble(amountAdded);

		// set status to pending when add new reimbursement
		String status = "Pending";

		// set userId that logged in to 'authorId'
		int authorId = currentlyLoggedInUser.getUserId();

		Reimbursement addedReimbursement = this.reimbDao.addReimbursement(amount, status, type, description, receipt,
				authorId);

		return addedReimbursement;
	}

	public void verifyReimbursementByReimbId(String reimbursementId)
			throws ReimbursementNotFoundException, InvalidParameterException, SQLException {
		logger.info("ReimbursementService.verifyReimbursementByReimbId() invoked");

		try {

			int reimbId = Integer.parseInt(reimbursementId);

			Reimbursement reimbursement = this.reimbDao.getReimbByReimbId(reimbId);

			/*-
			 * 	Possibilities
			 * 	1. reimbursement does not exist
			 * 	2. reimbursement exist
			 */

			// 1.
			if (reimbursement == null) {
				logger.info("reimb does not exist");

				throw new ReimbursementNotFoundException("Reimbursement with ID " + reimbId + " was not found.");

				// 2.
			} else {
				logger.info("reimb exist");
			}

		} catch (NumberFormatException e) {
			logger.info("Client ID provided is not an int convertable value.");

			throw new InvalidParameterException("Client ID provided is not an int convertable value.");
		}
	}

	// getRecieptByReimbursementId
	public InputStream getRecieptImageByReimbId(User currentlyLoggedInUser, String reimbursementId)
			throws SQLException, UnauthorizedException, ReimbursementReceiptNotFoundException {
		logger.info("ReimbursementService.getRecieptImageByReimbId() invoked");

		int reimbId = Integer.parseInt(reimbursementId);

		/*-
		 *  Business logic
		 *  1. finance manager can view any images that belong to anyone
		 *  2. employee can only view images for reimb belong to them
		 */

		// 1.
		if (currentlyLoggedInUser.getUserRole().equals(UserRoleConstants.EMPLOYEE)) {
			logger.info("usre is an employee");

			int userId = currentlyLoggedInUser.getUserId();
			List<Reimbursement> reimbursementsBelongToEmployee = this.reimbDao.getReimbByUserId(userId);

			Set<Integer> reimbIdsEncountered = new HashSet<>();
			for (Reimbursement r : reimbursementsBelongToEmployee) {
				reimbIdsEncountered.add(r.getReimbId());
			}

			// check if the image belong to the user
			if (!reimbIdsEncountered.contains(reimbId)) {
				throw new UnauthorizedException("Unable to access reimbursement's receipt.");
			}
		}

		// grab the image form the DAO
		InputStream image = this.reimbDao.getRecieptByReimbursementId(reimbId);

		if (image == null) {
			throw new ReimbursementReceiptNotFoundException("Reciept was not found for reimbursement ID " + reimbId);
		}

		return image;
	}

	public Reimbursement updateReimbursementStatus(User currentlyLoggedInUser, String reimbId, String status,
			int authorId) throws SQLException, ParseException, UnauthorizedException, InvalidParameterException {
		logger.info("ReimbursementService.updateReimbursementStatus() invoked");

		int reimbursementId = Integer.parseInt(reimbId);

		/*-
		 *  Authorization
		 *  1. FM
		 *  2. employee
		 */

		// 1.
		if (currentlyLoggedInUser.getUserRole().equals(UserRoleConstants.FINANCE_MANAGER)) {
			logger.info("user is a finance manager");

			// 2.
		} else if (currentlyLoggedInUser.getUserRole().equals(UserRoleConstants.EMPLOYEE)) {
			logger.info("user is an employee");

			throw new UnauthorizedException("Access denied. User must be a finance manager.");
		}

		Reimbursement reimbursement = this.reimbDao.getReimbByReimbId(reimbursementId);

		logger.debug("rembid {}", reimbursementId);

		/*-
		 * 	STATUS Possibilities
		 * 	1. cannot update your own status
		 * 	2. can update status but wrong status
		 * 	3. can update status, correct status type
		 * 	4. cant' update status, status has already been updated
		 */

		logger.info("id's {} {}",currentlyLoggedInUser.getUserId(),reimbursement.getAuthorId());
		// 1.
		if (currentlyLoggedInUser.getUserId() == reimbursement.getAuthorId()) {
			logger.info("cannot update your own reimb");

			
			throw new UnauthorizedException("You cannot update your own reimbursement status.");
		}

		// change status to upper case first letter
		if (status != null) {
			status = status.substring(0, 1).toUpperCase() + status.substring(1);
		}

		// 2.
		if (!validStatusType.contains(status)) {
			logger.info("invalid status {}", status);

			throw new InvalidParameterException("Status must be either 'Approved' or 'Denied'.");
		}

		// 3.
		if (reimbursement.getStatus().equals("Pending")) {
			logger.info("correct status");

			this.reimbDao.updateReimbursementStatus(reimbursementId, status, currentlyLoggedInUser.getUserId());

			reimbursement = this.reimbDao.getReimbByReimbId(reimbursementId);

			// 4.
		} else {
			logger.info("reimbursment has already been updated");

			throw new InvalidParameterException("Unable to update an already updated reimbursement.");

		}

		return reimbursement;
	}
}
