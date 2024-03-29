package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SofaTest {
    @Test
    public void testBadLogin() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        
        driver.get("https://www.sofa.com/gb");
        Assert.assertEquals(driver.getTitle(), "Sofas - Handmade To Order, Especially for you | Sofa.com");
        
        WebElement loginButton = driver.findElement(By.xpath("//a[@href=\"/gb/login\"]"));
        loginButton.click();
        Thread.sleep(1000);
        
        WebElement inputEmail = driver.findElement(By.id("j_username"));
        inputEmail.sendKeys("hello@gmail.com");
        WebElement inputPassword = driver.findElement(By.id("j_password"));
        inputPassword.sendKeys("123");
        WebElement loginBtn = driver.findElement(By.id("loginBtn"));
        loginBtn.click();
        Thread.sleep(1000);
        
        WebElement errorText = driver.findElement(By.xpath("//div[@class=\"alert alert-danger alert-dismissable\"]"));
        String text = errorText.getText();
        Assert.assertEquals(text, "×\n" +
                "Your username or password was incorrect.");
        
        driver.quit();

    }
}
