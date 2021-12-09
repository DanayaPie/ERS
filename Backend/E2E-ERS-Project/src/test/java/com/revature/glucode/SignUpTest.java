package com.revature.glucode;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.page.EmployeeHomepage;
import com.revature.page.FinanceManagerHomepage;
import com.revature.page.SignInPage;
import com.revature.page.SignUpPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SignUpTest {
	
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
	
	@Given("I am at the login page to sign up")
	public void i_am_at_the_login_page_to_sign_up() {

		// Selenium set up
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers/chromedriver.exe");

		this.driver = new ChromeDriver();

		this.driver.get("http://127.0.0.1:5500/");
		this.signInPage = new SignInPage(driver);
	}

	@When("I click on the sign up button on the log in page")
	public void i_click_on_the_sign_up_button_on_the_log_in_page() {
		this.signInPage.getSignUpButton().click();
	}

	@When("I type in a first name of {string}")
	public void i_type_in_a_first_name_of(String string) {
		this.signUpPage.getFirstNameInput().sendKeys(string);
	}

	@When("I type in a last name of {string}")
	public void i_type_in_a_last_name_of(String string) {
		this.signUpPage.getLastNameInput().sendKeys(string);
	}

	@When("I type in an email of {string}")
	public void i_type_in_an_email_of(String string) {
		this.signUpPage.getemailInput().sendKeys(string);
	}

	@When("I type in an user role of {string}")
	public void i_type_in_an_user_role_of(String string) {
		this.signUpPage.getuserRoleInput().sendKeys(string);
	}

	@When("I click the sign up button on the sign up page")
	public void i_click_the_sign_up_button_on_the_sign_up_page() {
		this.signUpPage.getSignUpButton().click();
	}

	@Then("I should be redirected to the sign in page")
	public void i_should_be_redirected_to_the_sign_in_page() {
		this.signInPage = new SignInPage(this.driver);

		String expectedHeadingText = "Expense Reimbursement System";

		Assertions.assertEquals(expectedHeadingText, this.signInPage.getHeading().getText());
		
		this.driver.quit();
	}

}
