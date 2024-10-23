Feature: View event details

  Scenario: Open event details
    Given : ParentAps website is opened
    And : login with user credentials username "demo@parent.cloud" , password "12345678"
    When : Open event that contain name "Demo"
    Then : Check the details of event is returned successfully