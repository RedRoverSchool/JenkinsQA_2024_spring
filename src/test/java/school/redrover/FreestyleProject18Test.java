package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject18Test extends BaseTest {


    @Test
    public void testDeleteProjectAnother() {
        final String projectItemName = "JavaHashGroupProject";
        final String projectDescription = "test for JavaHashGroupProject ";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectItemName)
                .selectFreestyleAndClickOk()
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
