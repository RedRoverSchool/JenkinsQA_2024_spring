package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class TheInternetTest extends BaseTest {
    @Test
    public void testLoginAndLogout() {
        WebDriver driver = getDriver();
        driver.get("https://the-internet.herokuapp.com/");

        Assert.assertEquals(driver.getTitle(), "The Internet");

        driver.findElement(By.linkText("Form Authentication")).click();

        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button")).click();

        Assert.assertEquals(driver.findElement(By.tagName("h2")).getText(), "Secure Area");

        driver.findElement(By.linkText("Logout")).click();
        driver.findElement(By.tagName("h2")).getText();

        Assert.assertEquals(driver.findElement(By.tagName("h2")).getText(), "Login Page");
    }
}
