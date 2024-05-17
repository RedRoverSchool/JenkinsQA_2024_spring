package school.redrover;

import com.beust.ah.A;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.*;
import org.testng.annotations.*;
import school.redrover.model.ConfirmRenamePage;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.*;

import java.util.List;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_PROJECT_NAME = "Freestyle Project Name";
    private static final String NEW_FREESTYLE_PROJECT_NAME = "New Freestyle Project Name";
    private static final String FREESTYLE_PROJECT_DESCRIPTION = "Some description text";
    private static final String FOLDER_NAME = "Folder SV";

    private WebElement submitButton() {

        return getDriver().findElement(By.xpath("//button[@name = 'Submit']"));
    }

    @Test
    public void testCreateFreestyleProjectJob() {
        String expectedHeading = "My First Freestyle project";

        List<String> itemName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(expectedHeading)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemName.contains(expectedHeading));
    }

    @Test
    public void testRenameFreestyleProjectFromConfigurationPage() {

        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickRename()
                .setNewName(NEW_FREESTYLE_PROJECT_NAME)
                .clickRename();

        String projectName = freestyleProjectPage.getProjectName();
        List<String> itemList = freestyleProjectPage
                .clickLogo()
                .getItemList();

        Assert.assertEquals(projectName, NEW_FREESTYLE_PROJECT_NAME);
        Assert.assertTrue(itemList.contains(NEW_FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testCreateFreestyleProjectInvalidChar() {

        String[] invalidCharacters = {"!", "@", "#", "$", "%", "^", "&", "*", "?", "|", "/", "["};
        String expectedResult = "";
        String actualResult = "";

        CreateNewItemPage createNewItemPage = new CreateNewItemPage(getDriver());

        new HomePage(getDriver())
                .clickCreateJob()
                .clickItemNameField();

        for (String invalidChar : invalidCharacters) {

             expectedResult = "» ‘" + invalidChar + "’ is an unsafe character";

             actualResult = createNewItemPage
                    .clearItemNameField()
                    .setItemName(invalidChar)
                    .getErrorMessageInvalidCharacterOrDuplicateName();

            Assert.assertEquals(actualResult, expectedResult);

            Assert.assertTrue(createNewItemPage.isOkButtonNotActive());
        }
    }

    @Test
    public void testRenameProject() {

        List<String> actualResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickRename()
                .setNewName(NEW_FREESTYLE_PROJECT_NAME)
                .clickRename()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(actualResult.contains(NEW_FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testFreestyleProjectCreate() {

        String actualResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualResult, FREESTYLE_PROJECT_NAME);
    }

    @Test
    public void testRenameWithEmptyName() {

        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickRenameInDropdown()
                .clearNameAndClickRenameButton();

        Assert.assertTrue(new ConfirmRenamePage(getDriver()).isErrorMessageDisplayed());
    }

    @Test
    public void testMoveToFolderInDropdown() {

        final String folderName = "Classic Models";
        final String projectName = "Race Cars";

        final String expectedResult = "Full project name: " + folderName + "/" + projectName;

        String actualResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(folderName)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName(projectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .openItemDropdown(projectName)
                .clickMoveInDropdown()
                .chooseFolderAndSave(folderName)
                .checkFullProjectName();

        Assert.assertTrue(actualResult.contains(expectedResult));
    }

    @Ignore
    @Test
    public void testBuildNowFreestyleProject() {

        String actualResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowOnSideBar()
                .waitBuildToFinish()
                .waitBuildToFinish()
                .getBuildInfo();

        Assert.assertEquals(actualResult, "#1");
    }

    @Test
    public void testDeleteFreestyleProjectFromConfigurationPage() {

        String deleteResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .chooseCreatedFreestyleProject(FREESTYLE_PROJECT_NAME)
                .clickDelete()
                .clickYesInConfirmDeleteDialog()
                .getWelcomeJenkinsHeader();

        Assert.assertEquals(deleteResult, "Welcome to Jenkins!");

    }

    @Ignore
    @Test
    public void testCopyFromContainer() {

        String oldProjectName1 = "Race Cars";
        String oldProjectName2 = "Race Bikes";
        String newProjectName = "Vintage Cars";

        List<String> elementsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(oldProjectName1)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName(oldProjectName2)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName(newProjectName)
                .setItemNameInCopyForm(oldProjectName1.substring(0, 1))
                .getCopyFormElementsList();

        Assert.assertTrue(elementsList.contains(oldProjectName1));
    }
    @Test
    public void testAddDescriptionOfConfiguration() {

        final String projectDescription = "This test is trying to create a new freestyle job";

        String projectDescriptionText = new HomePage(getDriver())
                .clickNewItem().setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(projectDescription)
                .clickSaveButton()
                .clickConfigure()
                .clearDescription()
                .setDescription("Description of " + FREESTYLE_PROJECT_NAME)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(projectDescriptionText, "Description of " + FREESTYLE_PROJECT_NAME);
    }
    @Test
    public void testAddDescription() {
        String projectDescriptionText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickAddDescription()
                .setDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(projectDescriptionText, FREESTYLE_PROJECT_DESCRIPTION);
    }

    @Test
    public void testCreateNewItemFromOtherExisting() {

        final String projectName1 = "Race Cars";
        final String projectName2 = "Vintage Cars";

        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName1)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName(projectName2)
                .setItemNameInCopyForm(projectName1)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(projectList.contains(projectName2));
    }

    @Test
    public void testDisableProject() {

        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo();

        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).click();
        submitButton().click();
        String disabledStatus = getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form[@id='enable-project']"))).getText();

        Assert.assertEquals(disabledStatus, "This project is currently disabled\nEnable");
    }

    @Test
    public void testEnableFreestyleProject() {

        String disableButtonText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickDisableProjectButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickEnableButton()
                .getDisableProjectButtonText();

        Assert.assertEquals(disableButtonText, "Disable Project");
    }

    @Test
    public void testCreateFreestyleProjectWithDescription() {

        FreestyleProjectPage page = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSaveButton();

        Assert.assertEquals(page.getProjectName(), FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(page.getProjectDescriptionText(), FREESTYLE_PROJECT_DESCRIPTION);

        List<String> itemList = page
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testEditDescriptionFreestyleProject() {

        String addedToDescription = "Create one more build apps";

        String editDescription = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSaveButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickAddDescription()
                .clearDescription()
                .setDescription(addedToDescription)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(editDescription, addedToDescription);
    }

    @Test
    public void testMoveFreestyleProjectToFolder() {

        String expectedText = String.format("Full project name: %s/%s", FOLDER_NAME, FREESTYLE_PROJECT_NAME);

        String actualText = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .chooseCreatedFreestyleProject(FREESTYLE_PROJECT_NAME)
                .clickMove()
                .choosePath(FOLDER_NAME)
                .clickMoveButton()
                .getFullProjectPath();

        Assert.assertTrue(actualText.contains(expectedText), "The text does not contain the expected project name.");
    }

    @Test
    public void testCreateProject() {

        String expectedName = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(FREESTYLE_PROJECT_NAME, expectedName);
    }
}