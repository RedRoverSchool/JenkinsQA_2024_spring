package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem1Test extends BaseTest {

    @Test
    public void testNewItemWithoutType() {
        getDriver().findElement(
                By.cssSelector("div#side-panel a[it='hudson.model.Hudson@19c58a75']"))
                .click();
        getDriver().findElement(
                By.cssSelector("div.add-item-name > input#name"))
                .sendKeys("ProjectWithoutType");

        Assert.assertFalse(getDriver().findElement(
                By.cssSelector("div.btn-decorator > button#ok-button"))
                .isEnabled());
    }

}
