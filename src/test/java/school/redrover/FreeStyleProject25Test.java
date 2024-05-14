package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;


public class FreeStyleProject25Test extends BaseTest {

    @Test
    public void testCreate() {

        final String name = "StasM";

        List<String> jobList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(name)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(jobList.contains(name));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testCreateFolder() {

        final String name = "Folder_1";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(name)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(name));
    }
}




