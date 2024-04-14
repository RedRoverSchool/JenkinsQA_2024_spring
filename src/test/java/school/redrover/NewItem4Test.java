package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem4Test extends BaseTest {

    private static final By BUTTON_NEW_ITEM = By.linkText("New Item");
    private static final By BUTTON_SUBMIT = By.id("ok-button");

    @Test
    public void testCreateItemWithNoName() {
        getDriver().findElement(BUTTON_NEW_ITEM).click();

        getDriver().findElement(BUTTON_SUBMIT).click();
        WebElement inputValidationMassage = getDriver().findElement(By.id("itemname-required"));

        Assert.assertEquals(inputValidationMassage.getText(),
                "» This field cannot be empty, please enter a valid name");
    }
}

