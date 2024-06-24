package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.ErrorPage;
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
        String textAboveNameField = new CreateNewItemPage(getDriver())
                .getTitleOfNameField();

        Assert.assertEquals(newItemHeader, "New Item [Jenkins]");
        Assert.assertEquals(textAboveNameField, "Enter an item name");
    }


    @Test
    @Story("US_00.000 Create New item")
    @Description("You can't create an item without selection of item type, because 'OkButton' is enabled ")
    public void testCreateItemWithoutSelectedItemType() {
        boolean isOkButtonEnabled = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName("Test Project")
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
    public void testCheckListOfSuggestedForCreationProjectTypes() {
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

        Allure.step("Expected result: All expected project types are present'");
        Assert.assertTrue(actualTypesList.containsAll(typesList));
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
                .typeItemName(x)
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Assert.assertEquals(errorMessage, "» ‘" + x + "’ is an unsafe character");
    }


    @Test
    @Story("US_00.000 Create New item")
    @Description(" Create item with empty name")

    public void testCreateItemWithEmptyName() {
        final String hintTextWhenEmptyName = "» This field cannot be empty, please enter a valid name";
        final String hintColor = "rgba(255, 0, 0, 1)";

        Boolean isOkButtonEnabled = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName("q")
                .clearItemNameField()
                .isOkButtonEnabled();

        String validationMessage = new CreateNewItemPage(getDriver())
                .getItemNameHintText();

        String validationMessageColor = new CreateNewItemPage(getDriver())
                .getItemNameHintColor();

        Assert.assertFalse(isOkButtonEnabled);
        Assert.assertEquals(validationMessage, hintTextWhenEmptyName);
        Assert.assertEquals(validationMessageColor, hintColor);
    }

    @Test
    @Story("US_00.000 Create New item")
    @Description("It is impossible to create a new item after clearing Item Name input Field  ")
    public void testCreateItemEmptyNameNegative() {
        CreateNewItemPage createNewItemPage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName("Name")
                .clearItemNameField()
                .selectFolder();

        Assert.assertFalse(createNewItemPage.isOkButtonEnabled());
    }


    @Story("US_00.007 Create a new item from other existing")
    @Description("Try to copy an unexisting project")
    @Test(dependsOnMethods = "testDropdownNamesMenuContentWhenCopyProject")
    public void testCopyFromNotExistingJob() {
        final String notExistingName = "AAA";

        ErrorPage errorPage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName("someName")
                .typeItemNameInCopyFrom(notExistingName)
                .clickOkButtonWhenError();

        Assert.assertTrue(errorPage.getCurrentUrl().endsWith("/createItem"));
        Assert.assertEquals(errorPage.getHeadingText(), "Error");
        Assert.assertEquals(errorPage.getErrorText(), "No such job: " + notExistingName);
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
                .typeItemName(jobName)
                .clickProjectType(type)
                .clickOkButtonWhenError()
                .clickLogo()
                .clickNewItem()
                .typeItemName(jobName + "Copy")
                .typeItemNameInCopyFrom(jobName)
                .clickOkButtonWhenError()
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

        final String freestyle1 = "folff";
        final String freestyle2 = "folff00";
        final String folder1 = "Folder1";
        final String folder2 = "bFolder2";
        final String pipeline1 = "pipe1";
        final String multiConfigurationProject1 = "multi1";
        final String multiBranchPipe1 = "multiBranch1";
        final String organizationFolder1 = "organizationFolder1";

        final String firstLetters = "fol";
        final String newItemName = "someName";

        TestUtils.createFreestyleProject(this, freestyle1);
        TestUtils.createFolderProject(this, folder1);
        TestUtils.createFolderProject(this, folder2);
        TestUtils.createFreestyleProject(this, freestyle2);
        TestUtils.createPipelineProject(this, pipeline1);
        TestUtils.createMultiConfigurationProject(this, multiConfigurationProject1);
        TestUtils.createMultibranchProject(this, multiBranchPipe1);
        TestUtils.createOrganizationFolderProject(this, organizationFolder1);

        TestUtils.setInsensitiveSearchUserSetting(this, true);

        List<String> firstLettersJobs = TestUtils.getJobsBeginningFromThisFirstLetters(this, firstLetters);

        List<String> jobsFromDropdownMenu = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(newItemName)
                .typeItemNameInCopyFrom(firstLetters)
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
        final String projectName = "NewProject";
        Random random = new Random();
        int itemOptionIndex = random.nextInt(3) + 1;

        Boolean isTypeChecked = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(projectName)
                .clickItemOption(projectType, itemOptionIndex)
                .isAttributeAriaChecked(projectType, itemOptionIndex);
        String currentUrl = new CreateNewItemPage(getDriver())
                .clickOkButtonWhenError()
                .getCurrentUrl();
        String pageHeading = new FreestyleConfigPage(getDriver())
                .clickSaveButton()
                .getProjectName();

        Assert.assertTrue(isTypeChecked);
        Assert.assertTrue(currentUrl.contains(projectName));
        Assert.assertTrue(pageHeading.contains(projectName));
    }
}
