package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class CrazyTestersTest extends BaseTest {

    @Test
    public void testDragAndDrop() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        WebElement columnA = driver.findElement(By.id("column-a"));
        WebElement columnB = driver.findElement(By.id("column-b"));
        new Actions(driver)
                .dragAndDrop(columnA, columnB)
                .perform();

        Assert.assertEquals(columnB.getText(), "A");

        driver.quit();
    }

    @Test
    public void testSubmitForm() {
        WebDriver driver = getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("https://demoqa.com/automation-practice-form");

        WebElement textName = driver.findElement(By.id("firstName"));
        textName.sendKeys("Anastasia");

        WebElement textLastName = driver.findElement(By.id("lastName"));
        textLastName.sendKeys("Belugina");

        WebElement radioButton = driver.findElement(By.xpath("//label[text()='Female']"));
        radioButton.click();

        WebElement textMobile = driver.findElement(By.id("userNumber"));
        textMobile.sendKeys("1234567890");

        WebElement submitButton = driver.findElement(By.id("submit"));
        js.executeScript("arguments[0].scrollIntoView();", submitButton);
        submitButton.click();

        WebElement result = driver.findElement(By.id("example-modal-sizes-title-lg"));
        String resultText = result.getText();

        Assert.assertEquals("Thanks for submitting the form", resultText);
    }
}
