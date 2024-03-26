package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MyFirstSauceDemoTest {
        @Test
        public void testTest(){
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.saucedemo.com");

            WebElement userName = driver.findElement(By.id("user-name"));
            WebElement password = driver.findElement(By.id("password"));
            WebElement submitButton = driver.findElement(By.id("login-button"));

            userName.sendKeys("problem_user");
            password.sendKeys("secret_sauce");
            submitButton.click();

            String message = driver.findElement(By.xpath("//span[@class='title']")).getText();
            Assert.assertEquals(message,"Products" );

            driver.quit();
        }
}
