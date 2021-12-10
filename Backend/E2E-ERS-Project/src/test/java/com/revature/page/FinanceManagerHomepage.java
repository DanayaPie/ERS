package com.revature.page;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FinanceManagerHomepage {

	private WebDriver driver;
	private WebDriverWait wdw; // explicit waits

	// PageFactory annotation = @FindBy...
	@FindBy(xpath = "//h1[contains(text(),'Welcome to Finance Manager Homepage')]")
	private WebElement welcomeHeading;

	@FindBy(xpath = "//button[@id='logout-btn']")
	private WebElement signOutButton;

	@FindBy(xpath = "//select[@id='status-dropdown']")
	private WebElement filterStatusInput;

	@FindBy(xpath = "//button[@id='status-filter-btn']")
	private WebElement filterStatusButton;

	@FindBy(xpath = "//tbody//tr//td//select")
	private WebElement upDateStatusInput;

	@FindBy(xpath = "//tbody/tr/td[12]/button[1]")
	private WebElement upDateStatusButton;

	public FinanceManagerHomepage(WebDriver driver) {
		this.driver = driver;

		// wait for a max of 10 sec before throwing an exception
		this.wdw = new WebDriverWait(driver, Duration.ofSeconds(10));

		// PageFactor initialization
		PageFactory.initElements(driver, this);
	}

	public WebElement getWelcomeHeading() {
		return this.wdw.until(ExpectedConditions.visibilityOf(welcomeHeading));
	}

	public WebElement getSignOutButton() {
		return this.signOutButton;
	}

	public WebElement getFilterStatusInput() {
		return this.filterStatusInput;
	}

	public WebElement getFilterStatusButton() {
		return this.filterStatusButton;
	}

	public WebElement getUpDateStatusInput() {
		return this.upDateStatusInput;
	}

	public WebElement getUpDateStatusButton() {
		return this.upDateStatusButton;
	}
}
