package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static school.redrover.runner.TestUtils.*;


public class MultiConfigurationProjectTest extends BaseTest {

    private final String projectName = "MCProject";

    @Test
    public void testAddDescription() {
        createNewItemAndReturnToDashboard(this, projectName, Item.MULTI_CONFIGURATION_PROJECT);
        final String text = "❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F";

        addProjectDescription(this, projectName, text);

        Assert.assertTrue(
                getWait15(this).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#description")))
                        .getText().startsWith(text));
    }

    @Test
    public void testEditDescriptionWithoutDelete() {
        final String text = "qwerty123";
        final String additionText = "AAA";

        createNewItemAndReturnToDashboard(this, projectName, Item.MULTI_CONFIGURATION_PROJECT);
        addProjectDescription(this, projectName, text);
        returnToDashBoard(this);

        getDriver().findElement(By.cssSelector("[href = 'job/MCProject/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(additionText);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                getWait15(this).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#description")))
                        .getText().contains(additionText + text));
    }

    @Test
    public void testDescriptionPreview() {
        createNewItemAndReturnToDashboard(this, projectName, Item.MULTI_CONFIGURATION_PROJECT);

        String text = "I want to see preview";
        getDriver().findElement(By.id("job_" + projectName)).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(text);
        getDriver().findElement(By.className("textarea-show-preview")).click();

        Assert.assertTrue(getDriver().findElement(By.className("textarea-preview")).getText().equals(text));
    }

    @Test
    public void testReplacingProjectDescription() {
        String oldText = "The text to be replaced";
        String newText = "Replacement text";

        createNewItemAndReturnToDashboard(this, projectName, Item.MULTI_CONFIGURATION_PROJECT);
        addProjectDescription(this, projectName, oldText);

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("description")).sendKeys(newText);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(newText));
    }

    @Test
    public void testMakeCopyMultiConfigurationProject() {
        String newProjectName = "MCProject copy";
        createNewItemAndReturnToDashboard(this, projectName, Item.MULTI_CONFIGURATION_PROJECT);

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