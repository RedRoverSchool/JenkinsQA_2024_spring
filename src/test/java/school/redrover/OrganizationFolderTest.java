package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.OrganizationFolderConfigPage;
import school.redrover.model.OrganizationFolderProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

@Epic("Organization folder")
public class OrganizationFolderTest extends BaseTest {

    private static final String ORGANIZATION_FOLDER_NAME = "OrganizationFolder";
    private static final String ORGANIZATION_FOLDER_DESCRIPTION = "Some description of the organization folder.";

    @Test
    @Story("US_06.000  Create project")
    @Description("Verify the creation of a project via Sidebar menu.")
    public void testCreateViaSidebarMenu() {
        String itemPageHeading = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Allure.step("Expected result: Created Project is displayed on Home page");
        Assert.assertEquals(itemPageHeading, ORGANIZATION_FOLDER_NAME);
    }

    @Test
    @Story("US_06.000  Create project")
    @Description("Verify creation of the project with the default icon.")
    public void testCreateWithDefaultIcon() {
        String organizationFolderIcon = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .selectDefaultIcon()
                .clickSaveButton()
                .getOrganizationFolderIcon();

        Allure.step("Expected result: Folder icon is displayed");
        Assert.assertEquals(organizationFolderIcon, "Folder");
    }

    @Test(dependsOnMethods = "testCreateViaSidebarMenu")
    @Story("US_06.009  Add description to Organization Folder")
    @Description("Verify description can be added to project by clicking Add description.")
    public void testAddDescriptionViaAddDescriptionButton() {
        String textInDescription = new OrganizationFolderProjectPage(getDriver())
                .clickAddDescription()
                .setDescription(ORGANIZATION_FOLDER_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Allure.step("Expected result: Added description is displayed");
        Assert.assertEquals(textInDescription, ORGANIZATION_FOLDER_DESCRIPTION);
    }

    @Test
    @Story("US_06.007  Pipeline syntax")
    @Description("Verify access to the declarative documentation for pipeline syntax.")
    public void testPipelineSyntaxDeclarativeDocumentationAccess() {
        TestUtils.createOrganizationFolderProject(this, ORGANIZATION_FOLDER_NAME);

        String pageTitle = new HomePage(getDriver())
                .clickSpecificOrganizationFolderName(ORGANIZATION_FOLDER_NAME)
                .clickSidebarPipelineSyntax()
                .clickSidebarDeclarativeOnlineDocumentation()
                .getPipelineSyntaxTitle();

        Allure.step("Expected result: The page title is 'Pipeline Syntax'");
        Assert.assertEquals(pageTitle, "Pipeline Syntax");
    }

    @Test
    @Story("US_06.007  Pipeline syntax")
    @Description("Verify access to examples Reference.")
    public void testPipelineSyntaxExamplesAccess() {
        TestUtils.createOrganizationFolderProject(this, ORGANIZATION_FOLDER_NAME);

        String pageTitle = new HomePage(getDriver())
                .clickSpecificOrganizationFolderName(ORGANIZATION_FOLDER_NAME)
                .clickSidebarPipelineSyntax()
                .clickSidebarExamplesReference()
                .getPipelineExamplesTitle();

        Allure.step("Expected result: The page title is 'Pipeline Examples'.");
        Assert.assertEquals(pageTitle, "Pipeline Examples");
    }

    @Test(dependsOnMethods = "testCreateViaSidebarMenu")
    @Story("US_06.007  Pipeline syntax")
    @Description("Verify tooltips of the step Catch Error.")
    public void testCatchErrorStepTooltipsViaDashboardDropdown() {
        final List<String> expectedTooltipList = List.of(
                "Help for feature: catchError",
                "Help for feature: Message",
                "Help for feature: Build result on error",
                "Help for feature: Stage result on error",
                "Help for feature: Catch Pipeline interruptions");

        List<String> actualTooltipList = new HomePage(getDriver())
                .openItemDropdownWithSelenium(ORGANIZATION_FOLDER_NAME)
                .openItemPipelineSyntaxFromDropdown()
                .selectCatchError()
                .getCatchErrorTooltipList();

        Allure.step("Expected result: Tooltips for 'Catch Error' match " + expectedTooltipList);
        Assert.assertEquals(actualTooltipList, expectedTooltipList);
    }

    @Test(dependsOnMethods = "testCreateViaSidebarMenu")
    @Story("US_06.001  Use Configuration page")
    @Description("Verify sidebar menu visibility when accessing the configuration page of an organization folder.")
    public void testSidebarMenuVisibility() {
        boolean isSidebarVisible = new HomePage(getDriver())
                .clickSpecificOrganizationFolderName(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .isSidebarVisible();

        Allure.step("Expected result: Sidebar menu is visible on the configuration page.");
        Assert.assertTrue(isSidebarVisible);
    }

    @Test
    @Story("US_06.006  Rename Organization Folder")
    @Description("Verify that a project can be successfully renamed via the sidebar")
    public void testRenameProjectViaSidebarMenu() {
        final String newOrganizationFolderName = "New Organization Folder";

        TestUtils.createOrganizationFolderProject(this, ORGANIZATION_FOLDER_NAME);

        List<String> itemList = new HomePage(getDriver())
                .clickJobByName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderProjectPage(getDriver()))
                .clickSidebarRename()
                .setNewName(newOrganizationFolderName)
                .clickRename()
                .clickLogo()
                .getItemList();

        Allure.step("Renamed Project is displayed on Home Page");
        Assert.assertListContainsObject(itemList, newOrganizationFolderName,
                "The project is not renamed successfully");
    }

    @Test(dependsOnMethods = "testCreateViaSidebarMenu")
    @Story("US_06.003  Scan Organization Folder Log")
    @Description("Verify that the 'Scan Organization Folder Log' page is accessible")
    public void testScanOrganizationFolder() {
        String titleText = new HomePage(getDriver())
                .clickJobByName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderProjectPage(getDriver()))
                .clickSidebarScanOrganizationFolderLog()
                .getScanText();

        Allure.step("Title 'Scan Organization Folder Log' is displayed");
        Assert.assertEquals(titleText, "Scan Organization Folder Log",
                "The page title does not match 'Scan Organization Folder Log'");
    }

