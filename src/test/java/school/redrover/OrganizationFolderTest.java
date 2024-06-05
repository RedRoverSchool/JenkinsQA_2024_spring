package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.OrganizationFolderConfigPage;
import school.redrover.model.OrganizationFolderProjectPage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class OrganizationFolderTest extends BaseTest {

    private static final String ORGANIZATION_FOLDER_NAME = "OrganizationFolder";

    private static final String SCAN_ORGANIZATION_TEXT = "Scan Organization Folder Log";

    private static final String ORGANIZATION_FOLDER_DESCRIPTION = "Some description of the organization folder.";

    @Test
    public void testCreateViaNewItem() {

        String getItemPageHeading = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(getItemPageHeading, ORGANIZATION_FOLDER_NAME);
    }

    @Test
    public void testCreateWithDefaultIcon() {

        String organizationFolderIcon = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .selectDefaultIcon()
                .clickSaveButton()
                .getOrganizationFolderIcon();

        Assert.assertEquals(organizationFolderIcon, "Folder");
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testAddDescription() {

        String textInDescription = new OrganizationFolderProjectPage(getDriver())
                .clickAddOrEditDescription()
                .setDescription(ORGANIZATION_FOLDER_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(textInDescription, ORGANIZATION_FOLDER_DESCRIPTION);
    }

    @Test
    public void testPipelineSyntaxDocumentationAccess() {
        String pageTitle = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificOrganizationFolderName(ORGANIZATION_FOLDER_NAME)
                .clickPipelineSyntax()
                .clickOnlineDocumentation()
                .getPipelineSyntaxTitle();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/pipeline/"));
        Assert.assertEquals(pageTitle, "Pipeline Syntax");
    }

    @Test
    public void testPipelineSyntaxExamplesAccess() {
        String pageTitle = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificOrganizationFolderName(ORGANIZATION_FOLDER_NAME)
                .clickPipelineSyntax()
                .clickExamplesReference()
                .getPipelineExamplesTitle();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/examples/"));
        Assert.assertEquals(pageTitle, "Pipeline Examples");
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
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

        Assert.assertEquals(actualTooltipList, expectedTooltipList);
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testSidebarMenuVisibility() {
        boolean isSidebarVisible = new HomePage(getDriver())
                .clickSpecificOrganizationFolderName(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .isSidebarVisible();

        Assert.assertTrue(isSidebarVisible);
    }

    @Test
    public void testRenameOrganizationFolder() {
        final String newOrganizationFolderName = "New Organization Folder";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickOnRenameButton()
                .setNewName(newOrganizationFolderName)
                .clickRename()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(newOrganizationFolderName));
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testScanOrganizationFolder() {
        String scan = new HomePage(getDriver())
                .clickJobByName(ORGANIZATION_FOLDER_NAME,
                        new OrganizationFolderProjectPage(getDriver()))
                .clickScan()
                .getScanText();

        Assert.assertEquals(scan, SCAN_ORGANIZATION_TEXT);
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testFindOrganizationFolderOnDashboard() {
        HomePage homePage = new HomePage(getDriver());

        Assert.assertListContainsObject(homePage.getItemList(), ORGANIZATION_FOLDER_NAME,
                ORGANIZATION_FOLDER_NAME + "is not on the dashboard");
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testPipelineSyntaxSidebarList() {
        final List<String> expectedPipelineSyntaxSidebarList = List.of("Back", "Snippet Generator", "Declarative Directive Generator",
                "Declarative Online Documentation", "Steps Reference",
                "Global Variables Reference", "Online Documentation", "Examples Reference",
                "IntelliJ IDEA GDSL");

        List<String> actualPipelineSyntaxSidebarList = new HomePage(getDriver())
                .clickJobByName(ORGANIZATION_FOLDER_NAME, new OrganizationFolderProjectPage(getDriver()))
                .clickPipelineSyntax()
                .getPipelineSyntaxSidebarList();

        Assert.assertEquals(actualPipelineSyntaxSidebarList, expectedPipelineSyntaxSidebarList);
    }

    @Test
    public void testDeleteOrganizationFolder() {

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickDeleteOnSidebar()
                .clickYesForDeleteOrganizationFolder()
                .getItemList();

        Assert.assertListNotContainsObject(itemList, ORGANIZATION_FOLDER_NAME, "Did not removed!");
    }

    @Test
    public void testProjectRecognizersFiltersBordersAreDashed() {

        boolean projectRecognizersBordersFiltersDashed = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickConfigure()
                .scrollToProjectRecognizersBlock()
                .clickProjectRecognizersAddButton()
                .addPipelineJenkinsFileFilter()
                .areProjectRecognizersFiltersBordersDashed();

        Assert.assertTrue(projectRecognizersBordersFiltersDashed,
                "Filters boarders of Project Recognizers block  are not dashed");
    }

    @Test
    public void testAnchorLinksLeadToCorrespondingBlocks() {

        boolean isNavigatedToCorrespondingBlock = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickConfigure()
                .isNavigatedToCorrespondingBlockClickingAnchorLink();

        Assert.assertTrue(isNavigatedToCorrespondingBlock, "An anchor link leads to a wrong block");
    }

    @Test
    public void testThrottleBuildsTimePeriodOptions() {
        final List<String> expectedTimePeriodOptions = List.of("Second", "Minute", "Hour", "Day", "Week", "Month", "Year");

        List<String> actualTimePeriodOptions = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickConfigure()
                .scrollToPropertyStrategyBlock()
                .clickAddPropertyButton()
                .clickThrottleBuildsDropdownOption()
                .getTimePeriodOptions();

        Assert.assertEquals(actualTimePeriodOptions, expectedTimePeriodOptions);
    }

    @Test
    public void testUntrustedPropertyCheckboxesSelectedUponSaving() {

        OrganizationFolderConfigPage organizationFolderConfigPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickConfigure()
                .scrollToPropertyStrategyBlock()
                .clickAddPropertyButton()
                .clickUntrustedDropdownOption()
                .selectUntrustedCheckboxes()
                .clickSaveButton()
                .clickConfigure()
                .scrollToPropertyStrategyBlock();

        Assert.assertTrue(organizationFolderConfigPage.areUntrustedCheckboxesSelected(),
                "Not all checkboxes are selected");
        Assert.assertEquals(organizationFolderConfigPage.getSelectedCheckboxesSize(), 11);
    }

    @Test
    public void testStrategyPropertiesOrderCanBeChanged() {
        final List<String> expectedStrategyPropertiesList = List.of("Untrusted", "Throttle builds");

        List<String> actualStrategyPropertiesList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
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

        Assert.assertEquals(actualStrategyPropertiesList, expectedStrategyPropertiesList);
    }
}
