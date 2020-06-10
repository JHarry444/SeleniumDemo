#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Logging in

  Scenario Outline: Test logging into account
    Given I have created an account with "<username>" and "<password>"
    And I am on the login page
    When I enter the "<username>" and "<password>"
    Then I verify the login "<status>"

    Examples: 
      | username | password | status           |
      | user1    | pass1    | Successful Login |
      | user2    | pass2    | Successful Login |
