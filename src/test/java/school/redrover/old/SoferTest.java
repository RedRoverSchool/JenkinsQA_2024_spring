package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class SoferTest {

    @Test
    public void testLogin(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement textBoxUsername = driver.findElement(By.cssSelector("#user-name"));
        textBoxUsername.sendKeys("standard_user");

        WebElement textBoxPassword = driver.findElement(By.cssSelector("#password"));
        textBoxPassword.sendKeys("secret_sauce");

        WebElement submitButton = driver.findElement(By.cssSelector("#login-button"));
        submitButton.click();

        WebElement message = driver.findElement(By.cssSelector("#header_container > div.primary_header > div.header_label > div"));
        String value = message.getText();
        Assert.assertEquals(value, "Swag Labs");

        driver.quit();
    }
}
