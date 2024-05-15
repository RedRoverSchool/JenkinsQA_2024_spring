package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class OKButtonUnavailableTest extends BaseTest {

    @Test
    public void testOKButtonIsUnavailable() {

        Boolean okButtonIsUnavailable = new HomePage(getDriver())
                .clickNewItem()
                .clearItemNameField()
                .setItemName("")
                .okButtonIsEnabled();

        Assert.assertFalse(okButtonIsUnavailable);
    }
}
