package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FreeStyleProjectDSTest extends BaseTest {

    @Test
    public void testCreateFreeStyleProjectDS() {
        final String projectName = "FreeStyleProjectDS";
        List<String> name = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FREESTYLE_PROJECT, new FreestyleConfigPage(getDriver()))
                .clickLogo()
                .getItemList();

        Assert.assertTrue(name.contains(projectName));
    }
}
