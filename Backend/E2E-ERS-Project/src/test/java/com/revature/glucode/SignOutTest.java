package com.revature.glucode;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.page.EmployeeHomepage;
import com.revature.page.FinanceManagerHomepage;
import com.revature.page.SignInPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SignOutTest {

	private WebDriver driver;
	private SignInPage signInPage;
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
	
	@Given("I am log in as finance manager")
	public void i_am_log_in_as_finance_manager() {
		
		// Selenium set up
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers/chromedriver.exe");
		this.driver = new ChromeDriver();
		this.driver.get("http://127.0.0.1:5500/");
		this.signInPage = new SignInPage(driver);
		
	    this.signInPage.getUsernameInput().sendKeys("JohnD");
	    this.signInPage.getPasswordInput().sendKeys("John123");
	    this.signInPage.getLoginButton().click();
	    
	    this.financeManagerHomepage = new FinanceManagerHomepage(this.driver);

		String expectedWelcomeHeadingText = "Welcome to Finance Manager Homepage";

		Assertions.assertEquals(expectedWelcomeHeadingText, this.financeManagerHomepage.getWelcomeHeading().getText());
	}
	
	@Given("I am log in as employee")
	public void i_am_log_in_as_employee() {
		
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers/chromedriver.exe");
		this.driver = new ChromeDriver();
		this.driver.get("http://127.0.0.1:5500/");
		this.signInPage = new SignInPage(driver);
		
	    this.signInPage.getUsernameInput().sendKeys("JaneD");
	    this.signInPage.getPasswordInput().sendKeys("Jane123");
	    this.signInPage.getLoginButton().click();
	    
	    this.employeeHomepage = new EmployeeHomepage(this.driver);

		String expectedWelcomeHeadingText = "Welcome to Employee Homepage";

		Assertions.assertEquals(expectedWelcomeHeadingText, this.employeeHomepage.getWelcomeHeading().getText());
	}
	
	@When("I click on sign out button")
	public void i_click_on_sign_out_button() {
	    this.financeManagerHomepage = new FinanceManagerHomepage(this.driver);
	    
	    this.financeManagerHomepage.getSignOutButton().click();
	}
	
	@Then("I should be redirected to the log in page")
	public void i_should_be_redirected_to_the_log_in_page() {
		this.signInPage = new SignInPage(this.driver);
		
		String expectedHeadingText = "Expense Reimbursement System";
		
		Assertions.assertEquals(expectedHeadingText, this.signInPage.getHeading().getText());
				
		this.driver.quit();	
	}
}
