package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;


public class FreeStyleProject25Test extends BaseTest {

    @Test
    public void testCreate() {

        String name = "StasM";

        List<String> jobList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(name)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(jobList.contains(name));
    }

    @Test
    public void testCreateFolder() {

        final String folderOne = "Folder_1";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(folderOne)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemList, folderOne, "Project not present");
    }
}




