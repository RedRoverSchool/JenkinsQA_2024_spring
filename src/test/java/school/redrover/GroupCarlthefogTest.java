package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupCarlthefogTest {

    @Test
    public void testSaucedemo() {
        String expectedPageName = "Swag Labs";

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String actualPageName = driver.findElement(By.xpath("//div[@class= 'app_logo']")).getText();

        Assert.assertEquals(expectedPageName, actualPageName);
        driver.quit();
    }
}
