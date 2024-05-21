package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.CreateItemPage;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;
import java.util.Random;

public class NewItemTest extends BaseTest {

    @Test
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
    public void testCreateNewFolder() {
        List<String> itemsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Name")
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemsList, "Name", "Item not found");
    }

    @Test
    public void testCreateItemWithoutSelectedItemType() {
        boolean okButtonIsEnabled = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Test Project")
                .isOkButtonEnabled();

        Assert.assertFalse(okButtonIsEnabled);
    }

    @Test
    public void testRenameFolder() {
        List<String> itemsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Name")
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .openItemDropdownWithSelenium("Name")
                .renameFolderFromDropdown()
                .setNewName("New Name")
                .clickRename()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemsList, "New Name", "Item not found");
    }

    @Test
    public void testMessageWhenCreateItemUsingSpecialCharactersInName() {
        String[] specialCharacters = {"!", "%", "&", "#", "@", "*", "$", "?", "^", "|", "/", "]", "["};

        new HomePage(getDriver())
                .clickNewItem();

        for (String specChar : specialCharacters) {
            String actualErrorMessage = new CreateNewItemPage(getDriver())
                    .clearItemNameField()
                    .setItemName("Fold" + specChar + "erdate")
                    .getErrorMessageInvalidCharacterOrDuplicateName();

            String expectMessage = "» ‘" + specChar + "’ is an unsafe character";

            Assert.assertEquals(actualErrorMessage, expectMessage, "Message is not displayed");
        }
    }

    @Test
    public void testCreateItemWithEmptyName() {
        String hintTextWhenEmptyName = "» This field cannot be empty, please enter a valid name";
        String hintColor = "rgba(255, 0, 0, 1)";

        Boolean IsOkButtonEnabled = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("q")
                .clearItemNameField()
                .isOkButtonEnabled();

        String validationMessage = new CreateNewItemPage(getDriver())
                .getItemNameHintText();

        String validationMessageColor = new CreateNewItemPage(getDriver())
                .getItemNameHintColor();

        Assert.assertFalse(IsOkButtonEnabled);
        Assert.assertEquals(validationMessage, hintTextWhenEmptyName);
        Assert.assertEquals(validationMessageColor, hintColor);
    }

    @Test
    public void testCreateItemEmptyNameNegative() {
        CreateNewItemPage createNewItemPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Name")
                .clearItemNameField()
                .selectFolder();

        Assert.assertFalse(createNewItemPage.isOkButtonEnabled());
    }

    @Test
    public void testCreateMulticonfigurationProject() {
        List<String> itemsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Name")
                .selectMultiConfigurationAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemsList, "Name", "Item not found");
    }

    @Test
    public void testCreateMulticonfigurationProjectNegative() {
        CreateNewItemPage createNewItemPage = new HomePage(getDriver())
                .clickNewItem()
                .selectMultiConfiguration();

        Assert.assertEquals(createNewItemPage.getItemNameHintText(), "» This field cannot be empty, please enter a valid name");
    }

    @Test(dependsOnMethods = "testDropdownNamesMenuContentWhenCopyProject")
    public void testCopyFromNotExistingJob() {
        final String notExistingName = "AAA";

        CreateItemPage errorPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("someName")
                .setItemNameInCopyForm(notExistingName)
                .clickOkButton();

        Assert.assertTrue(errorPage.getCurrentUrl().endsWith("/createItem"));
        Assert.assertEquals(errorPage.getPageHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessageText(), "No such job: " + notExistingName);
    }

    @DataProvider(name="existingJobsNames")
    public Object[][] existingJobsNames(){
        return new Object[][]{
                {"Freestyle","folff"},
                {"Freestyle","folff00"},
                {"Folder","Folder1"},
                {"Folder","bFolder2"},
                {"Pipeline","pipe1"},
                {"MultiConfigurationProject", "multi1"},
                {"MultiBranchPipe", "multiBranch1"},
                {"organizationFolder","organizationFolder1"}
       };
    }
    @Test(dependsOnMethods = "testDropdownNamesMenuContentWhenCopyProject" ,dataProvider = "existingJobsNames")
    public void testCopyFromExistingJob(String type, String jobName) {

        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(jobName + "Copy")
                .setItemNameInCopyForm(jobName)
                .clickOkButton()
                .clickLogo();
        Assert.assertTrue(homePage.isItemExists(jobName + "Copy"));
        }

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

        final String firstLetters = "foL";
        final String newItemName = "someName";

        List<String> firstLettersJobs = new HomePage(getDriver())
                .createFreestyleProject(freestyle1)
                .createFolder(folder1)
                .createFolder(folder2)
                .createFreestyleProject(freestyle2)
                .createPipeline(pipeline1)
                .createMultiConfigurationProject(multiConfigurationProject1)
                .createMultibranchPipeline(multiBranchPipe1)
                .createOrganizationFolder(organizationFolder1)
                .getJobsBeginningFromThisFirstLetters(firstLetters);

        List<String> jobsFromDropdownMenu = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(newItemName)
                .setItemNameInCopyForm(firstLetters)
                .getDropdownMenuContent();

        Assert.assertEquals(jobsFromDropdownMenu, firstLettersJobs);
    }

    @DataProvider(name = "unsafeCharactersProvider")
    public Object[][] unsafeCharactersProvider() {
        return new Object[][]{
                {"!"}, {"#"}, {"$"}, {"%"}, {"&"}, {"*"}, {"/"}, {";"},
                {">"}, {"<"}, {"?"}, {"@"}, {"["}, {"\\"}, {"]"}, {"^"}, {"|"}
        };
    }

    @Test(dataProvider = "unsafeCharactersProvider")
    public void testInvalidValuesForProjectNameInput(String x) {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(x)
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Assert.assertEquals(errorMessage, "» ‘" + x + "’ is an unsafe character");
    }

    @Test
    public void testUserSeeTheNameEntryField() {

        Assert.assertTrue(new HomePage(getDriver())
                .clickNewItem()
                .isDisplayedNameField());
    }

    @Test
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

    @DataProvider
    Object[][] projectTypes() {
        return new Object[][]{
                {"standalone-projects"},
                {"nested-projects"}};
    }

    @Test(dataProvider = "projectTypes")
    public void testCreateItemForStandaloneOrNestedProjects(String projectType) {
        String PROJECT_NAME = "NewProject";
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
                .clickSaveButton().getPageHeadingText();

        Assert.assertTrue(isTypeChecked);
        Assert.assertEquals(currentUrl, String.format("http://localhost:8080/job/%s/configure", PROJECT_NAME));
        Assert.assertTrue(pageHeading.contains(PROJECT_NAME));
    }
}