    @Test(dependsOnMethods = "testCreateViaSidebarMenu")
    @Story("US_06.000  Create project")
    @Description("Verify the creation of a project on Dashboard")
    public void testProjectPresenceOnDashboard() {
        List<String> itemList = new HomePage(getDriver())
                .getItemList();

        Allure.step("Expected result: Created Project is displayed on Home Page");
        Assert.assertListContainsObject(itemList, ORGANIZATION_FOLDER_NAME,
                ORGANIZATION_FOLDER_NAME + "is not on the dashboard");
    }

    @Test(dependsOnMethods = "testCreateViaSidebarMenu")
    @Story("US_06.007  Pipeline syntax")
    @Description("Verify that the pipeline syntax sidebar contains the expected list of items.")
    public void testPipelineSyntaxSidebarList() {
        final List<String> expectedPipelineSyntaxSidebarList = List.of("Back", "Snippet Generator", "Declarative Directive Generator",
                "Declarative Online Documentation", "Steps Reference",
                "Global Variables Reference", "Online Documentation", "Examples Reference",
                "IntelliJ IDEA GDSL");

        List<String> actualPipelineSyntaxSidebarList = new HomePage(getDriver())
                .clickJobByName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderProjectPage(getDriver()))
                .clickSidebarPipelineSyntax()
                .getPipelineSyntaxSidebarList();

