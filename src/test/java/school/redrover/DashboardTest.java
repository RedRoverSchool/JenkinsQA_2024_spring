package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Collections;
import java.util.List;

@Epic("Dashboard")
public class DashboardTest extends BaseTest {

    private static final String PIPELINE_NAME = "The Pipeline";

    private static final String MULTI_CONFIGURATION_PROJECT_NAME = "MCPN";

    private static final String VIEW_NAME = "RedRover";

    private static final String VIEW_IN_PROGRESS = "in progress";

    private static final String MY_VIEW_NAME = "EmployeeView";

    @Story("US_16.005 Sidebar Menu Menu Items")
    @Description("Check all Sidebar Menu Items exist")
    @Test
    public void testSidebarMenuItemsList() {
        final List<String> expectedSidebarMenu = List.of(
                "New Item",
                "People",
                "Build History",
                "Manage Jenkins",
                "My Views");

        List<String> actualSidebarMenu = new HomePage(getDriver())
                .getSidebarMenuList();

        Allure.step("Expected results: All expected Sidebar Menu items exist");
        Assert.assertEquals(actualSidebarMenu, expectedSidebarMenu);
    }

    @Story("US_16.005 Sidebar Menu Items")
    @Description("Verify People Sidebar Menu Header")
    @Test
    public void testPeopleOnSidebarMenu() {
        final String expectedPageHeader = "People";
        String actualHeading = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .getHeadingText();

        Allure.step("Expected results: The header text should be " + expectedPageHeader);
        Assert.assertEquals(actualHeading, expectedPageHeader);
    }

    @Story("US_16.003 Item Chevron Menu > List of Menu Items")
    @Description("Check all Folder Chevron Menu Items exist")
    @Test(dependsOnMethods = "testSidebarMenuItemsList")
    public void testFolderChevronMenu() {
        final String folderName = "A Folder";

        final List<String> folderMenu = List.of(
                "Configure",
                "New Item",
                "Delete Folder",
                "People",
                "Build History",
                "Rename",
                "Credentials");

        TestUtils.createFolderProject(this, folderName);

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(folderName)
                .getDropdownMenu();

        Allure.step("Expected results: All expected Folder Chevron Menu items exist");
        Assert.assertEquals(chevronMenu, folderMenu);
    }

    @Story("US_16.003 Item Chevron Menu > List of Menu Items")
    @Description("Check all Freestyle Project Menu Items exist")
    @Test(dependsOnMethods = "testFolderChevronMenu")
    public void testFreestyleProjectChevronMenu() {

        String freestyleName = "Freestyle";

        final List<String> freestyleMenu = List.of(
                "Changes",
                "Workspace",
                "Build Now",
                "Configure",
                "Delete Project",
                "Move",
                "Rename");

        TestUtils.createFreestyleProject(this, freestyleName);

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(freestyleName)
                .getDropdownMenu();

        Allure.step("Expected results: All expected Freestyle Chevron Menu items exist");
        Assert.assertEquals(chevronMenu, freestyleMenu);
    }

    @Story("US_16.003 Item Chevron Menu > List of Menu Items")
    @Description("Check all Pipeline Project Menu Items exist")
    @Test(dependsOnMethods = "testFolderChevronMenu")
    public void testPipelineChevronMenu() {
        final List<String> pipelineMenu = List.of(
                "Changes",
                "Build Now",
                "Configure",
                "Delete Pipeline",
                "Move",
                "Full Stage View",
                "Rename",
                "Pipeline Syntax");

        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(PIPELINE_NAME)
                .getDropdownMenu();

        Allure.step("Expected results: All expected Pipeline Chevron Menu items exist");
        Assert.assertEquals(chevronMenu, pipelineMenu);
    }

