Feature: Sign Up

	Scenario Outline: Successful <userRole> signup (positive)
		Given I am at the login page
		When I click on the sign up button on the log in page
		And I type in a first name of <firstName>
		And I type in a last name of <lastName>
		And I type in a username of <username>
		And I type in a password of <password>
		And I type in an email of <email>
		And I type in an user role of <userRole>
		And I click the sign up button on the sign up page
		Then I should be redirected to the sign in page
		
		Examples:
		| firstName | lastName | username | password | email | userRole |
		| "Ash" | "DaQueen" | "AshQueen" | "Ash123" | "ash_daqueen@gmail.com" | "finance manager" |
		| "Garen" | "Spin" | "GarenS" | "Garen123" |