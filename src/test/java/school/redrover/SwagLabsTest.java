package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SwagLabsTest {
    @Test
    public void testUsps() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/");


        WebElement userNameField = driver.findElement(By.id("user-name"));
        userNameField.sendKeys("standard_user");


        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("secret_sauce");


        WebElement buttonLogin = driver.findElement(By.id("login-button"));
        buttonLogin.click();


        WebElement findText = driver.findElement(By.xpath("//div[@class='product_label']"));
        String textString = findText.getText();
        Assert.assertEquals(textString, "Products");


        driver.quit();
    }
}




