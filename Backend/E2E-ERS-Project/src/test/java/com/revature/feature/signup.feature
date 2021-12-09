Feature: Sign Up

	Scenario Outline: Successful finance manager signup (positive)
		Given I am at the login page to sign up
		When I click on the sign up button on the log in page
		And I type in a first name of "Ash"
		And I type in a last name of "DaQueen"
		And I type in a username of "AshQueen"
		And I type in a password of "Ash123"
		And I type in an email of "ash_daqueen@gmail.com"
		And I type in an user role of "finance manager"
		And I click the sign up button on the sign up page
		Then I should be redirected to the sign in page
		
		Examples:
		| firstName | lastName | username | password | email | userRole |
		| "Ash" | "DaQueen" | "AshQueen" | "Ash123" | "ash_daqueen@gmail.com" | "finance manager" |
		| "Garen" | "Spin" | "GarenS" | "Garen123" | "garen_spin@gmail.com" | "employee" |
