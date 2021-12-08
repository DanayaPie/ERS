package com.revature.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	public List<Reimbursement> getReimbursementsByStatus(String statusRequested) throws SQLException {
		logger.info("ReimbursementDAO.getReimbursementsByStatus() invoked");

		try (Connection con = JDBCUtility.getConnection()) {
			List<Reimbursement> reimbursements = new ArrayList<>();

			String sql = "SELECT * FROM \"ERS_project\".reimbursement WHERE status = ? ORDER BY reimb_id;";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, statusRequested);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int reimbId = rs.getInt("reimb_id");
				double amount = rs.getInt("amount");
				Date submittedTime = rs.getDate("submitted_time");
				Date resolvedTime = rs.getDate("resolved_time");
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

	public Reimbursement addReimbursement(Double amount, String status, String type, String description,
			InputStream receipt, int authorId) throws SQLException {
		logger.info("ReimbursementDAO.addReimbursement() invoked");

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "INSERT INTO \"ERS_project\".reimbursement (amount, status, "
					+ "	type, description, receipt, author_id) VALUES (?, ?, ?, ?, ?, ?);";

			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDouble(1, amount);
			pstmt.setString(2, status);
			pstmt.setString(3, type);
			pstmt.setString(4, description);
			pstmt.setBinaryStream(5, receipt);
			pstmt.setInt(6, authorId);

			int numberOfInsertedRecords = pstmt.executeUpdate();

			if (numberOfInsertedRecords != 1) {
				throw new SQLException("Issue occured when adding reimbursement.");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				// grabbing the first column
				int generagedReimbId = rs.getInt(1);

				Reimbursement reimb = this.getReimbByReimbId(generagedReimbId);

//				// autocommit is enable in the setting
//				con.commit(); // commit

				// generatedReimbId, amount, status, type, description, authorId
				return reimb;
				
//				return new Reimbursement(generagedReimbId, amount, submittedTime, resolvedTime, status, type, description, authorId 
			} else {
				throw new SQLException("Generated key was not retrieved.");
			}
		}
	}
	
	public Reimbursement getReimbByReimbId(int reimbursementId) throws SQLException {
		logger.info("ReimbursementDAO.getReimbByReimbId() invoked");

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM \"ERS_project\".reimbursement WHERE reimb_id = ? ORDER BY reimb_id;";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reimbursementId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int reimbId = rs.getInt("reimb_id");
				double amount = rs.getInt("amount");
				Date submittedTime = rs.getDate("submitted_time");
				Date resolvedTime = rs.getDate("resolved_time");
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
				
				return r;
			}
			return null;
		}
	}

}
