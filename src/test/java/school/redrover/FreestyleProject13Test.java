package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class FreestyleProject13Test extends BaseTest {

    @Test
    private void testNewFreestyleProjectCreated() {
        final String projectName = "Freestyle1";

        String actualName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualName, projectName);
    }
}
