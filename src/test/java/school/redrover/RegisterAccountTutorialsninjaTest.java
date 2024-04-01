package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class RegisterAccountTutorialsninjaTest extends BaseTest {

    @Test
    public void testRegisterAccountTutorialsninja() {
        WebDriver driver = getDriver();
        driver.get("https://tutorialsninja.com/demo/");

        WebElement accountButton = driver.findElement(By.xpath("(//i[@class='fa fa-user'])"));
        accountButton.click();

        WebElement registerButton = driver.findElement(By.xpath("(//a[normalize-space()='Register'])[1]"));
        registerButton.click();

        WebElement firstNameField = driver.findElement(By.name("firstname"));
        firstNameField.sendKeys("Anna");

        WebElement lastNameField = driver.findElement(By.name("lastname"));
        lastNameField.sendKeys("La");

        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys("qwerty123@gmail");

        WebElement telephoneField = driver.findElement(By.name("telephone"));
        telephoneField.sendKeys("6469043333");

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("qwerty123");

        WebElement confirmPasswordField = driver.findElement(By.name("confirm"));
        confirmPasswordField.sendKeys("qwerty123");

        WebElement continueButton = driver.findElement(By.xpath("//input[@value='Continue']"));
        continueButton.click();

        WebElement errorAfterEmailField = driver.findElement(By.xpath("(//div[@class='text-danger'])[1]"));
        String value = errorAfterEmailField.getText();

        Assert.assertEquals(value, "E-Mail Address does not appear to be valid!");

    }
}