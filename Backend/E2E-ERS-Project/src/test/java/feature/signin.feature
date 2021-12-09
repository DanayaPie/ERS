Feature: Sign In

  Scenario: Successful finance manager login (positive)
    Given I am at the login page
    When I type in a username of "JohnD"
    And I type in a password of "John123"
    And I click the signin button
    Then I should be redirected to the finance manager homepage

	Scenario Outline: Successful employee login (positive)
		Given I am at the login page
		When I type in a username of <username>
		And I type in a password of <password>
		And I click the signin button
		Then I should be redicrected to the finance manager homepage
		
		Examples:
		| username | password |
		| JaneD | Jane123 |
		| DavidG | David123 |
		
