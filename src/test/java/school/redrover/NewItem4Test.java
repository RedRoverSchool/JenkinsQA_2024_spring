package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem4Test extends BaseTest {

    private static final By BUTTON_NEW_ITEM = By.linkText("New Item");

    @Test
    public void testGoToCreateItemPage() {
        getDriver().findElement(BUTTON_NEW_ITEM).click();
        WebElement title = getDriver().findElement(By.xpath("//*[text()='Enter an item name']"));

        Assert.assertTrue(title.isDisplayed());
    }
}