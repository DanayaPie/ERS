package com.revature.AuthorizationService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.exception.UnauthorizedException;
import com.revature.model.User;
import com.revature.service.AuthorizationService;

public class AuthorizeFinanceManagerAndEmployeeTest {
	private AuthorizationService authService;
	
	@BeforeEach
	public void setUp() {
		this.authService = new AuthorizationService();
	}
	
	@Test
	public void authorizeFinanceManagerAndEmployeeTest_UserIsFinanceManager_Positive() throws UnauthorizedException {
		User user = new User(96, "Raivena", "Valentine", "RaivenaV", "RV123", "raivena@gmail.com", "finance manager");
		
		this.authService.authorizeFinanceManagerAndEmployee(user);
	}
	
	@Test
	public void authorizeFinanceManagerTest_UserIsEmployee_Positive() throws UnauthorizedException {
		User user = new User(96, "Raivena", "Valentine", "RaivenaV", "RV123", "raivena@gmail.com", "employee");
		
		this.authService.authorizeFinanceManagerAndEmployee(user);
	}
	
	@Test
	public void authorizeFinanceManagerTest_UserIsNull_Negative() {
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeFinanceManagerAndEmployee(null);
		});
	}
	
	@Test
	public void authorizeFinanceManagerTest_UserIsNotFinanceManagerNorEmployee_Negative() {
		User user = new User(96, "Raivena", "Valentine", "RaivenaV", "RV123", "raivena@gmail.com", "manager");
		
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeFinanceManagerAndEmployee(user);
		});
	}
	

}
