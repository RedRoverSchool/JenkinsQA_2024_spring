package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem5Test extends BaseTest {
    @Test
    public void testCreateItemWithEmptyName() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).clear();
        WebElement submitButton = getDriver().findElement(By.id("ok-button"));
        submitButton.click();

        Assert.assertFalse(submitButton.isEnabled());

        WebElement validationMessage = getDriver().findElement(By.id("itemname-required"));
        Assert.assertEquals(validationMessage.getText(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertEquals(validationMessage.getCssValue("color"), "rgba(255, 0, 0, 1)");
    }
}
