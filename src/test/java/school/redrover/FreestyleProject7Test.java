package school.redrover;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FreestyleProject7Test extends BaseTest {
    final String freestyleProjectName = "FreestyleProjectTest";
    @Test
    public void testCreateFreestyleProject() {

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(freestyleProjectName)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FREESTYLE_PROJECT, new FreestyleConfigPage(getDriver()))
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(freestyleProjectName));
    }
}
