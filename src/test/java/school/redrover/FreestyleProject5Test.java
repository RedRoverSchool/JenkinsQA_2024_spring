package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject5Test extends BaseTest {

    @Test
    public void testCheckFreestyleProjectNameOnTheDashboard() {
        String PROJECT_NAME = "ProjectName";
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(PROJECT_NAME));
    }
}
