package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class FreestyleProject13Test extends BaseTest {

    @Test
    private void testNewFreestyleProjectCreated() {

        final String name = "Freestyle1";

        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(name)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(projectName, name);
    }
}
