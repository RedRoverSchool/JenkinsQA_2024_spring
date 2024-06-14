package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.CreateItemPage;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.Random;

@Epic("New item")
public class NewItemTest extends BaseTest {

    @Test
    @Story("US_00.000 Create New item")
    @Description("Enter to New item page")
    public void testOpenCreateNewItemPage() {
        String newItemHeader = new HomePage(getDriver())
                .clickNewItem()
                .getPageTitle();
        String TextAboveNameField = new CreateNewItemPage(getDriver())
                .getTitleOfNameField();

        Assert.assertEquals(newItemHeader, "New Item [Jenkins]");
        Assert.assertEquals(TextAboveNameField, "Enter an item name");
    }


    @Test
    @Story("US_00.000 Create New item")
    @Description("You can't create an item without selection of item type, because 'OkButton' is enabled ")
    public void testCreateItemWithoutSelectedItemType() {
        boolean isOkButtonEnabled = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Test Project")
                .isOkButtonEnabled();

        Assert.assertFalse(isOkButtonEnabled);
    }

    @Test
    @Story("US_00.000 Create New item")
    @Description("A user can see the name entry field ")
    public void testUserSeeTheNameEntryField() {

        Boolean isNameEntryFieldDisplayed = new HomePage(getDriver())
                .clickNewItem()
                .isDisplayedNameField();

        Assert.assertTrue(isNameEntryFieldDisplayed);
    }

    @Test
    @Story("US_00.000 Create New item")
    @Description(" Check list of types, suggested for a project creation ")
    public void TestCheckListOfSuggestedForCreationProjectTypes() {
        List<String> typesList = List.of(
                "Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder");

        List<String> actualTypesList = new HomePage(getDriver())
                .clickNewItem()
                .getTypesList();

        Assert.assertEquals(actualTypesList, typesList);
    }


    @DataProvider(name = "unsafeCharactersProvider")
    public Object[][] unsafeCharactersProvider() {
        return new Object[][]{
                {"!"}, {"#"}, {"$"}, {"%"}, {"&"}, {"*"}, {"/"}, {";"},
                {">"}, {"<"}, {"?"}, {"@"}, {"["}, {"\\"}, {"]"}, {"^"}, {"|"}
        };
    }

    @Test(dataProvider = "unsafeCharactersProvider")
    @Story("US_00.000 Create New item")
    @Description("Check hint, when entering an unsafe character ")
    public void testInvalidValuesForProjectNameInput(String x) {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(x)
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Assert.assertEquals(errorMessage, "» ‘" + x + "’ is an unsafe character");
    }


    @Test
    @Story("US_00.000 Create New item")
    @Description(" Create item with empty name")

    public void testCreateItemWithEmptyName() {
        final String HINT_TEXT_WHEN_EMPTY_NAME = "» This field cannot be empty, please enter a valid name";
        final String HINT_COLOR = "rgba(255, 0, 0, 1)";

        Boolean isOkButtonEnabled = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("q")
                .clearItemNameField()
                .isOkButtonEnabled();

        String validationMessage = new CreateNewItemPage(getDriver())
                .getItemNameHintText();

        String validationMessageColor = new CreateNewItemPage(getDriver())
                .getItemNameHintColor();

        Assert.assertFalse(isOkButtonEnabled);
        Assert.assertEquals(validationMessage, HINT_TEXT_WHEN_EMPTY_NAME);
        Assert.assertEquals(validationMessageColor, HINT_COLOR);
    }

    @Test
    @Story("US_00.000 Create New item")
    @Description("It is impossible to create a new item after clearing Item Name input Field  ")
    public void testCreateItemEmptyNameNegative() {
        CreateNewItemPage createNewItemPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Name")
                .clearItemNameField()
                .selectFolder();

        Assert.assertFalse(createNewItemPage.isOkButtonEnabled());
    }


    @Story("US_00.007 Create a new item from other existing")
    @Description("Try to copy an unexisting project")
    @Test(dependsOnMethods = "testDropdownNamesMenuContentWhenCopyProject")
    public void testCopyFromNotExistingJob() {
        final String NOT_EXISTING_NAME = "AAA";

        CreateItemPage errorPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("someName")
                .typeItemNameInCopyFrom(NOT_EXISTING_NAME)
                .clickOkButton();

        Assert.assertTrue(errorPage.getCurrentUrl().endsWith("/createItem"));
        Assert.assertEquals(errorPage.getPageHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessageText(), "No such job: " +NOT_EXISTING_NAME);
    }

