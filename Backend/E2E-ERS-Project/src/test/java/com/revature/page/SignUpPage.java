package com.revature.page;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUpPage {

	private WebDriver driver;
	private WebDriverWait wdw; // explicit waits
	
	@FindBy(xpath="//h1[contains(text(),'Sign Up')]")
	private WebElement heading;
	
	@FindBy(xpath="//input[@id='firstName-input']")
	private WebElement firstNameInput;
	
	@FindBy(xpath="//input[@id='lastName-input']")
	private WebElement lastNameInput;
	
	@FindBy(xpath="//input[@id='username-input']")
	private WebElement usernameInput;
	
	@FindBy(xpath="//input[@id='password-input']")
	private WebElement passwordInput;
	
	@FindBy(xpath="//input[@id='email-input']")
	private WebElement emailInput;
	
	@FindBy(xpath="//input[@id='userRole-input']")
	private WebElement userRoleInput;
	
	@FindBy(xpath="//div[@id='errorMessage']")
	private WebElement errorMessage;
	
	@FindBy(xpath="//button[@id='signUp-btn']")
	private WebElement signUpButton;
	
	public SignUpPage(WebDriver driver) {
		this.driver = driver;
		this.wdw = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getHeading() {
	
		return this.wdw.until(ExpectedConditions.visibilityOf(this.heading));
	}

	public WebElement getFirstNameInput() {
		return this.firstNameInput;
	}
	
	public WebElement getLastNameInput() {
		return this.lastNameInput;
	}
	
	public WebElement getUsernameInput() {
		return this.usernameInput;
	}
	
	public WebElement getPasswordInput() {
		return this.passwordInput;
	}
	
	public WebElement getemailInput() {
		return this.emailInput;
	}
	
	public WebElement getuserRoleInput() {
		return this.userRoleInput;
	}
	
	public WebElement geterrorMessage() {
		return this.errorMessage;
	}
	
	public WebElement getSignUpButton() {
		return this.signUpButton;
	}
	
}
