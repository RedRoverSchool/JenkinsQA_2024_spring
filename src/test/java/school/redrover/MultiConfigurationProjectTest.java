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

public class MultiConfigurationProjectTest extends BaseTest {

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

        TestUtils.getWait15(this).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Rename"))).click();
        getDriver().findElement(By.name("newName")).sendKeys("New");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.linkText(projectName + "New")).isDisplayed());
    }

    @Test
    public void testAddDescription() {
        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);
        final String text = "❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️";

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(String.format("[href = 'job/%s/']", projectName)))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                TestUtils.getWait15(this).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#description")))
                        .getText().startsWith(text));
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
                TestUtils.getWait15(this).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#description")))
                        .getText().contains(additionText + text));
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

        Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(newText));
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
}