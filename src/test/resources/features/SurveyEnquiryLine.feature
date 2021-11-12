Feature:  Survey Enquiry Line

#  Initial set up steps to get to address list
#  1.The user (SEL operator) is in CCUI find case page
#  2.The user selects the "Find Case option"
#  3.The user is presented with callers postcode search option page
#  4.The user enters the callers postcode
#  5.The user clicks "Continue"
#  6.The system displays a list of addresses for the postcode

  @SelPostcodeSearch_T6 @Setup @TearDown
  Scenario: SEL Postcode search - A case exists with users address
    Given The SEL operator finds a case by postcode and a list of addresses is displayed
    When The user selects the callers address from the list of addresses which is number "6"
    And The user clicks "Continue"
    Then The system displays the case details for the callers address

  @SelPostcodeSearch_T8 @Setup @TearDown
  Scenario: SEL Postcode search - A case does not exist with users address
    Given The SEL operator finds a case by postcode and a list of addresses is displayed
    When The user selects the callers address from the list of addresses which is number "6A"
    And  The user clicks "Continue"
    Then CCSvc returns no case for the selected address
    And The user is presented with a no cases message

  @SelPostcodeSearch_T9 @Setup @TearDown
  Scenario: SEL Postcode search - Cannot find address for postcode
    Given The SEL operator finds a case by postcode and a list of addresses is displayed
    When The user selects "I cannot find the caller's address" from the list
    And The user clicks "Continue"
    Then The user is presented with a address not found message for postcode "EX4 1EH"