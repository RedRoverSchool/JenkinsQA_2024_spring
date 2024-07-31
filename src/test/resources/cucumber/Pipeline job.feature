Feature: Pipeline job

  Scenario: Create job
    When Go to New Item
    And Enter job name "job name"
    And Set Pipeline type and click Ok leading to Configure page
    And Save configuration leading to Pipeline project page
    And Return home
    Then Pipeline job name "job name" is listed on Dashboard

  Scenario: Rename job
    When Click "job name" leading to Pipeline Project page
    And Go to Rename on Sidebar and go to Rename page
    And Clear input field and type "new job name" in the input field
    And Click Rename to confirm and go to Pipeline Project page
    And Return home
    Then  Pipeline job name "new job name" is listed on Dashboard

  Scenario: Build job
    When Click on Green build triangle for "new job name"
    And Click "new job name" leading to Pipeline Project page
    Then Permalinks build information is displayed

    Scenario: Delete job
      When Click "new job name" leading to Pipeline Project page
      And Go to Delete Pipeline on sidebar leading to Delete Dialog
      And Click Yes leading to return home
      Then Pipeline job name "new job name" is not listed on Dashboard