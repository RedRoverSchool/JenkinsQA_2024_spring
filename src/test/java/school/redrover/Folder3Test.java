package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.HomePage;
import school.redrover.model.ItemPage;
import school.redrover.runner.BaseTest;

public class Folder3Test extends BaseTest {

    private static final String FOLDER_NAME_FIRST = "Folder_1";
    private static final String FOLDER_NAME_NEW = "Folder_1_New";
    private static final String FOLDER_DESCRIPTION_FIRST = "Some description of the folder.";

    @Test
    public void testCreate() {

        HomePage homePage = new HomePage(getDriver());
        homePage.clickNewItem();

        new ItemPage(getDriver())
                .setItemName(FOLDER_NAME_FIRST)
                .selectFolderType()
                .clickButtonOK()
                .clickLogo();

        Assert.assertTrue(homePage.isItemExists(FOLDER_NAME_FIRST));
    }

    @Test (dependsOnMethods = "testCreate")
    public void testAddDescription() {
        new HomePage(getDriver()).clickFolder(FOLDER_NAME_FIRST);

        String textInDescription = new FolderProjectPage(getDriver())
                .clickAddOrEditDescription()
                .setDescription(FOLDER_DESCRIPTION_FIRST)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(textInDescription, FOLDER_DESCRIPTION_FIRST);
    }

    @Test (dependsOnMethods = "testAddDescription")
    public void testRename() {
        HomePage homePage = new HomePage(getDriver());
        homePage.clickFolder(FOLDER_NAME_FIRST);

        new FolderStatusPage(getDriver())
                .clickOnRenameButton()
                .setNewName(FOLDER_NAME_NEW)
                .clickRename()
                .clickLogo();

        Assert.assertTrue(homePage.isItemExists(FOLDER_NAME_NEW) && !homePage.isItemExists(FOLDER_NAME_FIRST));
    }

}
