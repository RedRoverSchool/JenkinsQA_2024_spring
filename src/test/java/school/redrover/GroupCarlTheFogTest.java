package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupCarlTheFogTest {

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

    @Test
    public void newTest(){
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com");

        WebElement fieldLogin = driver.findElement(By.name("user-name"));
        fieldLogin.sendKeys("standard_user");

        WebElement fieldPassword = driver.findElement(By.name("password"));
        fieldPassword.sendKeys("secret_sauce");

        WebElement button = driver.findElement(By.id("login-button"));
        button.click();

        WebElement message = driver.findElement(By.className("app_logo"));
        String value = message.getText();

        Assert.assertEquals(value, "Swag Labs");

        driver.quit();
    }
}
