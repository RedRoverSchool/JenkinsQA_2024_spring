package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem7Test extends BaseTest {

    public WebElement getOkButton() {
        return getDriver().findElement(By.id("ok-button"));
    }

    public void enterToNewItemPage() {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
    }

    @Test
    public void testVerifyCreateNewItemPage() {
        enterToNewItemPage();

        String actualResult = getDriver().findElement(By.xpath("//*[@class='h3']")).getText();

        Assert.assertEquals(actualResult, "Enter an item name");
    }

    @Test
    public void testCheckHintToCreateNewItemWithoutName() {
        enterToNewItemPage();
        getDriver().findElement(By.tagName("body")).click();

        WebElement hintElement = getDriver().findElement(By.id("itemname-required"));

        Assert.assertEquals(hintElement.getText(),"» This field cannot be empty, please enter a valid name");
        Assert.assertEquals(hintElement.getCssValue("color"), "rgba(255, 0, 0, 1)");
    }

    @Test
    public void testVerifyOkButtonUnavailableWithNameWithoutType() {
        enterToNewItemPage();
        getDriver().findElement(By.id("name")).sendKeys("NewProject");

        Assert.assertTrue(getOkButton().isDisplayed());
        Assert.assertFalse(getOkButton().isEnabled());
    }
}
