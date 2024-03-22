package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

import java.time.Duration;

public class DemoQATextBoxTest {

    @Test
    public void testTextBox() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://demoqa.com/text-box");

        String title = driver.getTitle();
        Assert.assertEquals("DEMOQA", title, "");

        WebElement fullname = driver.findElement(By.id("userName"));
        WebElement email = driver.findElement(By.id("userEmail"));
        WebElement curAddress = driver.findElement(By.id("currentAddress"));
        WebElement perAddress = driver.findElement(By.id("permanentAddress"));

        Faker faker = new Faker();
        String randomName = faker.name().fullName();
        String randomEmail = faker.internet().emailAddress();
        String randomAddressC = faker.address().fullAddress();
        String randomAddressP = faker.address().fullAddress();

        fullname.sendKeys(randomName);
        email.sendKeys(randomEmail);
        curAddress.sendKeys(randomAddressC);
        perAddress.sendKeys(randomAddressP);

        WebElement button = driver.findElement(By.id("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();

        WebElement fullname_result = driver.findElement(By.xpath("//p[@id='name']"));
        Assert.assertEquals("Name:" + randomName, fullname_result.getText(), "Name is not equals");

        WebElement email_result = driver.findElement(By.xpath("//p[@id='email']"));
        Assert.assertEquals("Email:" + randomEmail, email_result.getText(), "Email is not equals");

        WebElement curAddress_result = driver.findElement(By.xpath("//p[@id='currentAddress']"));
        Assert.assertEquals("Current Address :" + randomAddressC, curAddress_result.getText(),
                "Current Address is not equals");

        WebElement perAddress_result = driver.findElement(By.xpath("//p[@id='permanentAddress']"));
        Assert.assertEquals("Permananet Address :" + randomAddressP, perAddress_result.getText(),
                "Permananet Address is not equals");

        driver.quit();
    }
}
