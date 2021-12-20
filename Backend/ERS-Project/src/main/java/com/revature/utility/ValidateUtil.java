package com.revature.utility;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.FailedLoginException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.exception.InvalidParameterException;
import com.revature.exception.ReimbursementNotFoundException;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.UserService;

import io.javalin.http.UploadedFile;

public class ValidateUtil {

	private static Logger logger = LoggerFactory.getLogger(ValidateUtil.class);

	private static UserService userService = new UserService();

	static List<String> allowedFileTypes = Arrays.asList("image/jpeg", "image/png", "image/gif");
	static List<String> reimbType = Arrays.asList("Lodging", "Travel", "Food", "Other");
	static List<String> userRoleList = Arrays.asList("employee", "finance manager");

	/*-
	 * 	'static' method = does not have to instantiate object to call the method
	 * 	
	 * 	className.methodName = call the method
	 */

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
		logger.info("ValidateUtil.verifySignUp() invoked()");

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

			if (StringUtils.equalsAnyIgnoreCase(userElement.getUsername(), user.getUsername())) {
				existingString.append("Username");
				existingBoolean = true;
			}
			if (StringUtils.equalsAnyIgnoreCase(userElement.getEmail(), user.getEmail())) {
				if (existingBoolean) {
					existingString.append(", email");
					existingBoolean = true;
				} else {
					existingString.append("Email");
					existingBoolean = true;
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

	public static void verifyAddReimb(String amount, String type, String description, UploadedFile file)
			throws InvalidParameterException {
		logger.info("ValidteUtil.verifyAddReimb() invoked");

		/*- 
		 *  building String dynamically
		 */
		boolean errorBoolean = false;

		StringBuilder userErrorString = new StringBuilder();

		if (StringUtils.isBlank(amount)) {
			userErrorString.append("Amount");
			errorBoolean = true;

		}

		if (StringUtils.isBlank(type)) {
			if (errorBoolean) {
				userErrorString.append(", type");
				errorBoolean = true;
			} else {
				userErrorString.append("Type");
				errorBoolean = true;
			}
		}

		if (StringUtils.isBlank(description)) {
			if (errorBoolean) {
				userErrorString.append(", description");
				errorBoolean = true;
			} else {
				userErrorString.append("Description");
				errorBoolean = true;
			}
		}

		if (file == null) {
			if (errorBoolean) {
				userErrorString.append(", receipt");
				errorBoolean = true;
			} else {
				userErrorString.append("Receipt");
				errorBoolean = true;
			}
		}

		if (errorBoolean) {
			userErrorString.append(" cannot be blank.");
			// .toString turn StringBuilder into a string
			throw new InvalidParameterException(userErrorString.toString());
		}
	}

	public static void verifyAndSetInputSpecificity(String amount, String type, String description, String mimeType,
			InputStream receipt) throws InvalidParameterException, SQLException {
		logger.info("ValidteUtil.verifyAndSetInputSpecificity() invoked");

		// amount
		try {

			Double amountReimb = Double.parseDouble(amount);

			if (amountReimb == 0 || amountReimb < 0) {
				throw new InvalidParameterException("Amount cannot be equal to or less than 0.");
			}
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Amount must be a number.");
		}

		// capitalized type
		if (type != null) {
			type = type.substring(0, 1).toUpperCase() + type.substring(1);
			// validate type
		}
		if (type == null || !reimbType.contains(type)) {
			throw new InvalidParameterException(
					"The type of reimbursement must be identified and be either 'Lodging', 'Travel', 'Food', 'Other'.");
		}

		// description
		if (description == null) {
			throw new InvalidParameterException("Please describe the reimbursement being requested.");
		}

		// receipt file
		if (!allowedFileTypes.contains(mimeType)) {
			throw new InvalidParameterException(
					"Receipt image must be uploaded and can only be PNG, JPEG, or GIF file.");
		}
	}

}
