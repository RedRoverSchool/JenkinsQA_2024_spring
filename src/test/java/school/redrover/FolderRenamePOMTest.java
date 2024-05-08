package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FolderConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class FolderRenamePOMTest extends BaseTest {

    public static final String FOLDER_NAME = "Folder Name";
    public static final String NEW_FOLDER_NAME = "Folder is renamed";

    @Test
    public void testRenameFolder() {

        HomePage homePage = new HomePage(getDriver());
        homePage
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectTypeAndClickOk("Folder");

        new FolderConfigPage(getDriver())
                .clickSaveButton()
                .clickOnRenameButtonLeft()
                .renameFolder(NEW_FOLDER_NAME)
                .clickLogo();

        List<String> itemList = homePage.getItemList();
        Assert.assertTrue(itemList.contains(NEW_FOLDER_NAME), "Folder is not renamed!");
    }
}
