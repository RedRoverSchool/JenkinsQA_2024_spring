package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CodeQATest {
    @Test
    public void testFieldValidation() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("http://allolosos.com.ua/ru/menu/pizza");

        WebElement button = driver.findElement(By.xpath("//a[@href='/ru/director']"));
        button.click();
        Thread.sleep(1000);

        WebElement textName = driver.findElement(By.id("feedbackdirector-name"));
        textName.sendKeys("Miroslav");
        String actualName = textName.getAttribute("value");
        String expectedName = "Miroslav";
        Assert.assertEquals(actualName, expectedName);

        WebElement textPhone = driver.findElement(By.id("feedbackdirector-phone"));
        textPhone.sendKeys("0502380088");
        String actualPhone = textPhone.getAttribute("value");
        String expectedPhone = "+38 (050) 238-00-88";
        Assert.assertEquals(actualPhone, expectedPhone);

        WebElement textMessage = driver.findElement(By.id("feedbackdirector-message"));
        textMessage.sendKeys("Thank you very much");
        String actualMessage = textMessage.getAttribute("value");
        String expectedMessage = "Thank you very much";
        Assert.assertEquals(actualMessage, expectedMessage);

        driver.quit();
    }
    @Test
    public void TestJenkinsFirst() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");

        WebElement loginArea = driver.findElement(By.cssSelector("#j_username"));
        loginArea.sendKeys("admin");

        WebElement passwordArea = driver.findElement(By.cssSelector("#j_password"));
        passwordArea.sendKeys("admin");

        WebElement buttonSubmit = driver.findElement(By.name("Submit"));
        buttonSubmit.click();

        String actualResult = driver.getTitle();
        String expectedResult = "Dashboard [Jenkins]";

        Assert.assertEquals(actualResult,expectedResult);

        driver.quit();
    }
}
