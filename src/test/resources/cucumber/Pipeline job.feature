Feature: Pipeline job

  Scenario: Create the job
    When Go to New Item
    And Enter job name "job name"
    And Set Pipeline type and click Ok leading to Configure page
    And Save configuration leading to Pipeline project page
    And Return home
    Then Pipeline job name "job name" is listed on Dashboard

    Scenario: Add description to the job
      When Click "job name" leading to Pipeline Project page
      And Click Add description
      And Type "job description" to description input field
      And Click Save
      Then Description "job description" is displayed on project page

  Scenario: Rename the job
    When Click "job name" leading to Pipeline Project page
    And Go to Rename on Sidebar and go to Rename page
    And Clear input field and type "new job name" in the input field
    And Click Rename to confirm and go to Pipeline Project page
    And Return home
    Then  Pipeline job name "new job name" is listed on Dashboard

    Scenario: Disable the job
      When Click "new job name" leading to Pipeline Project page
      And Click Disable Project
      Then Message "This project is currently disabled" is displayed

      Scenario: Enable the job back
        When Click "new job name" leading to Pipeline Project page
        And Click Enable button
        And Return home
        Then Green build triangle for the job "new job name" is displayed

  Scenario: Build the job
    When Click on Green build triangle for "new job name"
    And Click "new job name" leading to Pipeline Project page
    Then Permalinks build information is displayed

    Scenario: Delete the job
      When Click "new job name" leading to Pipeline Project page
      And Go to Delete Pipeline on sidebar leading to Delete Dialog
      And Click Yes leading to return home
      Then Pipeline job name "new job name" is not listed on Dashboard