    @DataProvider(name = "existingJobsNames")
    public Object[][] existingJobsNames() {
        return new Object[][]{
                {"Freestyle project", "folff"},
                {"Freestyle project", "folff00"},
                {"Folder", "Folder1"},
                {"Folder", "bFolder2"},
                {"Pipeline", "pipe1"},
                {"Multi-configuration project", "multi1"},
                {"Multibranch Pipeline", "multiBranch1"},
                {"Organization Folder", "organizationFolder1"}
        };
    }
    @Story("US_00.007 Create a new item from other existing")
    @Description(" Create a new item from other existing one for ALL types of jobs.")
    @Test(dataProvider = "existingJobsNames")
    public void testCopyFromExistingJob(String type, String jobName) {

        HomePage homePage;
        homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(jobName)
                .clickProjectType(type)
                .clickOkButton()
                .clickLogo()
                .clickNewItem()
                .setItemName(jobName + "Copy")
                .typeItemNameInCopyFrom(jobName)
                .clickOkButton()
                .clickLogo();
        Integer quantityItemsWithCopies = new HomePage(getDriver())
                .getItemList()
                .size();

        Assert.assertEquals(quantityItemsWithCopies, 2);
        Assert.assertTrue(homePage.isItemExists(jobName + "Copy"));
        Assert.assertTrue(homePage.isItemExists(jobName));
    }

    @Story("US_00.007 Create a new item from other existing")
    @Description("Check tooltip's content contains the all names of jobs, beginning from defined letter(letters)")
    @Test
    public void testDropdownNamesMenuContentWhenCopyProject() {

        final String FREESTYLE1 = "folff";
        final String FREESTYLE2 = "folff00";
        final String FOLDER1 = "Folder1";
        final String FOLDER2 = "bFolder2";
        final String PIPELINE1 = "pipe1";
        final String MULTI_CONFIGURATION_PROJECT1 = "multi1";
        final String MULTI_BRANCH_PIPE1 = "multiBranch1";
        final String ORGANIZATION_FOLDER1 = "organizationFolder1";

        final String FIRST_LETTERS = "fol";
        final String NEW_ITEM_NAME = "someName";

        TestUtils.createFreestyleProject(this, FREESTYLE1);
        TestUtils.createFolderProject(this,FOLDER1);
        TestUtils.createFolderProject(this, FOLDER2);
        TestUtils.createFreestyleProject(this, FREESTYLE2);
        TestUtils.createPipelineProject(this, PIPELINE1);
        TestUtils.createMultiConfigurationProject(this, MULTI_CONFIGURATION_PROJECT1);
        TestUtils.createMultibranchProject(this,MULTI_BRANCH_PIPE1);
        TestUtils.createOrganizationFolderProject(this,ORGANIZATION_FOLDER1);

        List<String> firstLettersJobs = TestUtils.getJobsBeginningFromThisFirstLetters(this, FIRST_LETTERS );

        List<String> jobsFromDropdownMenu = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NEW_ITEM_NAME)
                .typeItemNameInCopyFrom(FIRST_LETTERS)
                .getDropdownMenuContent();

        Assert.assertEquals(jobsFromDropdownMenu, firstLettersJobs);
    }

    @DataProvider
    Object[][] projectTypes() {
        return new Object[][]{
                {"standalone-projects"},
                {"nested-projects"}};
    }

    @Test(dataProvider = "projectTypes")
    @Story("US_00.000 Create New item")
    @Description("Verification for desirable job type  ")
    public void testCreateItemForStandAloneOrNestedProjects(String projectType) {
        final String PROJECT_NAME = "NewProject";
        Random random = new Random();
        int itemOptionIndex = random.nextInt(3) + 1;

        Boolean isTypeChecked = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .clickItemOption(projectType, itemOptionIndex)
                .isAttributeAriaChecked(projectType, itemOptionIndex);
        String currentUrl = new CreateNewItemPage(getDriver())
                .clickOkButton()
                .getCurrentUrl();
        String pageHeading = new FreestyleConfigPage(getDriver())
                .clickSaveButton()
                .getPageHeadingText();

        Assert.assertTrue(isTypeChecked);
        Assert.assertTrue(currentUrl.contains(PROJECT_NAME));
        Assert.assertTrue(pageHeading.contains(PROJECT_NAME));
    }
}