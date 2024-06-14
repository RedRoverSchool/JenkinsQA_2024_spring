package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Collections;
import java.util.List;

public class DashboardTest extends BaseTest {

    private static final String PIPELINE_NAME = "The Pipeline";

    private static final String MULTI_CONFIGURATION_PROJECT_NAME = "MCPN";

    private static final String VIEW_NAME = "RedRover";


    @Test
    public void testDashboardMenu() {
        final List<String> expectedSidebarMenu = List.of(
                "New Item",
                "People",
                "Build History",
                "Manage Jenkins",
                "My Views");

        List<String> actualSidebarMenu = new HomePage(getDriver())
                .getSidebarMenuList();

        Assert.assertEquals(actualSidebarMenu, expectedSidebarMenu);
    }

    @Test(dependsOnMethods = "testDashboardMenu")
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

        Assert.assertEquals(actualDescription, expectedDescription);
        Assert.assertEquals(actualLinkText, expectedLinkText);
    }

    @Test(dependsOnMethods = "testDashboardMenu")
    public void testFolderChevronMenu() {
        String folderName = "A Folder";

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

        Assert.assertEquals(chevronMenu, folderMenu);
    }


    @Test(dependsOnMethods = "testFolderChevronMenu")
    public void testFreestyleProjectChevronMenu() {
        String freestyleProjectName = "FREESTYLE";

        final List<String> freestyleProjectMenu = List.of(
                "Changes",
                "Workspace",
                "Build Now",
                "Configure",
                "Delete Project",
                "Move",
                "Rename");

        TestUtils.createFreestyleProject(this, freestyleProjectName);

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(freestyleProjectName)
                .getDropdownMenu();

        Assert.assertEquals(chevronMenu, freestyleProjectMenu);
    }


    @Test(dependsOnMethods = "testFreestyleProjectChevronMenu")
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

        Assert.assertEquals(chevronMenu, pipelineMenu);
    }


    @Test(dependsOnMethods = "testPipelineChevronMenu")
    public void testMultiConfigurationProjectChevronMenu() {
        final List<String> multiConfigurationProjectMenu = List.of(
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

        Assert.assertEquals(chevronMenu, multiConfigurationProjectMenu);
    }


    @Test(dependsOnMethods = "testMultiConfigurationProjectChevronMenu")
    public void testMultibranchPipelineChevronMenu() {
        String multibranchPipelineName = "MULTIBRANCH_PIPELINE";

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

        TestUtils.createMultibranchProject(this, multibranchPipelineName);

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(multibranchPipelineName)
                .getDropdownMenu();

        Assert.assertEquals(chevronMenu, multibranchPipelineMenu);
    }


    @Test(dependsOnMethods = "testMultibranchPipelineChevronMenu")
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

        Assert.assertEquals(chevronMenu, organizationFolderMenu);
    }


    @Test(dependsOnMethods = "testOrganizationFolderChevronMenu")
    public void testCreateListView() {
        String createdViewName = new HomePage(getDriver())
                .clickPlusToCreateView()
                .setViewName(VIEW_NAME)
                .clickListViewRadioButton()
                .clickCreateViewButton()
                .clickOkButton()
                .getActiveViewName();

        Assert.assertEquals(createdViewName, VIEW_NAME);
    }


    @Test(dependsOnMethods = "testCreateListView")
    public void testAddItemsToView() {

        List<String> projectNameList = new HomePage(getDriver())
                .clickViewName(VIEW_NAME)
                .clickEditViewOnSidebar()
                .checkProjectForAddingToView(PIPELINE_NAME)
                .checkProjectForAddingToView(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickOkButton()
                .getProjectNames();

        Assert.assertEquals(
                projectNameList,
                List.of(MULTI_CONFIGURATION_PROJECT_NAME, PIPELINE_NAME));
    }


    @Test(dependsOnMethods = "testAddItemsToView")
    public void testChangeIconSize() {

        List<Integer> expectedSizeOfProjectIconList = List.of(16, 20, 24);

        for (int i = 0; i < expectedSizeOfProjectIconList.size(); i++) {
            int iconHeight = new HomePage(getDriver())
                    .clickIconForChangeSize(i)
                    .getProjectIconHeight();

            Assert.assertEquals(iconHeight, expectedSizeOfProjectIconList.get(i));
        }
    }

    @Test(dependsOnMethods = "testChangeIconSize")
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

        Assert.assertNotEquals(passiveColor, hoverColor);
        Assert.assertNotEquals(hoverColor, activeColor);
        Assert.assertNotEquals(activeColor, passiveColor);
    }


    @Test(dependsOnMethods = "testBackgroundColorOfViewName")
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

        Assert.assertEquals(reverseSortedByClickNameList, reverseSortedByStreamNameList);
        Assert.assertEquals(sortedByClickNameList, sortedByStreamNameList);
    }

    @Test
    public void testCreateMyView() {
        String newViewName =
                new HomePage(getDriver())
                        .clickCreateAJob()
                        .setItemName(MULTI_CONFIGURATION_PROJECT_NAME)
                        .selectMultiConfigurationAndClickOk()
                        .clickLogo()
                        .clickPlusToCreateView()
                        .setViewName(VIEW_NAME)
                        .clickMyViewRadioButton()
                        .clickCreateMyView()
                        .getNewViewName();

        Assert.assertEquals(newViewName, VIEW_NAME);
    }

    @Test
    public void testPeopleOnSidebar() {
        String actualHeading = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .getHeadingText();

        Assert.assertEquals(actualHeading, "People");
    }

    @Test
    public void testStartPageHeading() {
        String actualHeading = new HomePage(getDriver())
                .getHeadingText();

        Assert.assertEquals(actualHeading, "Welcome to Jenkins!");
    }
}