        Allure.step("Expected result: The Pipeline Syntax sidebar should contain " + expectedPipelineSyntaxSidebarList);
        Assert.assertEquals(actualPipelineSyntaxSidebarList, expectedPipelineSyntaxSidebarList);
    }

    @Test
    @Story("US_06.005  Delete Organization Folder")
    @Description("Verify the deletion of a project via Sidebar menu.")
    public void testDeleteOrganizationFolderViaSidebar() {
        TestUtils.createOrganizationFolderProject(this, ORGANIZATION_FOLDER_NAME);

        List<String> itemList = new HomePage(getDriver())
                .clickJobByName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderProjectPage(getDriver()))
                .clickSidebarDelete()
                .clickYesForDeleteOrganizationFolder()
                .getItemList();

        Allure.step("Expected result: Deleted project is not present in the item list");
        Assert.assertListNotContainsObject(itemList, ORGANIZATION_FOLDER_NAME, "Did not removed!");
    }

    @Test
    @Story("US_06.001  Use Configuration page")
    @Description("Verify that the borders of the project recognizers filters are dashed in the configuration page.")
    public void testProjectRecognizersFiltersBordersAreDashed() {
        TestUtils.createOrganizationFolderProject(this, ORGANIZATION_FOLDER_NAME);

        boolean isProjectRecognizersBordersFiltersDashed = new HomePage(getDriver())
                .clickJobByName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderProjectPage(getDriver()))
                .clickConfigure()
                .scrollToProjectRecognizersBlock()
                .clickProjectRecognizersAddButton()
                .addPipelineJenkinsFileFilter()
                .areProjectRecognizersFiltersBordersDashed();

        Allure.step("Expected result: 'Project Recognizers' filter borders are dashed");
        Assert.assertTrue(isProjectRecognizersBordersFiltersDashed,
                "Filters boarders of Project Recognizers block  are not dashed");
    }

    @Test
    @Story("US_06.001  Use Configuration page")
    @Description("Verify that clicking anchor links in the configuration page navigates to the corresponding blocks.")
    public void testAnchorLinksLeadToCorrespondingBlocks() {
        TestUtils.createOrganizationFolderProject(this, ORGANIZATION_FOLDER_NAME);

        boolean isNavigatedToCorrespondingBlock = new HomePage(getDriver())
                .clickJobByName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderProjectPage(getDriver()))
                .clickConfigure()
                .isNavigatedToCorrespondingBlockClickingAnchorLink();

        Allure.step("Expected result: Clicking anchor links navigates to the corresponding blocks");
        Assert.assertTrue(isNavigatedToCorrespondingBlock, "An anchor link leads to a wrong block");
    }

    @Test
    @Story("US_06.001  Use Configuration page")
    @Description("Verify that the 'Throttle Builds' dropdown contains the correct time period options.")
    public void testThrottleBuildsTimePeriodOptions() {
        final List<String> expectedTimePeriodOptions = List.of("Second", "Minute", "Hour", "Day", "Week", "Month", "Year");

        TestUtils.createOrganizationFolderProject(this, ORGANIZATION_FOLDER_NAME);

        List<String> actualTimePeriodOptions = new HomePage(getDriver())
                .clickJobByName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderProjectPage(getDriver()))
                .clickConfigure()
                .scrollToPropertyStrategyBlock()
                .clickAddPropertyButton()
                .clickThrottleBuildsDropdownOption()
                .getTimePeriodOptions();

        Allure.step("Expected result: Time period options are displayed: " + expectedTimePeriodOptions);
        Assert.assertEquals(actualTimePeriodOptions, expectedTimePeriodOptions);
    }

    @Test
    @Story("US_06.001  Use Configuration page")
    @Description("Verify that the untrusted property checkboxes remain selected after saving the configuration.")
    public void testUntrustedPropertyCheckboxesSelectedUponSaving() {
        TestUtils.createOrganizationFolderProject(this, ORGANIZATION_FOLDER_NAME);

        OrganizationFolderConfigPage organizationFolderConfigPage = new HomePage(getDriver())
                .clickJobByName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderProjectPage(getDriver()))
                .clickConfigure()
                .scrollToPropertyStrategyBlock()
                .clickAddPropertyButton()
                .clickUntrustedDropdownOption()
                .selectUntrustedCheckboxes()
                .clickSaveButton()
                .clickConfigure()
                .scrollToPropertyStrategyBlock();

        Allure.step("Expected result: All untrusted property checkboxes remain selected after saving");
        Assert.assertTrue(organizationFolderConfigPage.areUntrustedCheckboxesSelected(),
                "Not all checkboxes are selected");
    }

    @Test
    @Story("US_06.001  Use Configuration page")
    @Description("Verify that the order of strategy properties can be changed in the configuration.")
    public void testStrategyPropertiesOrderCanBeChanged() {
        final List<String> expectedStrategyPropertiesList = List.of("Untrusted", "Throttle builds");

        TestUtils.createOrganizationFolderProject(this, ORGANIZATION_FOLDER_NAME);

        List<String> actualStrategyPropertiesList = new HomePage(getDriver())
                .clickJobByName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderProjectPage(getDriver()))
                .clickConfigure()
                .scrollToPropertyStrategyBlock()
                .clickAddPropertyButton()
                .clickThrottleBuildsDropdownOption()
                .clickAddPropertyButton()
                .clickUntrustedDropdownOption()
                .changeUntrustedAndThrottleBuildsOrder()
                .clickSaveButton()
                .clickConfigure()
                .getAddedStrategyPropertyList();

        Allure.step("Strategy Property list is in this order: " + expectedStrategyPropertiesList);
        Assert.assertEquals(actualStrategyPropertiesList, expectedStrategyPropertiesList);
    }
}
