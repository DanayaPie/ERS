Feature: Submit Reimbursement

	Scenario: Sucessfully submit new reimbursement (positive)
		Given I am log in as employee
		When I type in an amount of "96"
		And I select the type as "Food"
		And I type in a description as "pie"
		And I upload the receipt image
		And I click the submit button
		Then I should see a new reimbursement
