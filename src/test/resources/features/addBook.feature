@Book
Feature: Testing book application

  Scenario: User can add book to list
    When User get the book list
    Then Verify Book list is empty
    When User add "SRE 101" "John Smith" to list
    Then User get the book list
    And user verify selected book is added to list

  Scenario: User can't add book similar book to list
    When User add "SRE 101" "John Smith" to list
    When User add "SRE 101" "John Smith" to list
    Then User should get similar book already added error

  Scenario Outline: User should not miss the book title or author
    When User add "<title>" "<author>" to list
    Then User should get "<error>" message from add books service

    Examples:
      | title   | author     | error                                   |
      | SRE 101 |            | Field 'author' is required.             |
      |         | John Smith | Field 'title' is required. Hata Demo :) |


  Scenario: User can't add book with id
    When User add 2 "SRE 101" "John Smith" to list
    Then User should get "Field id is only read only." message from add books service


  Scenario: User can navigate added book with id
    When User add "SRE 101" "John Smith" to list
    When User navigate the book with 11
    Then User should get URL not found error
    Then User navigate the book with 1
    And User can navigate book information