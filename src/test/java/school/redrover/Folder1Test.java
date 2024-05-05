package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Folder1Test extends BaseTest {
    private static final String ROOT_FOLDER_NAME = "Root Folder";
    private static final String FIRST_FOLDER_NAME = "Inner Folder 1";
    private static final String SECOND_FOLDER_NAME = "Inner Folder 2";

    @Test
    public void testCreate() {
        String pageTopic = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ROOT_FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getPageTopic();

        Assert.assertEquals(pageTopic, ROOT_FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testCheckNewFolderIsEmpty() {
        Boolean isFolderEmpty = new HomePage(getDriver())
                .clickFolder(ROOT_FOLDER_NAME)
                .isFolderEmpty();

        Assert.assertTrue(isFolderEmpty);
    }

    @Test(dependsOnMethods = "testCheckNewFolderIsEmpty")
    public void testCreateTwoInnerFolder() {
        List<String> itemNames = new HomePage(getDriver())
                .clickFolder(ROOT_FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(FIRST_FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickFolder(ROOT_FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(SECOND_FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickFolder(ROOT_FOLDER_NAME)
                .getItemListInsideFolder();

        Assert.assertTrue(itemNames.contains(FIRST_FOLDER_NAME) && itemNames.contains(SECOND_FOLDER_NAME));
    }
}
