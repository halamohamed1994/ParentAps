Feature: Create new event

#  Scenario: Check create event fields are displayed successfully
#    Given : ParentAps website already opened
#    And : login with user credentials username "demo@parent.cloud" and password "12345678"
#    And : User opened event that contain name "Demo"
#    When : Click on calendar icon from the top of page
#    And : Click on create event
#    Then : Check all fields of creation are displayed successfully

  Scenario: Create event with valid data
    Given : ParentAps website already opened
    And : login with user credentials username "demo@parent.cloud" and password "12345678"
    And : User opened event that contain name "Demo"
    When : Click on calendar icon from the top of page
    And : Click on create event
    Then : Fill all fields with valid data

