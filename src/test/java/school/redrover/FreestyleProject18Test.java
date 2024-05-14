package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FreestyleProject18Test extends BaseTest {
    final String projectItemName = "JavaHashGroupProject";

    @Test
    public void testDeleteProjectAnother() {
        final String projectDescription = "test for JavaHashGroupProject ";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectItemName)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FREESTYLE_PROJECT, new FreestyleConfigPage(getDriver()))
                .setDescription(projectDescription)
                .clickSaveButton()
                .clickLogo()
                .clickCreatedItemName()
                .deleteFreestyleProject()
                .confirmDeleteFreestyleProject()
                .getItemList();

        Assert.assertTrue(itemList.isEmpty());
    }
}
