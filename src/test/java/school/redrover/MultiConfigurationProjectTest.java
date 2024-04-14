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
    private void returnToDashBoard() {
        getDriver().findElement(By.cssSelector("a[href = '/']")).click();
    }

    @Test
    public void testAddDescription() {
        createItem("MCProject", "hudson_matrix_MatrixProject");
        final String text = "❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F❤\uFE0F";
        returnToDashBoard();

        getDriver().findElement(By.cssSelector("[href = 'job/MCProject/']")).click();
        getWait15().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        getDriver().findElement(By.name("description")).sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                getWait15().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#description")))
                        .getText().startsWith(text));
    }

    @Test
    public void testEditDescriptionWithoutDelete() {
        createItem("MCProject", "hudson_matrix_MatrixProject");
        returnToDashBoard();

        final String text = "qwerty123";

        getDriver().findElement(By.cssSelector("[href = 'job/MCProject/']")).click();
        getWait15().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        getDriver().findElement(By.name("description")).sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();
        returnToDashBoard();

        getDriver().findElement(By.cssSelector("[href = 'job/MCProject/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys("AAA");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                getWait15().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#description")))
                        .getText().contains("AAA" + text));
    }
}