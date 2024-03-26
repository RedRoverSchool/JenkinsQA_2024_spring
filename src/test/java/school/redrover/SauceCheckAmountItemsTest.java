package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.util.List;

public class SauceCheckAmountItemsTest {
    WebDriver driver = new ChromeDriver();
    @Test
    public void testCheckAmountItems() throws InterruptedException {
        int expectedResult = 6;
        //Preconditions
        driver.get("https://www.saucedemo.com/");
        WebElement name = driver.findElement(By.xpath("//input[@id='user-name']"));
        name.sendKeys("standard_user");
        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        //Test
        List<WebElement> items = driver.findElements(By.xpath("//div[@class='inventory_item']"));
        int actualResult = items.size();
        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();
    }
    @AfterMethod
    public void quit(){driver.quit();
    }
}