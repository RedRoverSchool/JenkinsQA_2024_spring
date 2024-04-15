package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem1Test extends BaseTest {

    @Test
    public void testNewItemWithoutType() {
        getDriver().findElement(
                By.xpath("//a[@href='newJob']"))
                .click();
        getDriver().findElement(
                By.cssSelector("div.add-item-name > input#name"))
                .sendKeys("ProjectWithoutType");

        Assert.assertFalse(getDriver().findElement(
                By.cssSelector("div.btn-decorator > button#ok-button"))
                .isEnabled());
    }

}