    @Story("US_16.003 Item Chevron Menu > List of Menu Items")
    @Description("Check all Multi-configuration Project Menu Items exist")
    @Test(dependsOnMethods = "testFolderChevronMenu")
    public void testMultiConfigurationProjectChevronMenu() {

        final List<String> multiConfigurationMenu = List.of(
                "Changes",
                "Workspace",
                "Build Now",
                "Configure",
                "Delete Multi-configuration project",
                "Move",
                "Rename");

        TestUtils.createMultiConfigurationProject(this, MULTI_CONFIGURATION_PROJECT_NAME);

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(MULTI_CONFIGURATION_PROJECT_NAME)
                .getDropdownMenu();

        Allure.step("Expected results: All expected Multi-configuration Chevron Menu items exist");
        Assert.assertEquals(chevronMenu, multiConfigurationMenu);
    }

    @Story("US_16.003 Item Chevron Menu > List of Menu Items")
    @Description("Check all Multibranch Pipeline Menu Items exist")
    @Test(dependsOnMethods = "testFolderChevronMenu")
    public void testMultibranchPipelineChevronMenu() {

        String multibranchPipeline = "Multibranch Pipeline";

        final List<String> multibranchPipelineMenu = List.of(
                "Configure",
                "Scan Multibranch Pipeline Log",
                "Multibranch Pipeline Events",
                "Delete Multibranch Pipeline",
                "People",
                "Build History",
                "Move",
                "Rename",
                "Pipeline Syntax",
                "Credentials");

        TestUtils.createMultibranchProject(this, multibranchPipeline);

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(multibranchPipeline)
                .getDropdownMenu();

        Allure.step("Expected results: All expected Multibranch Pipeline Chevron Menu items exist");
        Assert.assertEquals(chevronMenu, multibranchPipelineMenu);
    }


    @Story("US_16.003 Item Chevron Menu > List of Menu Items")
    @Description("Check all Organization Folder Menu Items exist")
    @Test(dependsOnMethods = "testFolderChevronMenu")
    public void testOrganizationFolderChevronMenu() {
        String organizationFolderName = "RedRover Organization";

        final List<String> organizationFolderMenu = List.of(
                "Configure",
                "Scan Organization Folder Log",
                "Organization Folder Events",
                "Delete Organization Folder",
                "People",
                "Build History",
                "Move",
                "Rename",
                "Pipeline Syntax",
                "Credentials");

        TestUtils.createOrganizationFolderProject(this, organizationFolderName);

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(organizationFolderName)
                .getDropdownMenu();

        Allure.step("Expected results: All expected Organization Folder Chevron Menu items exist");
        Assert.assertEquals(chevronMenu, organizationFolderMenu);
    }

    @Story("US_16.007 Item Sorting")
    @Description("Check items sorting by Name in natural and reverse order")
    @Test(dependsOnMethods = "testOrganizationFolderChevronMenu")
    public void testSortItemsByNameInTable() {

        List<String> reverseSortedByClickNameList = new HomePage(getDriver())
                .clickTitleForSortByName()
                .getItemList();

        List<String> sortedByClickNameList = new HomePage(getDriver())
                .clickTitleForSortByName()
                .getItemList();

        List<String> reverseSortedByStreamNameList = reverseSortedByClickNameList
                .stream()
                .sorted(Collections.reverseOrder())
                .toList();

        List<String> sortedByStreamNameList = reverseSortedByClickNameList
                .stream()
                .sorted()
                .toList();

        Allure.step("Expected results:Table sorted by Name in reverse and then alphabetical order");
        Assert.assertEquals(reverseSortedByClickNameList, reverseSortedByStreamNameList);
        Assert.assertEquals(sortedByClickNameList, sortedByStreamNameList);
    }


    @Story("US_16.002 Create and edit View")
    @Description("Create the 'List View'")
    @Test(dependsOnMethods = "testPipelineChevronMenu")
    public void testCreateListView() {
        String createdViewName = new HomePage(getDriver())
                .clickPlusToCreateView()
                .typeViewName(VIEW_NAME)
                .clickListViewRadioButton()
                .clickCreateButtonForListView()
                .clickOkButton()
                .getActiveViewName();

        Allure.step("Expected results: New Created List View name is " + VIEW_NAME);
        Assert.assertEquals(createdViewName, VIEW_NAME);
    }

