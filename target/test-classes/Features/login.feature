Feature: login functionality test

   Scenario Outline: Check if I can make a payment to see payment declined error
    Given I am on Ryanair start page
    
      And I entered the '<username>' and '<password>' and click on login button
      And I search for a flight from dublin to '<flightTo>' on flightDate
      And I pick a first offer
      And I select the seat and click on continue
      And I do a check out
     When I am trying to enter the contact number and select the insurance
     Then I insert payment details with card number and card SecurityCode and click on pay

    Examples:
      |username            |password| flightTo       | 
      |toyedo4448@noobf.com|IND@319a| London Stansted| 



	