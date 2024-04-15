package school.redrover;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class NewItem3Test extends BaseTest {

    private boolean isException = false;

    @Test
    public void createItemEmptyNameNegativeTest() {
        try {
            TestUtils.createItem(TestUtils.FREESTYLE_PROJECT, "", getDriver());
        } catch (NoSuchElementException e) {
            this.isException = true;
            }

        Assert.assertTrue(isException);
        }
    }

