package com.revature.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.security.auth.login.FailedLoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.UserDAO;
import com.revature.exception.InvalidParameterException;
import com.revature.model.User;
import com.revature.utility.HashUtil;

public class UserService {

	private Logger logger = LoggerFactory.getLogger(UserService.class);

	private UserDAO userDao;

	public UserService() {
		super();
		this.userDao = new UserDAO();
	}

	public User getUserByUsernameAndPassword(String username, String password)
			throws SQLException, FailedLoginException, NoSuchAlgorithmException {
		logger.info("UserService.getUserByUsernameAndPassword() invoked");

		User user = this.userDao.getUserByUsername(username);

		if (user != null) {

			// hashing password
			String algorithm = "SHA-256";
			String hashedInputPassword = HashUtil.hashInputPassword(password.trim(), algorithm);

//			// salt = cann't loging because can't match the password
//			byte[] salt = HashUtil.createSalt();
//			String hashedInputPassword = HashUtil.hashInputPassword(password.trim(), algorithm, salt);

			logger.debug("hashedPassword {}", user.getPassword());
			logger.debug("hashedInputPasssword {}", hashedInputPassword);

			Boolean isCorrectPassword = hashedInputPassword.equals(user.getPassword());

			if (isCorrectPassword) {
				return user;
			} else {
				throw new FailedLoginException("Incorrect username and/or password");
			}
		} else {
			throw new FailedLoginException("Incorrect username and/or password");
		}

	}

	public List<User> getUserByUsernameAndEmail(String username, String email) throws SQLException {
		logger.info("UserService.getUserByUsernameAndEmail() invoked");

		List<User> users = this.userDao.getUserByUsernameAndEmail(username, email);

		return users;
	}

	public User signUp(User user) throws InvalidParameterException, SQLException, NoSuchAlgorithmException {
		logger.info("UserService.signUp() invoked");

		// hashing password
		String algorithm = "SHA-256";
		String hashedPassword = HashUtil.hashPassword(user.getPassword().trim(), algorithm);

//		// salt = cann't loging because can't match the password
//		byte[] salt = HashUtil.createSalt();
//		String hashedPassword = HashUtil.hashPassword(user.getPassword().trim(), algorithm, salt);

		user.setFirstName(user.getFirstName().trim());
		user.setLastName(user.getLastName().trim());
		user.setUsername(user.getUsername().trim());
		user.setPassword(hashedPassword);
		user.setEmail(user.getEmail().trim().toLowerCase());
		user.setUserRole(user.getUserRole().trim());

		User addedUser = this.userDao.signUp(user);
		return addedUser;
	}

}
