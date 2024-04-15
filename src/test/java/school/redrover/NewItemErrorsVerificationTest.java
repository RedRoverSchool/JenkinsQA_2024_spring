package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class NewItemErrorsVerificationTest extends BaseTest {

    @Test
    public void createNewItemErrorMessageUnsafeChar() {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("*");
        WebElement element = getDriver().findElement(By.xpath("//*[@id=\"itemname-invalid\"]"));

        String resultText = element.getText();
        Assert.assertEquals(resultText, "» ‘*’ is an unsafe character");
    }

    @Test
    public void createNewItemErrorMessageEmptyName() {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.cssSelector("div#j-add-item-type-nested-projects > ul > li > div:nth-of-type(2) > img")).click();
        WebElement element = getDriver().findElement(By.xpath("//*[@id=\"itemname-required\"]"));

        String resultText = element.getText();
        Assert.assertEquals(resultText, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void createNewItemSkipType() {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Item Without Selected Type");
        WebElement webElement = getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]"));

        String attribute = webElement.getAttribute("disabled");

        Assert.assertNotNull (attribute, "Ok button is not Disabled");
    }
}
