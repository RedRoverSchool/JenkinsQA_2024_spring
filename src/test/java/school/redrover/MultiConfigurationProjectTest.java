package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import static school.redrover.runner.TestUtils.*;


public class MultiConfigurationProjectTest extends BaseTest {

    private final String projectName = "MCProject";

    @Test
    public void testAddDescription() {
        createNewItemAndReturnToDashboard(this,projectName, Item.MULTI_CONFIGURATION_PROJECT);
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

        createNewItemAndReturnToDashboard(this,projectName, Item.MULTI_CONFIGURATION_PROJECT);
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
}