package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class MultiConfigurationProjectTest extends BaseTest {

    private WebDriverWait getWait15() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(15));
    }

    private void createItem(String name, String itemClassName) {

        getDriver().findElement(By.cssSelector("#side-panel > div > div")).click();
        getWait15().until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(name);
        getDriver().findElement(By.className(itemClassName)).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testAddDescription() {
        createItem("MCP", "hudson_matrix_MatrixProject");
        final String text = "xxx";

        getWait15().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name = 'description']")))
                .sendKeys(text);
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).click();

        Assert.assertTrue(
                getWait15().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#description"))).getText().startsWith(text));
    }
}