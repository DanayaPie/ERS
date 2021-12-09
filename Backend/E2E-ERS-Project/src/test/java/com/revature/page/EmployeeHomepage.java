package com.revature.page;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeeHomepage {

	private WebDriver driver;
	private WebDriverWait wdw; // explicit waits

	@FindBy(xpath = "//*[text()='Welcome to Employee Homepage']") // PageFactory annotation
	private WebElement welcomeHeading;

	public EmployeeHomepage(WebDriver driver) {
		this.driver = driver;

		// wait for a max of 15 sec before throwing an exception
		this.wdw = new WebDriverWait(driver, Duration.ofSeconds(15));
		
		// PageFactor initialization
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getWelcomeHeading() {
		return this.wdw.until(ExpectedConditions.visibilityOf(welcomeHeading));
	}
}
