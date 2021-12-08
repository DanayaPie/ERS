package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.model.Reimbursement;
import com.revature.utility.JDBCUtility;

public class ReimbursementDAO {

	private Logger logger = LoggerFactory.getLogger(ReimbursementDAO.class);

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");

	// getAllReimbursements
	public List<Reimbursement> getAllReimbursements() throws SQLException, ParseException {
		logger.info("ReimbursementDAO.getAllReimbursements() invoked");

		try (Connection con = JDBCUtility.getConnection()) {
			List<Reimbursement> reimbursements = new ArrayList<>();

			String sql = "SELECT * FROM \"ERS_project\".reimbursement ORDER BY reimb_id;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int reimbId = rs.getInt("reimb_id");
				logger.debug("reimbId {} ", reimbId);

				double amount = rs.getInt("amount");
				Timestamp submittedTime = rs.getTimestamp("submitted_time");
				Timestamp resolvedTime = rs.getTimestamp("resolved_time");
				String status = rs.getString("status");
				String type = rs.getString("type");
				String description = rs.getString("description");
				int authorId = rs.getInt("author_id");
				int resolverId = rs.getInt("resolver_id");

				String submittedDateTimestamp = null;
				if (submittedTime != null) {
					submittedDateTimestamp = dateFormat.format(new Date(submittedTime.getTime()));
				}
				String resolvedDateTimestamp = null;
				if (resolvedTime != null) {
					resolvedDateTimestamp = dateFormat.format(new Date(resolvedTime.getTime()));
				}

				Reimbursement r = new Reimbursement();
				r.setAmount(amount);
				r.setSubmittedTime(submittedDateTimestamp);
				r.setResolvedTime(resolvedDateTimestamp);
				r.setStatus(status);
				r.setType(type);
				r.setDescription(description);
				r.setAuthorId(authorId);
				r.setResolverId(resolverId);

				reimbursements.add(r);
			}

			return reimbursements;
		}
	}

	public List<Reimbursement> getReimbByUserId(int userId) throws SQLException {
		logger.info("ReimbursementDAO.getReimbByUserId() invoked");

		try (Connection con = JDBCUtility.getConnection()) {
			List<Reimbursement> reimbursements = new ArrayList<>();

			String sql = "SELECT * FROM \"ERS_project\".reimbursement WHERE author_id = ? ORDER BY reimb_id;";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int reimbId = rs.getInt("reimb_id");
				double amount = rs.getInt("amount");
				Timestamp submittedTime = rs.getTimestamp("submitted_time");
				Timestamp resolvedTime = rs.getTimestamp("resolved_time");
				String status = rs.getString("status");
				String type = rs.getString("type");
				String description = rs.getString("description");
				int authorId = rs.getInt("author_id");
				int resolverId = rs.getInt("resolver_id");

				String submittedDateTimestamp = null;
				if (submittedTime != null) {
					submittedDateTimestamp = dateFormat.format(new Date(submittedTime.getTime()));
				}
				String resolvedDateTimestamp = null;
				if (resolvedTime != null) {
					resolvedDateTimestamp = dateFormat.format(new Date(resolvedTime.getTime()));
				}

				Reimbursement r = new Reimbursement();
				r.setReimbId(reimbId);
				r.setAmount(amount);
				r.setSubmittedTime(submittedDateTimestamp);
				r.setResolvedTime(resolvedDateTimestamp);
				r.setStatus(status);
				r.setType(type);
				r.setDescription(description);
				r.setAuthorId(authorId);
				r.setResolverId(resolverId);

				reimbursements.add(r);
			}
			
			return reimbursements;
		}
	}

}
