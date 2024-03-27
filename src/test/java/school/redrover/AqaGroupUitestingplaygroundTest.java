package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;


public class AqaGroupUitestingplaygroundTest extends AqaGroupBaseTest{
    @Test
    public void testClientSideDelay() {
        getDriver().get("http://uitestingplayground.com/clientdelay");

        getWait25().until(ExpectedConditions.elementToBeClickable(By.id("ajaxButton"))).click();

        Assert.assertTrue(
                getWait25()
                        .until(ExpectedConditions.visibilityOfElementLocated(By.className("bg-success")))
                        .getText()
                        .startsWith("Data calculated"),
                "Label text wrong");
    }

    @Test
    public void testOverlappedElement() {
        String link = "http://uitestingplayground.com/overlapped";
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(link);

            WebElement name = driver.findElement(By.id("name"));
            ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollIntoView(true);", name);
            String myName = "myName";
            name.sendKeys(myName);

            Assert.assertEquals(name.getAttribute("value"), myName, "input field contains wrong value");
        } finally {
            driver.quit();
        }
    }
}
