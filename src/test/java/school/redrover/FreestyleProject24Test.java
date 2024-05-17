package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class FreestyleProject24Test extends BaseTest {

    private static final String FREESTYLE_NAME = "newFreestyleProject";

    private static final String FOLDER = "NewFolder";

    private static final String DESCRIPTION_TEXT = "This project has been added into the folder";

   @Test
   public void testCreateFreestyleProject() {

       List<String> itemList = new HomePage(getDriver())
               .clickNewItem()
               .setItemName(FREESTYLE_NAME)
               .selectFreestyleAndClickOk()
               .clickSaveButton()
               .clickLogo()
               .getItemList();

       Assert.assertTrue(itemList.contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testAddDescription() {

        String currentFreestyleDescription = new FreestyleProjectPage(getDriver())
                .clickAddDescription()
                .setDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertTrue(currentFreestyleDescription.matches(DESCRIPTION_TEXT));
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testFreestyleProjectMoveToFolder() {

        new HomePage(getDriver())
                .clickLogo()
                .clickNewItem()
                .setItemName(FOLDER)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .openItemDropdown(FREESTYLE_NAME)
                .chooseFolderToMove()
                .chooseFolderAndSave(FOLDER)
                .clickBreadcrumbFolder(FOLDER);

        List<String> itemListInsideFolder = new FolderProjectPage(getDriver()).getItemListInsideFolder();

        Assert.assertTrue(itemListInsideFolder.contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testFreestyleProjectMoveToFolder")
    public void testCheckFreestyleProjectViaBreadcrumb() {

        List<String> itemListInsideFolder = new HomePage(getDriver())
                .openDashboardBreadcrumbsDropdown()
                .clickMyViewsFromDropdown()
                .clickBreadcrumbAll()
                .clickJobNameBreadcrumb(FOLDER)
                .getItemListInsideFolder();

        Assert.assertTrue(itemListInsideFolder.contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testCheckFreestyleProjectViaBreadcrumb")
    public void testDeleteFreestyleProjectViaDropdown() {

        boolean ItemExists = new HomePage(getDriver())
                .clickFolder(FOLDER)
                .openItemDropdown()
                .clickDropdownDeleteProject()
                .clickYesForDeleteFolder()
                .isItemExists(FREESTYLE_NAME);

        Assert.assertFalse(ItemExists);
    }
}
