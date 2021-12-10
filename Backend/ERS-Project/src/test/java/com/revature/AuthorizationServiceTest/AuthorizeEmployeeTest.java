package com.revature.AuthorizationServiceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.exception.UnauthorizedException;
import com.revature.model.User;
import com.revature.service.AuthorizationService;

public class AuthorizeEmployeeTest {

	private AuthorizationService authService;
	
	@BeforeEach
	public void setUp() {
		this.authService = new AuthorizationService();
	}
	
	@Test
	public void testAuthorizeEmployee_Positive() throws UnauthorizedException {
		User user = new User(96, "Raivena", "Valentine", "RaivenaV", "RV123", "raivena@gmail.com", "employee");
		
		this.authService.authorizeEmployee(user);
	}
	
	@Test
	public void testAuthroizeEmployee_UserIsNull_Negative() {
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployee(null);
		});
	}
	
	@Test
	public void testAuthorizeEmployee_UserIsFinanceManager_Negative() {
		User user = new User(96, "Raivena", "Valentine", "RaivenaV", "RV123", "raivena@gmail.com", "finance manager");
		
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployee(user);
		});
	}
}
