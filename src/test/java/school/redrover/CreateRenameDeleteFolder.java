package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import java.util.List;

public class CreateRenameDeleteFolder extends BaseTest {

    @Test
    public void createNewFolder() {
        String newFolderName = "TG New Folder";

        FolderStatusPage folderStatusPage = createNewFolderByName(newFolderName);

        String getBreadcrumbName = folderStatusPage.getBreadcrumbName();
        Assert.assertEquals(getBreadcrumbName, newFolderName);
    }

    @Test
    public void renameFolder() {
        String newFolderName = "TG New Folder";
        String renameFolderName = "TG Folder Renamed";

        FolderStatusPage folderStatusPage = createNewFolderByName(newFolderName);
        FolderRenamePage renameItemPage = folderStatusPage.clickRenameMenuButton();
        renameItemPage.setNewName(renameFolderName);
        FolderStatusPage renamedFolderStatusPage = renameItemPage.clickSave();

        String getBreadcrumbName = renamedFolderStatusPage.getBreadcrumbName();
        Assert.assertEquals(getBreadcrumbName, renameFolderName);
    }

    @Test
    public void deleteFolder() {
        String newFolderName = "TG New Folder";

        FolderStatusPage folderStatusPage = createNewFolderByName(newFolderName);
        DeleteDialog deleteDialog = folderStatusPage.clickDeleteMenuButton();
        HomePage homepage = deleteDialog.clickYes(new HomePage(getDriver()));

        List<String> folders = homepage.getItemList();
        Assert.assertTrue(folders.isEmpty());
    }

    private FolderStatusPage createNewFolderByName(String folderName) {
        HomePage homePage = new HomePage(getDriver());
        CreateNewItemPage createNewItemPage = homePage.clickNewItem();
        createNewItemPage.setItemName(folderName);
        FolderConfigPage folderConfigPage = createNewItemPage.selectFolderAndClickOk();
        FolderStatusPage folderStatusPage = folderConfigPage.clickSaveButton();

        return folderStatusPage;
    }
}