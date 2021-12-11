package com.revature.UserServiceTest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.security.auth.login.FailedLoginException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import com.revature.model.User;
import com.revature.service.UserService;
import com.revature.utility.JDBCUtility;

import constant.TableConstant;

/*-
 * 	H2 Unit test
 * 
 * 	required JUnit 4 and run via Java
 * 	---> working on it
 */

public class getUserByUsernameAndPasswordTest {

	// part of springframework dependency that connect to the database

	UserService userSerive = new UserService();

	@Before
	public void setUp() throws ScriptException, SQLException {
		
		ScriptUtils.executeSqlScript(JDBCUtility.getConnection(),
				new ClassPathResource(TableConstant.CREATE_USER_TABLE_SCRIPT));
		
		ScriptUtils.executeSqlScript(JDBCUtility.getConnection(),
				new ClassPathResource(TableConstant.INSERT_USER_SCRIPT));
	}

	@After
	public void dropTable() throws ScriptException, SQLException {
		ScriptUtils.executeSqlScript(JDBCUtility.getConnection(),
				new ClassPathResource(TableConstant.DROP_USER_SCRIPT));
	}

//	@Test
//	public void getUserByUsernameAndPasswordTest_Positive() throws SQLException, FailedLoginException, NoSuchAlgorithmException {
//		User user = userSerive.getUserByUsernameAndPassword("JohnD", "John123");
//		assertNotEquals(user, null);
//	}
	
//	@Test
//	public void getUserWithInvalidUsernameAndPassowrdTest_Negative() throws FailedLoginException, SQLException {
//		User user = userSerive.getUserByUsernameAndPassword("J123", "J123");
//		exceptionRule.expect(NumberFormatException.class);
//	}
}
