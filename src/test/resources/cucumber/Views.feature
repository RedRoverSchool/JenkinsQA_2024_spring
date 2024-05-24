Feature: Views

  Scenario: Items in Views are sorted alphabetically by default
    When Go to New Item via Create a job
    And Enter Item name "Freestyle"
    And Set Item type as Freestyle project, click Ok and go to Configure page
    And Save configuration and go to Freestyle project page
    And Go to home page

    And Go to New Job to create another item
    And Enter Item name "Pipeline"
    And Set Item type as Pipeline, click Ok and go to Configure page
    And Save configuration and go to Pipeline project page
    And Go to home page

    And Go to New Job to create another item
    And Enter Item name "OrganizationFolder"
    And Set Item type as Organization Folder, click Ok and go to Configure page
    And Save configuration and go to Organization Folder page
    And Go to home page

    And Click '+' to create New View
    And Type View name "ViewToVerifySorting"
    And Set View type as 'My View'
    And Click Create button upon choosing My View

    Then Sorting column text is "Name ↓"
    Then Sorted items by name in view list order should be:
      | Freestyle |
      | OrganizationFolder |
      | Pipeline |
