package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class CreateFolder2Test extends BaseTest {

    @Test
    public void testCreate() {
        final String FolderName = "New Folder";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FolderName)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(FolderName));
    }
}