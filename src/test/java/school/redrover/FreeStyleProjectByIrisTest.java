package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FreeStyleProjectByIrisTest extends BaseTest {

    @Test
    public void testCreate() {

        final String newFreestyleProject = "new Freestyle project";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(newFreestyleProject)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FREESTYLE_PROJECT, new FreestyleConfigPage(getDriver()))
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(newFreestyleProject));
    }
}
