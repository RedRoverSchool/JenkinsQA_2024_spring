package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Random;

public class NewItem2Test extends BaseTest {

    public  char generateRandomRestrictedChar() {
        char[] restrictedChars = {'!', '@', '#', '$', '%', '^', '&', '*', '/', '\\', '|', ':', ';', '[', ']'};
        Random random = new Random();
        int index = random.nextInt(restrictedChars.length);

        return restrictedChars[index];
    }

    @Test
    public void testCreateItemWithEmptyName() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        WebElement submitButton = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        submitButton.click();
        Assert.assertFalse(submitButton.isEnabled());

        WebElement inputField = getDriver().findElement(By.xpath("//input[@class='jenkins-input']"));
        Assert.assertEquals(inputField.getText(), "");

        WebElement validationMessage = getDriver().findElement(By.xpath("//div[@class='input-validation-message']"));
        Assert.assertEquals(validationMessage.getText(), "» This field cannot be empty, please enter a valid name");

        String validationMessageColor = Color.fromString(validationMessage.getCssValue("color")).asHex();
        Assert.assertEquals(validationMessageColor, "#ff0000");
    }

    @Test
    public void testCreateItemWithUnsafeChar() {
        char restrictedChar = generateRandomRestrictedChar();

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        WebElement inputField = getDriver().findElement(By.xpath("//input[@class='jenkins-input']"));
        inputField.sendKeys(String.valueOf(restrictedChar));

        WebElement validationMessage = getDriver().findElement(By.xpath("//div[@class='input-validation-message']"));
        Assert.assertEquals(validationMessage.getText(), String.format("» ‘%s’ is an unsafe character", restrictedChar));

        String validationMessageColor = Color.fromString(validationMessage.getCssValue("color")).asHex();
        Assert.assertEquals(validationMessageColor, "#ff0000");
    }

    @Test
    public void testCreateItemWithoutSelectedItemType() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        WebElement inputField = getDriver().findElement(By.xpath("//input[@class='jenkins-input']"));
        inputField.sendKeys("NewProject");

        WebElement submitButton = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        submitButton.click();
        Assert.assertFalse(submitButton.isEnabled());
    }

    @Test
    public void testCreateItemWithSelectedItemType() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        String projectName = "NewProject";
        WebElement inputField = getDriver().findElement(By.xpath("//input[@class='jenkins-input']"));
        inputField.sendKeys(projectName);

        WebElement item1 = getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']"));
        item1.click();
        Assert.assertTrue(Boolean.parseBoolean(item1.getAttribute("aria-checked")));

        WebElement submitButton = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        Assert.assertTrue(submitButton.isEnabled());
        submitButton.click();

        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertEquals(currentUrl, String.format("http://localhost:8080/job/%s/configure", projectName));

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        WebElement pageHeadline = getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']"));
        Assert.assertEquals(pageHeadline.getText(), projectName);
    }

}
