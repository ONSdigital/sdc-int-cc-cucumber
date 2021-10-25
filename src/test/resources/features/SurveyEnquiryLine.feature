Feature:  Survey Enquiry Line

  @SelPostcodeSearch @Setup @TearDown
  Scenario: SEL Postcode search
  	Given A case exists with my address
    And I have navigated to the SEL Postcode search page
    When I have entered a UK postcode and clicked Continue
    And I select the desired address from a list
    Then The case is displayed for selection

