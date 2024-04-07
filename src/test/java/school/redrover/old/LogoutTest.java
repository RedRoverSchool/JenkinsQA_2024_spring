package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class LogoutTest {
    @Test
    public void TestMyTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement textArea = driver.findElement(By.id("user-name"));
        textArea.sendKeys("standard_user");

        WebElement textArea2 = driver.findElement(By.id("password"));
        textArea2.sendKeys("secret_sauce");

        WebElement button = driver.findElement(By.name("login-button"));
        button.click();

        WebElement button2= driver.findElement(By.className("bm-burger-button"));
        button2.click();

        WebElement logOut = driver.findElement(By.id("logout_sidebar_link"));
        String value = logOut.getAccessibleName();

        Assert.assertEquals(value,"Logout");

        driver.quit();

    }
}
