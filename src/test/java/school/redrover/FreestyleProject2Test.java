package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject2Test extends BaseTest {

    private static final String PROJECT_NAME = "First project";
    private static final String DESCRIPTION = "My first Freestyle Project";
    private static final String FOLDER_NAME = "Folder";

    @Test
    public void testCreateFreestyleProject() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testDescriptionAddedByUsingAddDescriptionButton() {
        String projectDescription = new HomePage(getDriver())
                .clickCreatedFreestyleName()
                .clickAddDescription()
                .setDescription(DESCRIPTION)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertTrue(projectDescription.matches(DESCRIPTION));
    }

    @Test(dependsOnMethods = "testDescriptionAddedByUsingAddDescriptionButton")
    public void testProjectMovedToFolder() {
        FolderProjectPage folderProjectPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickLogo()
                .openItemDropdown(PROJECT_NAME)
                .clickMoveInDropdown()
                .chooseFolderAndSave(FOLDER_NAME)
                .clickSaveButton()
                .clickLogo()
                .clickFolderName();

        Assert.assertTrue(folderProjectPage.getProjectName().contains(FOLDER_NAME));
        Assert.assertTrue(folderProjectPage.isItemExistsInsideFolder(PROJECT_NAME));
    }
}
