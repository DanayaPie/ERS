package com.revature.glucode;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.page.EmployeeHomepage;
import com.revature.page.FinanceManagerHomepage;
import com.revature.page.SignInPage;
import com.revature.page.SignUpPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SignInTest {

	private Logger logger = LoggerFactory.getLogger(SignInTest.class);

	private WebDriver driver;
	private SignInPage signInPage;
	private SignUpPage signUpPage;
	private EmployeeHomepage employeeHomepage;
	private FinanceManagerHomepage financeManagerHomepage;

	@BeforeEach
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers/chromedriver.exe");

		this.driver = new ChromeDriver();

		this.driver.get("http://127.0.0.1:5500/");
		this.signInPage = new SignInPage(driver);

	}

	@AfterEach
	public void closeTest() {

		this.driver.quit();
	}

	@Given("I am at the login page")
	public void i_am_at_the_login_page() {

		// Selenium set up
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers/chromedriver.exe");

		this.driver = new ChromeDriver();

		this.driver.get("http://127.0.0.1:5500/");
		this.signInPage = new SignInPage(driver);
	}

	@When("I type in a username of {string}")
	public void i_type_in_a_username_of(String string) {
		this.signInPage.getUsernameInput().sendKeys(string);
	}

	@When("I type in a password of {string}")
	public void i_type_in_a_password_of(String string) {
		this.signInPage.getPasswordInput().sendKeys(string);
	}

	@When("I click the signin button")
	public void i_click_the_signin_button() {
		this.signInPage.getLoginButton().click(); // click LogInButton
	}
	
	@When("I click the sign up button")
	public void i_click_the_sign_up_button() {
		
		this.signInPage.getSignUpButton().click();
	}

	/*-
	 *  Positive Tests
	 */
	@Then("I should be redirected to the finance manager homepage")
	public void i_should_be_redirected_to_the_finance_manager_homepage() {
		this.financeManagerHomepage = new FinanceManagerHomepage(this.driver);

		String expectedWelcomeHeadingText = "Welcome to Finance Manager Homepage";

		Assertions.assertEquals(expectedWelcomeHeadingText, this.financeManagerHomepage.getWelcomeHeading().getText());

		this.driver.quit();
	}

	@Then("I should be redirected to the employee homepage")
	public void i_should_be_redirected_to_the_employee_homepage() {
		this.employeeHomepage = new EmployeeHomepage(this.driver);

		String expectedWelcomeHeadingText = "Welcome to Employee Homepage";

		Assertions.assertEquals(expectedWelcomeHeadingText, this.employeeHomepage.getWelcomeHeading().getText());

		this.driver.quit();
	}
	
	@Then("I should be redirected to the sign up page")
	public void i_should_be_redirected_to_the_sign_up_page() {
		this.signUpPage = new SignUpPage(this.driver);
		
		String expectedHeadingText = "Sign Up";

		logger.info("getText() {}", this.signUpPage.getHeading().getText());
		
		Assertions.assertEquals(expectedHeadingText, this.signUpPage.getHeading().getText());

		this.driver.quit();
	}

	/*-
	 *  Negative Tests
	 */
	@Then("I should see a message of {string}")
	public void i_should_see_a_message_of(String string) {
		String actual = this.signInPage.getErrorMessagElement().getText();

		Assertions.assertEquals(string, actual);

		this.driver.quit();
	}
}
