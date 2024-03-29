package school.redrover.model;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CodeQASaucedemoTest {
    @Test
    public void testLoginBtn(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        WebElement submitButton = driver.findElement(By.id("login-button"));
        Assert.assertEquals(submitButton.getAttribute("value").toString(), "Login");
        driver.quit();
    }
}
