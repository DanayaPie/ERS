package com.revature.AuthorizationServiceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.exception.UnauthorizedException;
import com.revature.model.User;
import com.revature.service.AuthorizationService;

public class AuthorizeFinanceManagerTest {

	private AuthorizationService authService;
	
	@BeforeEach
	public void setUp() {
		this.authService = new AuthorizationService();
	}
	
	@Test
	public void authorizeFinanceManagerTest_Positive() throws UnauthorizedException {
		User user = new User(96, "Raivena", "Valentine", "RaivenaV", "RV123", "raivena@gmail.com", "finance manager");
		
		this.authService.authorizeFinanceManager(user);
	}
	
	@Test
	public void authorizeFinanceManagerTest_UserIsNull_Negative() {
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeFinanceManager(null);
		});
	}
	
	@Test
	public void authorizeFinanceManagerTest_UserIsEmployee_Negative() {
		User user = new User(96, "Raivena", "Valentine", "RaivenaV", "RV123", "raivena@gmail.com", "employee");
		
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeFinanceManager(user);
		});
	}
}
