
Feature: Sign Out

	Scenario: Log out as finance manager (positive)
		Given that I am log in as finance manager
		When I click on sign out button
		Then I should be redirected to the log in page
