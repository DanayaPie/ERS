package com.revature.glucode;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.revature.page.EmployeeHomepage;
import com.revature.page.FinanceManagerHomepage;
import com.revature.page.SignInPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SubmitReimbTest {
	private WebDriver driver;
	private WebDriverWait wdw;
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
	@When("I type in an amount of {int}")
	public void i_type_in_an_amount_of(Integer int1) {
		
	    this.employeeHomepage.getAmountInput().sendKeys(String.valueOf(int1));
	}
	@When("I select the type as {string}")
	public void i_select_the_type_as(String string) {
		this.employeeHomepage = new EmployeeHomepage(this.driver);
	    this.employeeHomepage.getTypeInput().sendKeys(string);
	}
	@When("I type in a description as {string}")
	public void i_type_in_a_description_as(String string) {
		this.employeeHomepage = new EmployeeHomepage(driver);
	    this.employeeHomepage.getDescriptionInput().sendKeys(string);
	}
	@When("I upload the receipt image")
	public void i_upload_the_receipt_image() {
		this.employeeHomepage = new EmployeeHomepage(this.driver);
	    this.employeeHomepage.getReceiptImageInput().click();
	    this.employeeHomepage.getReceiptImageInput().sendKeys("D:/Pie/Photos/Pie\\Logo.jpg");
	    
	}
	@When("I click the submit button")
	public void i_click_the_submit_button() {
		this.employeeHomepage = new EmployeeHomepage(this.driver);
	    this.employeeHomepage.getSubmitNewReimbButton().click();
	}
	@Then("I should see a new reimbursement")
	public void i_should_see_a_new_reimbursement() {
		
		int numberOfTrElements = driver.findElements(By.xpath("//div[@class='reimbTable']/table/tbody/tr")).size();
		
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(10));
	    
	    String mostRecentTrXpath = "//div[@class='reimbTable']/table/tbody/tr[" + (numberOfTrElements +1) + "]";
	    wdw.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(mostRecentTrXpath)));
	    
	    this.driver.findElement(By.xpath(mostRecentTrXpath));
	}
}
