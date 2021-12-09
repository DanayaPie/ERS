package page;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage {

	private WebDriver driver;
	private WebDriverWait wdw; // explicit waits
	
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
	
	public SignUpPage(WebDriver driver) {
		this.driver = driver;
		this.wdw = new WebDriverWait(driver, Duration.ofSeconds(8));
	}
	
}
