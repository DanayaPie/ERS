package com.revature.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.constant.UserRoleConstants;
import com.revature.dao.ReimbursementDAO;
import com.revature.model.Reimbursement;
import com.revature.model.User;

public class ReimbursementService {

	private Logger logger = LoggerFactory.getLogger(ReimbursementService.class);

	private ReimbursementDAO reimbDao;
	
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

}
