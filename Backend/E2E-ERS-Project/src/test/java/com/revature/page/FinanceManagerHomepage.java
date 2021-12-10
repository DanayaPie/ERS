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
	
	@FindBy(xpath="//button[@id='logout-btn']")
	private WebElement signOutButton;
	
	@FindBy(xpath="//tbody/tr[1]/td[5]/button[1]")
	private WebElement viewReceiptImage;
	
	@FindBy(xpath="//span[contains(text(),'×')]")
	private WebElement closeViewReceiptImage;
	
	@FindBy(xpath="//input[@id='amount']")
	private WebElement amountInput;
	
	@FindBy(xpath="//select[@id='type-dropdown']")
	private WebElement typeInput;
	
	@FindBy(xpath="//input[@id='description']")
	private WebElement descriptionInput;

	@FindBy(xpath="//input[@id='receipt-file']")
	private WebElement receiptImageInput;
	
	@FindBy(xpath="//p[@id='ptag']")
	private WebElement errorMessage;
	
	@FindBy(xpath="//button[@id='submit-reimbursement-btn']")
	private WebElement submitNewReimbButton;
	
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
	
	public WebElement getViewReceiptImage() {
		return this.viewReceiptImage;
	}
	
	public WebElement getCloseViewReceiptImage() {
		return this.closeViewReceiptImage;
	}
	
	public WebElement getAmountInput() {
		return this.amountInput;
	}
	
	public WebElement getTypeInput() {
		return this.typeInput;
	}
	
	public WebElement getDescriptionInput() {
		return this.descriptionInput;
	}
	
	public WebElement getReceiptImageInput() {
		return this.receiptImageInput;
	}
	
	public WebElement getSubmitNewReimbButton() {
		return this.submitNewReimbButton;
	}
	
	public WebElement getErrorMessage() {
		return this.errorMessage;
	}
}
