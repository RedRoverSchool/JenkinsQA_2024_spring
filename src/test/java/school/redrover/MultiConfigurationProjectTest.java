package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.ItemErrorPage;


import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class MultiConfigurationProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MCProject";
    private final String RANDOM_PROJECT_NAME = TestUtils.randomString();

    @Ignore
    @Test
    public void testRenameProjectViaMainPageDropdown() {
        String addToProjectName = "New";

        String newProjectName = new HomePage(getDriver())
                .clickNewItem()
                .createNewItem(PROJECT_NAME, "MultiConfiguration")
                .openItemDropdownWithSelenium(PROJECT_NAME)
                .selectRenameFromDropdown()
                .changeProjectName(addToProjectName)
                .clickRenameButton()
                .getProjectNameText();

        Assert.assertEquals(newProjectName,
                "Project " + PROJECT_NAME + "New",
                "Project name has not been changed");
    }

    @Test(dependsOnMethods = "testCreateMCP")
    public void testAddDescription() {
        final String text = "❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️";

        String description = new HomePage(getDriver())
                .clickMCPName(RANDOM_PROJECT_NAME)
                .clickAddDescriptionButton()
                .addOrEditDescription(text)
                .clickSaveDescription()
                .getDescriptionText();

        Assert.assertEquals(description, text);
    }

    @Test
    public void testEditDescriptionWithoutDelete() {
        final String text = "qwerty123";
        final String additionText = "AAA";

        TestUtils.createNewItemAndReturnToDashboard(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(String.format("[href = 'job/%s/']", PROJECT_NAME)))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();

        TestUtils.returnToDashBoard(this);

        getDriver().findElement(By.cssSelector("[href = 'job/" + PROJECT_NAME + "/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(additionText);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#description div:not([class])")))
                        .getText().equals(additionText + text));
    }

    @Test
    public void testDescriptionPreview() {
        TestUtils.createNewItemAndReturnToDashboard(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        final String text = "I want to see preview";
        getDriver().findElement(By.id("job_" + PROJECT_NAME)).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(text);
        getDriver().findElement(By.className("textarea-show-preview")).click();

        Assert.assertEquals(text, getDriver().findElement(By.className("textarea-preview")).getText());
    }

    @Test
    public void testReplacingProjectDescription() {
        final String oldText = "The text to be replaced";
        final String newText = "Replacement text";

        TestUtils.createNewItemAndReturnToDashboard(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(String.format("[href = 'job/%s/']", PROJECT_NAME)))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).sendKeys(oldText);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("description")).sendKeys(newText);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description div:not([class])")).getText(), newText);
    }

    @Test
    public void testMakeCopyMultiConfigurationProject() {
        final String newProjectName = "MCProject copy";
        TestUtils.createNewItemAndReturnToDashboard(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        getDriver().findElement(By.cssSelector("[href $= 'newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(newProjectName);

        WebElement copyFrom = getDriver().findElement(By.id("from"));
        ((JavascriptExecutor) getDriver()).executeScript(
                "return arguments[0].scrollIntoView(true);",
                copyFrom);
        copyFrom.sendKeys(PROJECT_NAME);

        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        Assert.assertEquals(
                getDriver().findElements(By.className("jenkins-table__link")).size(),
                2,
                "Copy of the project does not created");
    }

    @Test
    public void testDeleteProjectDescription() {
        final String description = "This is project description";
        TestUtils.createNewItemAndReturnToDashboard(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(String.format("[href = 'job/%s/']", PROJECT_NAME)))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        TestUtils.returnToDashBoard(this);

        getDriver().findElement(By.cssSelector("[href = 'job/" + PROJECT_NAME + "/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#description>div"))));
    }

    private static final String NAME_OF_PROJECT = "The name of Multi-configuration project";

    @Test
    public void testCreateMultiConfigurationProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id = 'name']")).sendKeys(NAME_OF_PROJECT);
        getDriver().findElement(By.xpath("//*[@class='hudson_matrix_MatrixProject']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@type='submit']"))).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Project " + NAME_OF_PROJECT);
    }

    @Test
    public void testAddDescriptionOnConfigurationPage() {
        final String description = "This is project description";
        TestUtils.createNewItemAndReturnToDashboard(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        getDriver().findElement(By.linkText(PROJECT_NAME)).click();
        getDriver().findElement(By.linkText("Configure")).click();
        getDriver().findElement(By.name("description")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#description>div:first-child")).getText(),
                description,
                "Project description is not displayed");
    }

    @Test
    public void testMCPDisableByToggle() {

        Assert.assertFalse(new HomePage(getDriver())
                .clickNewItem()
                .createNewItem(PROJECT_NAME, "MultiConfiguration")
                .clickMCPName(PROJECT_NAME)
                .clickConfigureButton()
                .clickToggleSwitch()
                .clickApply()
                .getStatusToggle());
    }

    @Test(dependsOnMethods = "testMCPDisableByToggle")
    public void testCheckTooltipEnablingMCP() {
        getDriver().findElement(By.linkText(PROJECT_NAME)).click();
        getDriver().findElement(By.linkText("Configure")).click();

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.className("jenkins-toggle-switch__label")))
                .perform();

        Assert.assertEquals(
                getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.tippy-box>div"))).getText(),
                "Enable or disable the current project");
    }

    @Test
    public void testYesButtonColorDeletingMCPInSidebar() {
        TestUtils.createNewItemAndReturnToDashboard(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);
        getDriver().findElement(By.linkText(PROJECT_NAME)).click();
        getDriver().findElement(By.cssSelector("[data-message^='Delete']")).click();

        String script = "return window.getComputedStyle(arguments[0]).getPropertyValue('--color')";
        String actualColor = (String) (((JavascriptExecutor) getDriver()).executeScript(
                script,
                getDriver().findElement(By.cssSelector("[data-id='ok']"))));
        String expectedColorNone = "#e6001f";
        String expectedColorDark = "hsl(5, 100%, 60%)";

        if (getDriver().findElement(By.tagName("html")).getAttribute("data-theme").equals("none")) {
            Assert.assertEquals(expectedColorNone, actualColor);
        } else if (getDriver().findElement(By.tagName("html")).getAttribute("data-theme").equals("dark")) {
            Assert.assertEquals(expectedColorDark, actualColor);
        }
    }

    @Test
    public void testCreateProjectWithoutName() {
        final String errorMessage = "This field cannot be empty";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();

        String actualErrorMessage = getDriver().findElement(By.id("itemname-required")).getText();
        WebElement okButton = getDriver().findElement(By.id("ok-button"));

        Assert.assertTrue(actualErrorMessage.contains(errorMessage));
        Assert.assertFalse(okButton.isEnabled());
    }


    @Test
    public void testTryCreateProjectExistName() {
        final String projectName = "MultiBuild";
        final String errorMessage = "A job already exists with the name " + "‘" + projectName + "’";

        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(projectName);
        getDriver().findElement(By.id("ok-button")).click();

        String actualMessage = getDriver().findElement(By.xpath("//*[@id='main-panel']/p")).getText();
        Assert.assertEquals(actualMessage, errorMessage);
    }

    @Test
    public void testCreateMCProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("MCProject");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[3]/label")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

        Assert.assertEquals(getDriver().findElement(By
                .xpath("//*[@id='job_MCProject']/td[3]/a/span")).getText(), "MCProject");

    }

    @Test(dependsOnMethods = "testCreateMCProject")
    public void testRenameMCProject() {
        getDriver().findElement(By.xpath("//*[@id='job_MCProject']/td[3]/a/span")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[7]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input")).clear();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input")).sendKeys("MCProjectNew");
        getDriver().findElement(By.name("Submit")).click();


        Assert.assertEquals(getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='breadcrumbs']/li[3]/a"))).getText(), "MCProjectNew");

    }

    @Test
    public void testCreateMCP() {
        List<String> itemNames = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(RANDOM_PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .clickSave()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemNames.contains(RANDOM_PROJECT_NAME));
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateMCP")
    public void testCreateMCPWithSameName() {
        ItemErrorPage errorPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(RANDOM_PROJECT_NAME)
                .selectMultiConfiguration()
                .clickOkAnyway(new ItemErrorPage(getDriver()));


        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(
                errorPage.getMessageText(),
                "A job already exists with the name ‘" + RANDOM_PROJECT_NAME + "’");
    }

    @Test
    public void testDeleteProjectViaDropdown() {
        TestUtils.createNewItemAndReturnToDashboard(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);
        getDriver().findElement(By.linkText(PROJECT_NAME)).click();

        TestUtils.openElementDropdown(this, getDriver().findElement(By.linkText(PROJECT_NAME)));

        getDriver().findElement(By.cssSelector(".tippy-box [href$='Delete']")).click();
        getDriver().findElement(By.cssSelector("[data-id='ok']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.tagName("h1")).getText(),
                "Welcome to Jenkins!",
                "Project not deleted");
    }

    @Test
    public void testMoveProjectToFolderViaDropdown() {
        final String folderName = "Folder";
        TestUtils.createNewItemAndReturnToDashboard(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);
        TestUtils.createNewItemAndReturnToDashboard(this, folderName, TestUtils.Item.FOLDER);
        new HomePage(getDriver()).openItemDropdownWithSelenium(PROJECT_NAME);

        getDriver().findElement(By.linkText("Move")).click();
        new Select(getDriver().findElement(By.name("destination"))).selectByValue("/" + folderName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                getDriver().findElement(By.linkText(folderName)).isDisplayed(),
                "Project not moved to folder");
    }

    @Test
    public void testDeleteMultiConfigurationProjectFromMenu() {
        TestUtils.createJob(this, TestUtils.Job.MULTI_CONFIGURATION, RANDOM_PROJECT_NAME);
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[3]/a")).click();

        TestUtils.deleteItem(this, RANDOM_PROJECT_NAME);

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Welcome to Jenkins!");
    }
}