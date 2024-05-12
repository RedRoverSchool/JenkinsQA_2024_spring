package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class FreeStyleProjectByIrisTest extends BaseTest {

    @Test
    public void testCreateProject() {

        final String newFreestyleProject = "new Freestyle Project";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(newFreestyleProject)
                .selectFreestyleAndClickOk()
                .clickSave()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(newFreestyleProject));
    }
}
