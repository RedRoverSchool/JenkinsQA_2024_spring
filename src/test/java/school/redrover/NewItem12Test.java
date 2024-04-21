package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class NewItem12Test extends BaseTest {
    @Test
    public void CreateNewItem(){
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("main-panel")).click();
        WebElement validationMessage = getDriver().findElement(By.id("itemname-required"));
        Assert.assertEquals(validationMessage.getText(), "» This field cannot be empty, please enter a valid name");

        getDriver().findElement(By.id("name")).sendKeys("GBtest");
        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        Assert.assertTrue(okButton.isEnabled());
        okButton.click();

        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
    }
}
