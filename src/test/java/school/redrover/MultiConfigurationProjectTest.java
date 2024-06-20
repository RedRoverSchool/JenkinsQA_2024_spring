package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.HomePage;
import school.redrover.model.MultiConfigurationProjectPage;
import school.redrover.model.MultibranchPipelineConfigPage;
import school.redrover.model.SearchResultPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.Random;

@Epic("Multi-configuration project")
public class MultiConfigurationProjectTest extends BaseTest {
    private static final String PROJECT_NAME = "MCProject";
    private static final String RANDOM_PROJECT_NAME = TestUtils.randomString();
    private static final String FOLDER_NAME = "Folder_name";

    private String generateRandomNumber() {
        Random r = new Random();
        int randomNumber = r.nextInt(100) + 1;
        return String.valueOf(randomNumber);
    }


    @Test
    @Story("US_03.004  Rename project")
    @Description("Check, an existing project can be renamed via dropdown")
    public void testRenameProjectViaDashboardDropdown() {
        final  String ADD_TO_PROJECT_NAME = "New";
        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        String  newProjectName = new HomePage(getDriver())
                .openItemDropdown(PROJECT_NAME)
                .clickRenameOnDropdownForMultiConfigurationProject()
                .changeProjectNameWithoutClear(ADD_TO_PROJECT_NAME)
                .clickRenameButton()
                .getProjectName();

        Allure.step("Expected result : Project name has  been changed .");
        Assert.assertEquals(newProjectName,
                "Project " + PROJECT_NAME + ADD_TO_PROJECT_NAME,
                "Project name has not been changed");
    }

    @Test
    @Story("US_03.001  Add/edit description")
    @Description("Adding the project description")
    public void testAddDescription() {
        final String TEXT  = "❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️";

        String description = TestUtils.createMultiConfigurationProject(this, RANDOM_PROJECT_NAME)
                .clickSpecificMultiConfigurationProjectName(RANDOM_PROJECT_NAME)
                .clickAddDescriptionButton()
                .addOrEditDescription(TEXT)
                .clickSaveDescription()
                .getDescriptionText();

        Allure.step("Expected result :" + TEXT + " has been added to description.");
        Assert.assertEquals(description, TEXT);
    }

    @Test
    @Story("US_03.001  Add/edit description")
    @Description("Add some text above to existing description of the project.")
    public void testEditDescriptionWithoutDelete() {
        final String TEXT = "qwerty123";
        final String ADDITIONAL_TEXT = "AAA";

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        String descriptionText = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickAddDescriptionButton()
                .addOrEditDescription(TEXT)
                .clickSaveDescription()
                .clickLogo()
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickAddDescriptionButton()
                .addOrEditDescription(ADDITIONAL_TEXT)
                .clickSaveDescription()
                .getDescriptionText();

        Allure.step("Expected result :  the description has been edited.");
        Assert.assertEquals(descriptionText, ADDITIONAL_TEXT + TEXT);
    }

    @Test
    @Story("US_03.001  Add/edit description")
    @Description("Check, the text in a preview field equals to the text in the description field.")
    public void testDescriptionPreview() {
        final String TEXT = "I want to see preview";
        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        String previewText =new HomePage(getDriver())
                        .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                        .clickAddDescriptionButton()
                        .addOrEditDescription(TEXT)
                        .clickPreview()
                        .getPreviewText();

        Allure.step("Expected result : the preview field contains the same text as in the description field . ");
        Assert.assertEquals(previewText, TEXT);
    }

    @Test
    @Story("US_03.000 Create project")
    @Description("Check creating project by copying the exist one")
    public void testMakeCopyMultiConfigurationProject() {
        final String NEW_PROJECT_NAME = "MCProject copy";

        List<String> projectList = TestUtils.createNewItem(
                this,
                        PROJECT_NAME,
                        TestUtils.Item.MULTI_CONFIGURATION_PROJECT)
                .clickNewItem()
                .setItemName(NEW_PROJECT_NAME)
                .typeItemNameInCopyFrom(PROJECT_NAME)
                .clickOkAnyway(new MultibranchPipelineConfigPage(getDriver()))
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Allure.step("Expected result : the list of items on the Dashboard contains the project as wel as it's copy .");
        Assert.assertTrue(projectList.contains(NEW_PROJECT_NAME) && projectList.contains(PROJECT_NAME));
    }

    @Test
    @Story("US_03.001  Add/edit description")
    @Description("Delete project description")
    public void testDeleteProjectDescription() {
        final String DESCRIPTION_TEXT  = "This is project description";
        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        MultiConfigurationProjectPage multiConfigurationProjectPage = new MultiConfigurationProjectPage(getDriver());

        boolean isDescriptionDeleted = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, multiConfigurationProjectPage)
                .clickAddDescriptionButton()
                .addOrEditDescription(DESCRIPTION_TEXT )
                .clickSaveDescription()
                .clickLogo()
                .clickJobByName(PROJECT_NAME, multiConfigurationProjectPage)
                .clickAddDescriptionButton()
                .clearDescription()
                .clickSaveDescription()
                .isDescriptionEmpty();

