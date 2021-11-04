Feature:  Survey Enquiry Line

  @SelPostcodeSearch @Setup @TearDown
  Scenario: SEL Postcode search - A case exists with users address
  	Given The SEL operator is in CCUI find case page
    And The user selects the "Find Case via postcode" option
    When The user is presented with callers postcode search option page
    Then The user enters the callers postcode
    And The user clicks "Continue"
    Then The system displays a list of addresses for the postcode
    And The user selects the callers address from the list of address which is number "6"
    Then The user clicks "Continue"
    Then The system displays the case details for the callers address

  @SelPostcodeSearch @Setup @TearDown
  Scenario: SEL Postcode search - A case does not exist with users address
    Given The SEL operator is in CCUI find case page
    And The user selects the "Find Case via postcode" option
    When The user is presented with callers postcode search option page
    Then The user enters the callers postcode
    And The user clicks "Continue"
    Then The system displays a list of addresses for the postcode
    And The user selects the callers address from the list of address which is number "6A"
    Then The user clicks "Continue"
    Then CCSvc returns no case for the selected address
    And The user is presented with a no cases message

  @SelPostcodeSearch @Setup @TearDown
  Scenario: SEL Postcode search - Cannot find address for postcode
    Given The SEL operator is in CCUI find case page
    And The user selects the "Find Case via postcode" option
    When The user is presented with callers postcode search option page
    Then The user enters the callers postcode
    And The user clicks "Continue"
    Then The system displays a list of addresses for the postcode
    And The user selects "I cannot find the caller's address" from the list
    Then The user clicks "Continue"
    And The user is presented with a address not found message for postcode "EX4 1EH"