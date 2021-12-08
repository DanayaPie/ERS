package com.revature.utility;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.FailedLoginException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.exception.InvalidParameterException;
import com.revature.model.User;
import com.revature.service.UserService;

public class ValidateUtil {

	private static Logger logger = LoggerFactory.getLogger(ValidateUtil.class);
	
	private static UserService userService = new UserService();

	static List<String> allowedFileTypes = Arrays.asList("image/jpeg", "image/png", "image/gif");
	static List<String> reimbType = Arrays.asList("Lodging", "Travel", "Food", "Other");
	static List<String> userRoleList = Arrays.asList("employee", "finance manager");
	
	public static void verifyUsernameAndPassword(String username, String password) throws FailedLoginException {
		logger.info("ValidteUtil.verifyUsernameAndPassword() invoked");

		if (StringUtils.isBlank(username) && StringUtils.isBlank(password)) {
			throw new FailedLoginException("Username and password cannot be blank.");
		}

		if (StringUtils.isBlank(username)) {
			throw new FailedLoginException("Username cannot be blank.");
		}

		if (StringUtils.isBlank(password)) {
			throw new FailedLoginException("Password cannot be blank.");
		}
	}

	public static void verifySignUp(User user) throws InvalidParameterException, SQLException {
		logger.info("ValidateUtil.vetifySignUp() invoked()");

		/*-
		 * 	Building String dynamically 
		 */
		boolean userErrorBoolean = false;
		StringBuilder userErrorString = new StringBuilder();

		/*-
		 * 	check if blank
		 */
		if (StringUtils.isBlank(user.getFirstName().trim())) {
			userErrorString.append("First name");
			userErrorBoolean = true;
		}
		if (StringUtils.isBlank(user.getLastName().trim())) {
			if (userErrorBoolean) {
				userErrorString.append(", last name");
				userErrorBoolean = true;
			} else {
				userErrorString.append("Last name");
				userErrorBoolean = true;
			}
		}
		if (StringUtils.isBlank(user.getUsername().trim())) {
			if (userErrorBoolean) {
				userErrorString.append(", username");
				userErrorBoolean = true;
			} else {
				userErrorString.append("Username");
				userErrorBoolean = true;
			}
		}
		if (StringUtils.isBlank(user.getPassword().trim())) {
			if (userErrorBoolean) {
				userErrorString.append(", password");
				userErrorBoolean = true;
			} else {
				userErrorString.append("Password");
				userErrorBoolean = true;
			}
		}
		if (StringUtils.isBlank(user.getEmail().trim())) {
			if (userErrorBoolean) {
				userErrorString.append(", email");
				userErrorBoolean = true;
			} else {
				userErrorString.append("Email");
				userErrorBoolean = true;
			}
		}
		if (StringUtils.isBlank(user.getUserRole())) {
			if (userErrorBoolean) {
				userErrorString.append(", user role");
				userErrorBoolean = true;
			} else {
				userErrorString.append("User role");
				userErrorBoolean = true;
			}
		}
		if (userErrorBoolean) {
			userErrorString.append(" cannot be blank.");
			// .toString turn StringBuilder into a string
			throw new InvalidParameterException(userErrorString.toString());
		}

		/*-
		 *  check user role
		 */
		if (user.getUserRole().trim().toLowerCase() != null
				&& !userRoleList.contains(user.getUserRole().trim().toLowerCase())) {
			throw new InvalidParameterException("User role must be 'employee' or 'finance manager'.");
		}

		/*-
		 * 	check if username and email exist
		 */
		boolean existingBoolean = false;
		StringBuilder existingString = new StringBuilder();
		
		List<User> users = userService.getUserByUsernameAndEmail(user.getUsername(), user.getEmail());
			
		for (User userElement : users) {
		
			logger.debug("signUp username email {} {}", user.getUsername(), user.getEmail());
			logger.debug("database username email {} {}", userElement.getUsername(), userElement.getEmail());
			
			if (StringUtils.equalsAnyIgnoreCase(userElement.getUsername(),user.getUsername())) {
				existingString.append("Username");
				existingBoolean = true;
			}
			if (StringUtils.equalsAnyIgnoreCase(userElement.getEmail(),user.getEmail())) {
				if (existingBoolean) {
					existingString.append(", email");
					existingBoolean = true;
				} else {
					userErrorString.append("Email");
					userErrorBoolean = true;
				}
			}
			if (existingBoolean) {
				existingString.append(" already exist.");
				throw new InvalidParameterException(existingString.toString());
			}
		}
		
		/*-
		 *  limit username and password length
		 */
		boolean limitUsernamePasswordBoolean = false;
		StringBuilder limitUsernamePasswordString = new StringBuilder();
		
		if (user.getUsername().length() > 20) {
			limitUsernamePasswordString.append("Username");
			limitUsernamePasswordBoolean = true;
		}
		if (user.getPassword().length() > 20) {
			if (existingBoolean) {
				limitUsernamePasswordString.append(", password");
				limitUsernamePasswordBoolean = true;
			} else {
				limitUsernamePasswordString.append("Password");
				limitUsernamePasswordBoolean = true;
			}
		}
		if (limitUsernamePasswordBoolean) {
			limitUsernamePasswordString.append(" cannot be more than 20 characters.");
			throw new InvalidParameterException(limitUsernamePasswordString.toString());
		}
	}
}
