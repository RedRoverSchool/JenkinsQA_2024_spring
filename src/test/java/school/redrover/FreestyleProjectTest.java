package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.*;
import org.testng.annotations.*;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.*;

import java.util.List;

public class FreestyleProjectTest extends BaseTest {
    private static final String FREESTYLE_PROJECT_NAME = "Freestyle Project Name";
    private static final String NEW_FREESTYLE_PROJECT_NAME = "New Freestyle Project Name";
    private static final String FREESTYLE_PROJECT_DESCRIPTION = "Some description text";
    private static final String FOLDER_NAME = "Folder SV";

    private WebElement okButton() {
        return getDriver().findElement(By.id("ok-button"));
    }

    private WebElement submitButton() {
        return getDriver().findElement(By.xpath("//button[@name = 'Submit']"));
    }

    private WebElement jenkinsHomeLink() {
        return getDriver().findElement(By.id("jenkins-home-link"));
    }

    public void createFreestyleProject(String newName) {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.id("name")))).sendKeys(newName);
        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
                By.className("hudson_model_FreeStyleProject")))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.id("ok-button")))).click();
        submitButton().click();
    }

    public FreestyleProjectPage createFreestyleProjectWithDescription() {

        return new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSaveButton();
    }

    public FolderProjectPage createFolder() {

        return new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton();
    }

    public void clickDisableEnableButton() {
        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).click();
        submitButton().click();
    }

    @Test
    public void testCreateFreestyleProjectJob() {
        String expectedHeading = "My First Freestyle project";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(expectedHeading);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        String actualHeading = getDriver()
                .findElement(By.xpath("//table//a[@href='job/My%20First%20Freestyle%20project/']")).getText();

        Assert.assertEquals(actualHeading, expectedHeading);
    }

    @Test
    public void testRenameFreestyleProjectFromConfigurationPage() {
        createFreestyleProject(FREESTYLE_PROJECT_NAME);
        jenkinsHomeLink().click();

        getDriver().findElement(By.xpath("//a[@class= 'jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[7]/span")).click();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys(NEW_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        String resultHeader = getDriver().findElement(By.xpath("//h1"))
                .getText();

        jenkinsHomeLink().click();

        String resultName = getDriver().findElement(By.xpath("//a[@class= 'jenkins-table__link model-link inside']"))
                .getText();

        Assert.assertEquals(resultHeader, NEW_FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(resultName, NEW_FREESTYLE_PROJECT_NAME);
    }

    @Test
    public void testCreateFreestyleProjectInvalidChar() {

        String[] invalidCharacters = {"!", "@", "#", "$", "%", "^", "&", "*", "?", "|", "/", "["};

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();

        for (String invalidChar : invalidCharacters) {
            getDriver().findElement(By.xpath("//*[@class='jenkins-input']")).clear();
            getDriver().findElement(By.xpath("//*[@class='jenkins-input']")).sendKeys(invalidChar);

            String actualResult = getDriver().findElement(By.xpath("//div[@id='itemname-invalid']"))
                    .getText();
            String expectedResult = "» ‘" + invalidChar + "’ is an unsafe character";
            Assert.assertEquals(actualResult, expectedResult);

            boolean okButton = getDriver().findElement(By.xpath("//button[@type='submit']")).isEnabled();
            Assert.assertFalse(okButton);
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
        createFreestyleProject(FREESTYLE_PROJECT_NAME);

        WebElement nameOfProject = getDriver().findElement(
                By.xpath("//h1[@class='job-index-headline page-headline']"));

        String actualResult = nameOfProject.getText();

        Assert.assertEquals(actualResult, FREESTYLE_PROJECT_NAME);
    }

    @Ignore
    @Test
    public void testAddDescription() {
        final String projectName = "New Freestyle project";
        final String description = "Text description of the project";

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement((By.id("description-link"))).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//div[text()='" + description + "']")).isDisplayed(),
                description);
    }

    @Test
    public void testRenameWithEmptyName() {
        createFreestyleProject(FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.id("jenkins-home-link")).click();

        WebElement projectName = getDriver().findElement(
                By.xpath("//span[text()='" + FREESTYLE_PROJECT_NAME + "']/following-sibling::button[@class='jenkins-menu-dropdown-chevron']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", projectName);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", projectName);

        getDriver().findElement(By.xpath("//a[contains(@href,'rename')]")).click();

        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();

        getDriver().findElement(By.xpath("//button[contains(text(),'Rename')]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//p[text()='No name is specified']")).getText(), "No name is specified");
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
        createFreestyleProject(FREESTYLE_PROJECT_NAME);
        jenkinsHomeLink().click();

        getDriver().findElement(By.xpath("//a[@class= 'jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[6]/span")).click();
        getDriver().findElement(By.xpath("//button[@data-id = 'ok']")).click();
        String resultHeader = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(resultHeader, "Welcome to Jenkins!");

    }

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
        createFreestyleProject(FREESTYLE_PROJECT_NAME);
        jenkinsHomeLink().click();

        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).click();
        submitButton().click();
        String disabledStatus = getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form[@id='enable-project']"))).getText();

        Assert.assertEquals(disabledStatus, "This project is currently disabled\nEnable");
    }

    @Test
    public void testEnableFreestyleProject() {
        createFreestyleProject(FREESTYLE_PROJECT_NAME);
        jenkinsHomeLink().click();

        clickDisableEnableButton();
        jenkinsHomeLink().click();
        clickDisableEnableButton();

        Assert.assertEquals(submitButton().getText(), "Disable Project");
    }

    @Test
    public void testCreateFreestyleProjectWithDescription() {
        FreestyleProjectPage freestyleProjectPage = createFreestyleProjectWithDescription();
        String freestyleTitleName = freestyleProjectPage.getProjectName();
        String freestyleDescriptionText = freestyleProjectPage.getProjectDescriptionText();

        Assert.assertEquals(freestyleTitleName, FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(freestyleDescriptionText, FREESTYLE_PROJECT_DESCRIPTION);

        List<String> itemList = freestyleProjectPage
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testEditDescriptionFreestyleProject() {
        String addedToDescription = "Create one more build apps";

        FreestyleProjectPage freestyleTest = createFreestyleProjectWithDescription();

        String editDescription = freestyleTest
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickChangeDescription()
                .clearOnDescriptionInput()
                .setDescription(addedToDescription)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(editDescription, addedToDescription);
    }

    @Test
    public void testMoveFreestyleProjectToFolder() {
        String expectedText = String.format("Full project name: %s/%s", FOLDER_NAME, FREESTYLE_PROJECT_NAME);

        createFreestyleProjectWithDescription()
                .clickLogo();

        createFolder()
                .clickLogo();

        String actualText = new HomePage(getDriver())
                .chooseCreatedFreestyleProject(FREESTYLE_PROJECT_NAME)
                .clickMove()
                .choosePath(FOLDER_NAME)
                .clickMoveButton()
                .getFullProjectPath();

        Assert.assertTrue(actualText.contains(expectedText), "The text does not contain the expected project name.");

    }
}