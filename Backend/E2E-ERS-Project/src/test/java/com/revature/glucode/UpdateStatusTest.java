package com.revature.glucode;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.revature.page.EmployeeHomepage;
import com.revature.page.FinanceManagerHomepage;
import com.revature.page.SignInPage;

public class UpdateStatusTest {
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
	
}
