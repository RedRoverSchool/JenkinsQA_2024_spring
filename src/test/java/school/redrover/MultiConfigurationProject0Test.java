package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class MultiConfigurationProject0Test extends BaseTest {

    private final String projectName = "MCProject";

    @Test
    public void testRenameProjectViaMainPageDropdown() {
        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.linkText(projectName)))
                .pause(1000)
                .scrollToElement(getDriver().findElement(By.cssSelector(String.format("[data-href*='/job/%s/']", projectName))))
                .click()
                .perform();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Rename"))).click();
        getDriver().findElement(By.name("newName")).sendKeys("New");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.linkText(projectName + "New")).isDisplayed());
    }

    @Test
    public void testAddDescription() {
        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);
        final String text = "❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️";

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(String.format("[href = 'job/%s/']", projectName)))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#description>div:first-child"))).getText(),
                text);
    }

    @Test
    public void testEditDescriptionWithoutDelete() {
        final String text = "qwerty123";
        final String additionText = "AAA";

        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(String.format("[href = 'job/%s/']", projectName)))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();

        TestUtils.returnToDashBoard(this);

        getDriver().findElement(By.cssSelector("[href = 'job/" + projectName+ "/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(additionText);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#description div:not([class])")))
                        .getText().equals(additionText + text));
    }

    @Test
    public void testDescriptionPreview() {
        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        final String text = "I want to see preview";
        getDriver().findElement(By.id("job_" + projectName)).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(text);
        getDriver().findElement(By.className("textarea-show-preview")).click();

        Assert.assertEquals(text, getDriver().findElement(By.className("textarea-preview")).getText());
    }

    @Test
    public void testReplacingProjectDescription() {
        final String oldText = "The text to be replaced";
        final String newText = "Replacement text";

        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(String.format("[href = 'job/%s/']", projectName)))).click();
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
        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        getDriver().findElement(By.cssSelector("[href $= 'newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(newProjectName);

        WebElement copyFrom = getDriver().findElement(By.id("from"));
        ((JavascriptExecutor) getDriver()).executeScript(
                "return arguments[0].scrollIntoView(true);",
                copyFrom);
        copyFrom.sendKeys(projectName);

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
        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(String.format("[href = 'job/%s/']", projectName)))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        TestUtils.returnToDashBoard(this);

        getDriver().findElement(By.cssSelector("[href = 'job/" + projectName+ "/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#description>div"))));
    }

    private  static final String NAME_OF_PROJECT = "The name of Multi-configuration project";

    @Test
    public void testCreateMultiConfigurationProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id = 'name']")).sendKeys(NAME_OF_PROJECT);
        getDriver().findElement(By.xpath("//*[@class='hudson_matrix_MatrixProject']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@type='submit']"))).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(),"Project " +  NAME_OF_PROJECT);
    }

    @Test
    public void testAddDescriptionOnConfigurationPage() {
        final String description = "This is project description";
        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        getDriver().findElement(By.linkText(projectName)).click();
        getDriver().findElement(By.linkText("Configure")).click();
        getDriver().findElement(By.name("description")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#description>div:first-child")).getText(),
                description,
                "Project description is not displayed" );
    }

    @Test
    public void testMCPDisableByToggle() {
        TestUtils.createNewItem(this, projectName, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        getDriver().findElement(By.className("jenkins-toggle-switch__label")).click();
        getDriver().findElement(By.name("Apply")).click();

        Assert.assertFalse(getDriver().findElement(By.id("enable-disable-project")).isSelected());
    }
}