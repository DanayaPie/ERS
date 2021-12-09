package glucode;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import page.EmployeeHomepage;
import page.FinanceManagerHomepage;
import page.SignInPage;

public class SignInTest {

	private WebDriver driver;
	private SignInPage signInPage;
	private EmployeeHomepage employeeHomepage;
	private FinanceManagerHomepage financeManagerHomepage;

	@Given("I am at the login page")
	public void i_am_at_the_login_page() {

		// Selenium set up
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers/chromedriver.exe");

		this.driver = new ChromeDriver();

		this.driver.get("http://127.0.0.1:5500/index.html");
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

	@Then("I should be redirected to the finance manager homepage")
	public void i_should_be_redirected_to_the_finance_manager_homepage() {
		this.financeManagerHomepage = new FinanceManagerHomepage(this.driver);

		String expectedWelcomeHeadingText = "Welcome to Finance Manager Homepage";

		Assertions.assertEquals(expectedWelcomeHeadingText, this.financeManagerHomepage.getWelcomeHeading().getText());

		this.driver.quit();
	}
}
