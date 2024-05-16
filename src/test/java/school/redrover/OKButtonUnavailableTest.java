package school.redrover;

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
                .isOkButtonEnabled();

        Assert.assertFalse(okButtonIsUnavailable);
    }
}
