package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem7Test extends BaseTest {
    @Test
    public void testVerifyCreateNewItemPage() {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();

        String actualResult = getDriver().findElement(By.xpath("//*[@class='h3']")).getText();

        Assert.assertEquals(actualResult, "Enter an item name");
    }

    @Test
    public void testCreateNewItemWithEmptyName() {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.tagName("body")).click();

        WebElement hintElement = getDriver().findElement(By.id("itemname-required"));

        Assert.assertEquals(hintElement.getText(),"» This field cannot be empty, please enter a valid name");
        Assert.assertEquals(hintElement.getCssValue("color"), "rgba(255, 0, 0, 1)");
    }
}