        Allure.step("Expected result : the description field is empty. ");
        Assert.assertTrue(isDescriptionDeleted);
    }

    @Test
    @Story("US_03.002  Enable/Disable project")
    @Description("Disable project by toggle")
    public void testProjectDisableByToggle() {

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        boolean statusToggle = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickConfigureButton()
                .clickToggleSwitch()
                .clickApply()
                .getStatusToggle();

        Allure.step("Expected result : the project is disable.");
        Assert.assertFalse(statusToggle);
    }

    @Test(dependsOnMethods = "testProjectDisableByToggle")
    @Story("US_03.002  Enable/Disable project")
    @Description("Get tooltip information 'Enable or disable the current project' by hovering on a Toggle Switch")
    public void testCheckTooltipEnablingMultiConfigurationProject() {

        String toggleTooltipText = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickConfigureButton()
                .hoverOverToggleSwitch()
                .getToggleTooltipText();

        Allure.step("Expected result : 'Enable or disable the current project' text has been appeared.");
        Assert.assertEquals(toggleTooltipText, "Enable or disable the current project");
    }

    @Test
    @Story("US_03.003 Delete project")
    @Description("Checking 'Yes' button color when delete project")
    public void testYesButtonColorDeletingMultiConfigurationProjectInSidebar() {
        final String EXPECTED_COLOR_NONE = "#e6001f";

        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        String actualYesButtonColor = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickSidebarDelete()
                .getYesButtonColorDeletingViaSidebar();

        Allure.step("Expected result : 'Yes' button color is red (#e6001f).");
        Assert.assertEquals(actualYesButtonColor, EXPECTED_COLOR_NONE);
    }

    @Test
    @Story("US_03.000 Create project")
    @Description("Create project with empty name")
    public void testCreateProjectWithoutName() {
        final String EMPTY_NAME = "";
        final String ERROR_MESSAGE = "This field cannot be empty";

        CreateNewItemPage createNewItemPage =
                new HomePage(getDriver())
                        .clickNewItem()
                        .setItemName(EMPTY_NAME)
                        .selectMultiConfiguration();

        boolean isErrorMessageCorrect = createNewItemPage.getErrorMessageEmptyName().contains(ERROR_MESSAGE);
        boolean isCanNotPressOkButton = createNewItemPage.isOkButtonNotActive();

        Allure.step("Expected result : 'This field cannot be empty' error message has appeared and 'OK' button is not active.");
        Assert.assertTrue(isErrorMessageCorrect && isCanNotPressOkButton);
    }

    @Test
    @Story("US_03.000 Create project")
    @Description("Create project with valid name")
    public void testCreateMultiConfigurationProject() {
        List<String> projectNameList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Allure.step("Expected result : the list of items on the Dashboard contains the project '" + PROJECT_NAME + "'");
        Assert.assertTrue(projectNameList.contains(PROJECT_NAME));
    }


    @Test(dependsOnMethods = "testCreateMultiConfigurationProject")
    @Story("US_03.004  Rename project")
    @Description("Check,an existing project can be renamed")
    public void testRenameMCProject() {

        HomePage homePage = new HomePage(getDriver());

        homePage
                .clickJobByName(PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickRenameInMenu()
                .changeProjectNameWithClear(RANDOM_PROJECT_NAME)
                .clickRenameButton()
                .clickLogo();
        Allure.step("Expected result :the list of items on the Dashboard contains the project with new name, but doesn't contain old project");
        Assert.assertTrue(homePage.isItemExists(RANDOM_PROJECT_NAME) && !homePage.isItemExists(PROJECT_NAME));
    }

    @Test
    @Story("US_03.003 Delete project")
    @Description("Delete an existing project via Dropdown menu")
    public void testDeleteProjectViaDropdown() {

        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        List<String> itemsList = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickBreadcrumbsProjectDropdownArrow()
                .clickDropdownDelete()
                .clickYes(new HomePage(getDriver()))
                .getItemList();

        Allure.step("Expected result :the list of items on the Dashboard doesn't contain deleted project.");
        Assert.assertListNotContainsObject(itemsList, PROJECT_NAME,
                "Project not deleted");
    }

    @Test
    @Story("US_03.005  Move project to Folder")
    @Description("Move the project to the Folder via Dropdown menu")
    public void testMoveProjectToFolderViaDropdown() {
        final String FOLDER_NAME = "Folder";

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);
        TestUtils.createNewItem(this, FOLDER_NAME, TestUtils.Item.FOLDER);

        boolean isProjectInsideFolder = new HomePage(getDriver())
                .openItemDropdownWithSelenium(PROJECT_NAME)
                .selectMoveFromDropdown()
                .selectFolder(FOLDER_NAME)
                .clickMove()
                .isProjectInsideFolder(PROJECT_NAME, FOLDER_NAME);

        Allure.step("Expected result : the project is inside of the folder");
        Assert.assertTrue(isProjectInsideFolder);
    }

    @Test
    @Story("US_03.003 Delete project")
    @Description("Delete an existing project via left-sidebar menu")
    public void testDeleteMultiConfigurationProjectFromMenu() {

        HomePage homePage = TestUtils.createMultiConfigurationProject(this, RANDOM_PROJECT_NAME);

        boolean isProjectDeleted = homePage
                .clickJobByName(RANDOM_PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickSidebarDelete()
                .clickYes(homePage)
                .isItemDeleted(RANDOM_PROJECT_NAME);

        Allure.step("Expected result :the list of items on the Dashboard doesn't contain deleted project.");
        Assert.assertTrue(isProjectDeleted);
    }

    @Test
    @Story("US_03.006  Edit configuration")
    @Description("Add discard old builds configurations to project")
    public void testAddDiscardOldBuildsConfigurationsToProject() {
        final String DAYS_TO_KEEP = generateRandomNumber();
        final String NUM_TO_KEEP = generateRandomNumber();
        final String ARTIFACT_DAYS_TO_KEEP= generateRandomNumber();
        final String ARTIFACT_NUM_TO_KEEP = generateRandomNumber();

        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        List<String> discardOldBuildsList = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickConfigureButton()
                .clickDiscardOldBuilds()
                .enterNumberOfDaysToKeepBuilds(DAYS_TO_KEEP)
                .enterMaxNumberOfBuildsToKeep(NUM_TO_KEEP)
                .clickAdvancedButton()
                .enterNumberOfDaysToKeepArtifacts(ARTIFACT_DAYS_TO_KEEP)
                .enterMaxNumberOfBuildsToKeepWithArtifacts(ARTIFACT_NUM_TO_KEEP)
                .clickSaveButton()
                .clickConfigureButton()
                .clickAdvancedButton()
                .getDiscardOldBuildsListText();

        Allure.step("Expected result : Values of discard old builds parameters are the same as entered");
        Assert.assertEquals(
                discardOldBuildsList,
                List.of(DAYS_TO_KEEP, NUM_TO_KEEP, ARTIFACT_DAYS_TO_KEEP, ARTIFACT_NUM_TO_KEEP));
    }

    @Test
    @Story("US_03.000 Create project")
    @Description("Verify existing project can be found using Search")
    public void testSearchForCreatedProject() {

        SearchResultPage searchResultPage = TestUtils
                .createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT)
                .getHeader()
                .typeTextToSearchField(PROJECT_NAME)
                .getHeader()
                .pressEnterOnSearchField();

        String currentUrl = searchResultPage.getCurrentUrl();
        String searchResult = searchResultPage.getTextFromMainPanel();

        Allure.step("Expected results :1 - the main panel of the page , we have been redirected to, contains '" + PROJECT_NAME + "'," +
                                            "2 - it's URL contains '" + PROJECT_NAME +"'.");
        Assert.assertTrue(currentUrl.contains(PROJECT_NAME) && searchResult.contains(PROJECT_NAME));
    }

    @Test
    @Story("US_03.002  Enable/Disable project")
    @Description("Check, that there is a special icon near displayed project on Dashboard")
    public void testVerifyThatDisabledIconIsDisplayedOnDashboard() {

        List<String> disabledProjectList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .clickBreadcrumbsProjectName()
                .clickDisableProject()
                .clickLogo()
                .getDisabledProjectListText();

        Allure.step("Expected result : the disabled project is marked by specific icon on dashboard");
        Assert.assertTrue(disabledProjectList.contains(PROJECT_NAME));
    }

    @Test
    @Story("US_03.005  Move project to Folder")
    @Description("Move project to Folder using left-sidebar  menu on Dashboard")
    public void testMoveProjectToFolderFromDashboardPage() {

        TestUtils.createFolderProject(this, FOLDER_NAME);
        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        HomePage homePage = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickMoveOptionInMenu()
                .selectFolder(FOLDER_NAME)
                .clickMove()
                .clickLogo();

        boolean isProjectDeleted = homePage
                .isItemDeleted(PROJECT_NAME);

        boolean isProjectMoved = homePage
                .clickSpecificFolderName(FOLDER_NAME)
                .getItemListInsideFolder()
                .contains(PROJECT_NAME);

        Allure.step("Expected result : the project is moved from dashboard to the folder '" + FOLDER_NAME +"' ");
        Assert.assertTrue(isProjectMoved && isProjectDeleted);
    }

    @Test
    @Story("US_03.002  Enable/Disable project")
    @Description("Check, that disable Message appears, when a project is disabled")
    public void testDisableProjectOnProjectPage() {

        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        String disableMessage = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickDisableProject()
                .getDisableMessage();

        Allure.step("Expected result : 'This project is currently disabled' message has appeared");
        Assert.assertTrue(disableMessage.contains("This project is currently disabled"), "Substring not found");
    }

    @Test(dependsOnMethods = "testDisableProjectOnProjectPage")
    @Story("US_03.002  Enable/Disable project")
    @Description("Check,that 'Enable' status is indicated on Project Configure Page, when a project is enable")
    public void testEnableProjectOnProjectPage() {

        String enableMessage = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickEnableButton()
                .clickConfigureButton()
                .getToggleStatusMessage();

        Allure.step("Expected result : 'Enabled' message has appeared");
        Assert.assertTrue(enableMessage.matches("Enabled"),
                "Substring not found");
    }
}