    @Story("US_16.002 Create and edit View")
    @Description("Verify all items added to New List View")
    @Test(dependsOnMethods =
            {"testCreateListView", "testPipelineChevronMenu", "testMultiConfigurationProjectChevronMenu"})
    public void testAddItemsToView() {

        List<String> projectNameList = new HomePage(getDriver())
                .clickViewName(VIEW_NAME)
                .clickEditViewOnSidebar()
                .checkProjectForAddingToView(PIPELINE_NAME)
                .checkProjectForAddingToView(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickOkButton()
                .getItemList();

        Allure.step("Expected results: " + PIPELINE_NAME + " and " + MULTI_CONFIGURATION_PROJECT_NAME
                + " Job items added to New List View");
        Assert.assertEquals(
                projectNameList,
                List.of(MULTI_CONFIGURATION_PROJECT_NAME, PIPELINE_NAME));
    }

    @Story("US_16.002 Create and edit View")
    @Description("Create the 'My View'")
    @Test
    public void testCreateMyView() {
        String newViewName =
                new HomePage(getDriver())
                        .clickCreateAJob()
                        .typeItemName(MULTI_CONFIGURATION_PROJECT_NAME)
                        .selectMultiConfigurationAndClickOk()
                        .clickLogo()
                        .clickPlusToCreateView()
                        .typeViewName(VIEW_NAME)
                        .clickMyViewRadioButton()
                        .clickCreateButtonForMyView()
                        .getActiveViewName();

        Allure.step("Expected results: New Created My View name is " + VIEW_NAME);
        Assert.assertEquals(newViewName, VIEW_NAME);
    }

    @Story("US_16.002 Create and edit View")
    @Description("Check background of active, hover and inactive Views")
    @Test(dependsOnMethods = "testCreateMyView")
    public void testBackgroundColorOfViewName() {

        String passiveColor = new HomePage(getDriver())
                .getColorOfPassiveViewNameBackground();

        String hoverColor = new HomePage(getDriver())
                .moveMouseToPassiveViewName()
                .getColorOfPassiveViewNameBackground();

        String activeColor = new HomePage(getDriver())
                .moveMouseToPassiveViewName()
                .mouseClick()
                .getColorOfActiveViewNameBackground();

        Allure.step("Expected results: colors of active, passive and hovered over Views are different");
        Assert.assertNotEquals(passiveColor, hoverColor);
        Assert.assertNotEquals(hoverColor, activeColor);
        Assert.assertNotEquals(activeColor, passiveColor);
    }

    @Story("US_16.004 Change Icon Size")
    @Description("Verify Icon Size changes")
    @Test(dependsOnMethods = "testCreateMyView")
    public void testChangeIconSize() {

        List<Integer> expectedSizeOfProjectIconList = List.of(16, 20, 24);

        for (int i = 0; i < expectedSizeOfProjectIconList.size(); i++) {
            int iconHeight = new HomePage(getDriver())
                    .clickIconForChangeSize(i)
                    .getProjectIconHeight();

            Allure.step("Expected results: Icon size is " + expectedSizeOfProjectIconList.get(i));
            Assert.assertEquals(iconHeight, expectedSizeOfProjectIconList.get(i));
        }
    }

    @Story("US_16.001 Start Page")
    @Description("Verify Start Page Header")
    @Test
    public void testStartPageHeading() {
        final String expectedHeader = "Welcome to Jenkins!";
        String actualHeading = new HomePage(getDriver())
                .getHeadingText();

        Allure.step("Expected results: Start Page Header is " + expectedHeader);
        Assert.assertEquals(actualHeading, expectedHeader);
    }

    @Story("US_16.006 Edit Dashboard Description")
    @Description("Test existence and ability to change Dashboard Description")
    @Test
    public void testEditDescriptionOnDashboard() {
        final String expectedDescription = "RedRover Projects";

        final String expectedLinkText = "Edit description";

        String actualDescription = new HomePage(getDriver())
                .clickEditDescription()
                .typeDescription(expectedDescription)
                .clickSaveButton()
                .getDescription();

        final String actualLinkText = new HomePage(getDriver())
                .getEditDescriptionLinkText();

        Allure.step("Expected results: Dashboard Description is " + expectedDescription
                + " and new text to change description is " + expectedLinkText);
        Assert.assertEquals(actualDescription, expectedDescription);
        Assert.assertEquals(actualLinkText, expectedLinkText);
    }

    @Test
    @Story("US_16.002 Create and edit View")
    @Description("Go to 'My Views' from  username dropdown on header")
    public void testGoToMyViewsFromUsernameDropdownOnHeader() {
        String views = "My Views";

        boolean textVisibility = new HomePage(getDriver())
                .getHeader().clickMyViewsOnHeaderDropdown()
                .isThereTextInBreadcrumbs(views);

        Assert.assertTrue(textVisibility, "'My Views' didn't open");
    }

    @Test
    @Story("US_16.002 Create and edit View")
    @Description("Add column into 'List View' and check it")
    public void testAddColumnIntoListView() {
        final String visible = "visible";

        TestUtils.createFolderProject(this, visible);

        int numberOfColumns = new HomePage(getDriver())
                .clickPlusToCreateView()
                .typeViewName(VIEW_IN_PROGRESS)
                .clickListViewRadioButton()
                .clickCreateButtonForListView()
                .clickCheckboxWithJobName(visible)
                .clickAddColumn()
                .selectAndClickOnColumnName("Project description")
                .clickOkButton()
                .clickLogo()
                .clickViewName(VIEW_IN_PROGRESS)
                .getSizeColumnHeaderList();

        Allure.step("Expected result: Number of columns on dashboard table - 7");
        Assert.assertEquals(numberOfColumns, 7, "Description column is not added");
    }

    @Test(dependsOnMethods = "testAddColumnIntoListView")
    @Story("US_16.002 Create and edit View")
    @Description("Change order of columns on dashboard table")
    public void testChangeOrderOfColumns() {
        List<String> columnNameText = new HomePage(getDriver())
                .clickViewName(VIEW_IN_PROGRESS)
                .clickEditViewOnSidebar()
                .scrollToColumnName("Project description")
                .dragAndDropDescriptionColumnToStatusColumn()
                .clickOkButton()
                .getColumnHeaderList();

        Allure.step("Expected result: Column 'Project description' is a first on dashboard table");
        Assert.assertEquals(columnNameText.get(0), "Description");
    }

    @Test
    @Story("US_16.002 Create and edit View")
    @Description("Check new column is added to the View Headline")
    public void testAddColumnToView() {
        final String pipelineName = "NewPipeline";
        final List<String> expectedPipelineViewList =
                List.of("S", "W", "Name" + "\n" + "  ↓",
                        "Last Success", "Last Failure", "Last Duration", "Git Branches");

        TestUtils.createPipelineProject(this, pipelineName);

        List<String> actualPipelineViewList = new HomePage(getDriver())
                .clickPlusToCreateView()
                .typeViewName(MY_VIEW_NAME)
                .clickListViewRadioButton()
                .clickCreateButtonForListView()
                .clickCheckboxWithJobName(pipelineName)
                .scrollToOkButton()
                .clickAddColumn()
                .selectAndClickOnColumnName("Git Branches")
                .clickOkButton()
                .getColumnHeaderList();

        Allure.step("Expected result: New column should be added to the view list");
        Assert.assertEquals(actualPipelineViewList, expectedPipelineViewList);
    }

    @Test(dependsOnMethods = "testAddColumnToView")
    @Story("US_16.002 Create and edit View")
    @Description("Verify a View list does not contain recently deleted View name")
    public void testDeleteView() {
        int viewNameListSize = new HomePage(getDriver())
                .clickViewName(MY_VIEW_NAME)
                .clickDeleteViewOnSidebar()
                .clickYesToConfirmDeletion()
                .getSizeOfViewNameList();

        Allure.step("Expected result:View name should be deleted from View List. Number of Viewes now - 2.");
        Assert.assertEquals(viewNameListSize, 2);
    }
}
