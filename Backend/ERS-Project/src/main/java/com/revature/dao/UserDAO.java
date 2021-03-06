package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.model.User;
import com.revature.utility.JDBCUtility;

public class UserDAO {

	private Logger logger = LoggerFactory.getLogger(UserDAO.class);

	String dbSchema = System.getenv("db.schema");

	public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
		logger.info("UserDAO.getUserByUsernameAndPassword() invoked");

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM " + dbSchema + ".users WHERE username = ? AND password = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				int userId = rs.getInt("user_id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String user = rs.getString("username");
				String pass = rs.getString("password");
				String email = rs.getString("email");
				String userRole = rs.getString("user_role");

				User retrievedUser = new User();
				retrievedUser.setUserId(userId);
				retrievedUser.setFirstName(firstName);
				retrievedUser.setLastName(lastName);
				retrievedUser.setUsername(user);
				retrievedUser.setPassword(pass);
				retrievedUser.setEmail(email);
				retrievedUser.setUserRole(userRole);

				return retrievedUser;
			} else {
				return null;
			}
		}
	}

	public List<User> getUserByUsernameAndEmail(String signUpUsername, String signUpEmail) throws SQLException {
		logger.info("UserDAO.getUserByUsernameAndEmail() invoked");

		try (Connection con = JDBCUtility.getConnection()) {
			List<User> users = new ArrayList<>();

			String sql = "Select username, email FROM " + dbSchema + ".users WHERE username = ? OR email = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, signUpUsername);
			pstmt.setString(2, signUpEmail);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String username = rs.getString("username");
				String email = rs.getString("email");

				User user = new User();
				user.setUsername(username);
				user.setEmail(email);

				users.add(user);
			}

			return users;
		}
	}

	public User signUp(User user) throws SQLException {
		logger.info("UserDAO.signUp() invoked");

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "INSERT INTO " + dbSchema
					+ ".users (first_name, last_name, username, password, email, user_role)"
					+ "VALUES (?, ?, ?, ?, ?, ?);";

			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getFirstName());
			pstmt.setString(2, user.getLastName());
			pstmt.setString(3, user.getUsername());
			pstmt.setString(4, user.getPassword());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, user.getUserRole());

			int numberOfrecordsInserted = pstmt.executeUpdate();

			if (numberOfrecordsInserted != 1) {
				throw new SQLException("Sign up was unsucessful.");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();
			// grabbing the first column
			Integer automaticallyGeneratedUserId = rs.getInt(1);

			user.setUserId(automaticallyGeneratedUserId);

			return user;
		}
	}

	public User getUserByUsername(String inputUsername) throws SQLException {
		logger.info("UserDAO.getUserByUsername() invoked");

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT username, password FROM " + dbSchema + ".users WHERE username = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, inputUsername);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				String username = rs.getString("username");
				String password = rs.getString("password");

				User user = new User();
				user.setUsername(username);
				user.setPassword(password);

				return user;
			} else {
				return null;
			}
		}
	}

}
