package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreestyleProject13Test extends BaseTest {

    @Test
    private void testNewFreestyleProjectCreated() {
        final String projectName = "Freestyle1";

        String actualName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FREESTYLE_PROJECT, new FreestyleConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualName, projectName);
    }
}
