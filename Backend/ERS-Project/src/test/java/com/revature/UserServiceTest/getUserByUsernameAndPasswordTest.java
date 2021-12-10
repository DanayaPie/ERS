package com.revature.UserServiceTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.SQLException;

import javax.security.auth.login.FailedLoginException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import com.revature.model.User;
import com.revature.service.UserService;
import com.revature.utility.JDBCUtility;

import constant.TableConstant;

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

	@Test
	public void getUserByUsernameAndPasswordPositiveTest() throws SQLException, FailedLoginException {
		User john = userSerive.getUserByUsernameAndPassword("JohnD", "John123");
		assertNotEquals(john, null);
	}
}
