
Feature: Sign Out

	Scenario: Log out as finance manager (positive)
		Given I am log in as finance manager
		When I click on sign out button
		Then I should be redirected to the log in page

	Scenario: Log out as employee (positive)
		Given I am log in as employee
		When I click on sign out button
		Then I should be redirected to the log in page