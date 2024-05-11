package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.DashboardPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Collections;
import java.util.List;

public class DashboardTest extends BaseTest {

    private final String PIPELINE_NAME = "The Pipeline";

    private final String MULTI_CONFIGURATION_PROJECT_NAME = "MCPN";

    private final String VIEW_NAME = "RedRover";


    @Test
    public void testDashboardMenu() {
        final List<String> expectedDashboardMenu = List.of(
                "New Item",
                "People",
                "Build History",
                "Manage Jenkins",
                "My Views");

        List<String> actualDashboardMenu = new DashboardPage(getDriver())
                .getDashboardMenuList();

        Assert.assertEquals(actualDashboardMenu, expectedDashboardMenu);
    }


    @Test(dependsOnMethods = "testDashboardMenu")
    public void testFolderChevronMenu() {

        String FOLDER_NAME = "A Folder";

        TestUtils.createFolderProject(this, FOLDER_NAME);
        TestUtils.goToMainPage(getDriver());

        final List<String> FOLDER_MENU = List.of(
                "Configure",
                "New Item",
                "Delete Folder",
                "People",
                "Build History",
                "Rename",
                "Credentials");

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(FOLDER_NAME)
                .getDropdownMenu();

        Assert.assertEquals(chevronMenu, FOLDER_MENU);
    }


    @Test(dependsOnMethods = "testFolderChevronMenu")
    public void testFreestyleProjectChevronMenu() {

        String FREESTYLE_PROJECT_NAME = "FREESTYLE";

        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);
        TestUtils.goToMainPage(getDriver());

        final List<String> FREESTYLE_PROJECT_MENU = List.of(
                "Changes",
                "Workspace",
                "Build Now",
                "Configure",
                "Delete Project",
                "Move",
                "Rename");

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .getDropdownMenu();

        Assert.assertEquals(chevronMenu, FREESTYLE_PROJECT_MENU);
    }


    @Test(dependsOnMethods = "testFreestyleProjectChevronMenu")
    public void testPipelineChevronMenu() {

        TestUtils.createPipelineProject(this, PIPELINE_NAME);
        TestUtils.goToMainPage(getDriver());

        final List<String> PIPELINE_MENU = List.of(
                "Changes",
                "Build Now",
                "Configure",
                "Delete Pipeline",
                "Move",
                "Full Stage View",
                "Rename",
                "Pipeline Syntax");

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(PIPELINE_NAME)
                .getDropdownMenu();

        Assert.assertEquals(chevronMenu, PIPELINE_MENU);
    }


    @Test(dependsOnMethods = "testPipelineChevronMenu")
    public void testMultiConfigurationProjectChevronMenu() {

        TestUtils.createMultiConfigurationProject(this, MULTI_CONFIGURATION_PROJECT_NAME);
        TestUtils.goToMainPage(getDriver());

        final List<String> MULTI_CONFIGURATION_PROJECT_MENU = List.of(
                "Changes",
                "Workspace",
                "Build Now",
                "Configure",
                "Delete Multi-configuration project",
                "Move",
                "Rename");

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(MULTI_CONFIGURATION_PROJECT_NAME)
                .getDropdownMenu();

        Assert.assertEquals(chevronMenu, MULTI_CONFIGURATION_PROJECT_MENU);
    }


    @Test(dependsOnMethods = "testMultiConfigurationProjectChevronMenu")
    public void testMultibranchPipelineChevronMenu() {

        String MULTIBRANCH_PIPELINE_NAME = "MULTIBRANCH_PIPELINE";

        TestUtils.createMultibranchProject(this, MULTIBRANCH_PIPELINE_NAME);
        TestUtils.goToMainPage(getDriver());

        final List<String> MULTIBRANCH_PIPELINE_MENU = List.of(
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

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(MULTIBRANCH_PIPELINE_NAME)
                .getDropdownMenu();

        Assert.assertEquals(chevronMenu, MULTIBRANCH_PIPELINE_MENU);
    }


    @Test(dependsOnMethods = "testMultibranchPipelineChevronMenu")
    public void testOrganizationFolderChevronMenu() {

        String ORGANIZATION_FOLDER_NAME = "RedRover Organization";

        TestUtils.createOrganizationFolderProject(this, ORGANIZATION_FOLDER_NAME);
        TestUtils.goToMainPage(getDriver());

        final List<String> ORGANIZATION_FOLDER_MENU = List.of(
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

        List<String> chevronMenu = new HomePage(getDriver())
                .openItemDropdown(ORGANIZATION_FOLDER_NAME)
                .getDropdownMenu();

        Assert.assertEquals(chevronMenu, ORGANIZATION_FOLDER_MENU);
    }


    @Test(dependsOnMethods = "testOrganizationFolderChevronMenu")
    public void testCreateView() {

        String createdViewName = new HomePage(getDriver())
                .clickPlusForCreateView()
                .setViewName(VIEW_NAME)
                .clickListViewRadioButton()
                .clickCreateViewButton()
                .clickOkButton()
                .getActiveViewName();

        Assert.assertEquals(createdViewName, VIEW_NAME);
    }


    @Test(dependsOnMethods = "testCreateView")
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
                .getPassiveViewNameBackgroundColor();

        String hoverColor = new HomePage(getDriver())
                .moveMouseToPassiveViewName()
                .getPassiveViewNameBackgroundColor();

        String activeColor = new HomePage(getDriver())
                .moveMouseToPassiveViewName()
                .mouseClick()
                .getActiveViewNameBackgroundColor();

        Assert.assertNotEquals(passiveColor, hoverColor);
        Assert.assertNotEquals(hoverColor, activeColor);
        Assert.assertNotEquals(activeColor, passiveColor);
    }


    @Test(dependsOnMethods = "testBackgroundColorOfViewName")
    public void testSortItemsByNameInTable() {

        List<String> sortedNameList = new HomePage(getDriver())
                .clickTitleForSortByName()
                .getItemList();

        List<String> reversedNameList = new HomePage(getDriver())
                .clickTitleForSortByName()
                .getItemList();

        List<String> twiceReversedNameList = reversedNameList
                .stream()
                .sorted(Collections.reverseOrder())
                .toList();

        Assert.assertEquals(sortedNameList, twiceReversedNameList);
    }
}
