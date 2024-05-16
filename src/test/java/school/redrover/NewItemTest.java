package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.CreateItemPage;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

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
    public void testCreateItemWithoutSelectedItemType() {
        boolean okButtonIsEnabled = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Test Project")
                .isOkButtonEnabled();

        Assert.assertFalse(okButtonIsEnabled);
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

    @Test(dependsOnMethods = "testDropdownNamesMenuContentWhenCopyProject")
    public void testCopyFromNotExistingJob() {
        String notExistingName = "AAA";

        CreateItemPage errorPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("someName")
                .setItemNameInCopyForm(notExistingName)
                .clickOkButton();

        Assert.assertTrue(errorPage.getCurrentUrl().endsWith("/createItem"));
        Assert.assertEquals(errorPage.getPageHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessageText(), "No such job: " + notExistingName);
    }

    @Test
    public void testDropdownNamesMenuContentWhenCopyProject() {
        String freestyle1 = "folff";
        String freestyle2 = "folff00";
        String folder1 = "Folder1";
        String folder2 = "bFolder2";

        String firstLetters = "foL";
        String newItemName = "someName";

        List<String> firstLettersJobs = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(freestyle1)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickNewItem()
                .setItemName(folder1)
                .selectFolderAndClickOk()
                .clickLogo()
                .clickNewItem()
                .setItemName(folder2)
                .selectFolderAndClickOk()
                .clickLogo()
                .clickNewItem()
                .setItemName(freestyle2)
                .selectFreestyleAndClickOk()
                .clickLogo()
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
}