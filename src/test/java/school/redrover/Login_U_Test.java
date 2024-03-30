package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class Login_U_Test {

    @Test
    public void testLoginUtest() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get("https://utest.com/");

        WebElement loginButton = driver.findElement(By.xpath("(//a[@class='unauthenticated-nav-bar__link'])"));
        loginButton.click();

        WebElement emailField = driver.findElement(By.name("username"));
        emailField.sendKeys("qwerty@gmail.com");

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("qwerty1234");

        WebElement sighInField = driver.findElement(By.name("login"));
        sighInField.click();

        WebElement errorField = driver.findElement(By.xpath("//div[@class='alert alert-error']//span[@class='kc-feedback-text']"));
        String value = errorField.getText();

        System.out.println("Error Message: " + value);

        Assert.assertEquals(value, "Invalid username or password.");

        driver.quit();
    }
}


