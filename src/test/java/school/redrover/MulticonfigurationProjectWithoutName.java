package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProjectWithoutName extends BaseTest {

    @Test
    public void testCreateMulticonfigurationProject(){

        String errorName = new HomePage(getDriver())
                .clickNewItem()
                .selectMultiConfigurationAndClickOk()
                .getErrorRequiresName();

        Assert.assertTrue(errorName.contains("This field cannot be empty, please enter a valid name"));
    }
}
