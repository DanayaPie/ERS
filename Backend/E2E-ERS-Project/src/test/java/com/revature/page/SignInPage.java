package com.revature.page;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage {

	private WebDriver driver;
	private WebDriverWait wdw; // explicit waits

	@FindBy(xpath = "//*[text()='Expense Reimbursement System')]")
	private WebElement heading;

	@FindBy(xpath = "//input[@id='username-input']") // PageFactory annotation
	private WebElement usernameInput;

	@FindBy(id = "password-input") // PageFactory annotation
	private WebElement passwordInput;

	@FindBy(xpath = "//button[@id='login-btn']") // PageFactory annotation
	private WebElement loginButton;

	@FindBy(xpath = "//a[@id='signup']")
	private WebElement signUpButton;

	@FindBy(id = "errorMessage") // PageFactory annotation
	private WebElement errorMessage;

	public SignInPage(WebDriver driver) {
		this.driver = driver;
		this.wdw = new WebDriverWait(driver, Duration.ofSeconds(5));

		// PageFactor initialization
		PageFactory.initElements(driver, this);
	}

	public WebElement getHeading() {
		return this.heading;
	}

	public WebElement getUsernameInput() {
		return this.usernameInput;
	}

	public WebElement getPasswordInput() {
		return this.passwordInput;
	}

	public WebElement getLoginButton() {
		return this.loginButton;
	}

	public WebElement getSignUpButton() {
		return this.signUpButton;
	}

	public WebElement getErrorMessagElement() {

		/*-
		 * 	Explicitely wait at most 5 seconds for the error message to appear
		 * 	- may not have to wait but just in case, so it does not throw an exception
		 */
		return this.wdw.until(ExpectedConditions.visibilityOf(this.errorMessage));
	}

}
