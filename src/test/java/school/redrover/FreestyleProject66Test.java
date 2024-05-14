package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject66Test extends BaseTest {

    @Test
    public void testCreateProject() {

        final String name = "NGV";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(name)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(name));
    }

    @Test(dependsOnMethods = "testCreateProject")
    public void testCreateFolder() {

        final String folderOne = "Folder_1";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(folderOne)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(folderOne));
    }
}
