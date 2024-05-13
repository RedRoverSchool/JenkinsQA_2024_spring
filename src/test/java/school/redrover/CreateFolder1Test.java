package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class CreateFolder1Test extends BaseTest {

    @Test
    public void testNewlyCreatedFolderIsEmptyAJ() {
        final String folderName = "NewProjectFolder";
        final String thisFolderIsEmptyMessage = "This folder is empty";
        final String createAJobLinkText = "Create a job";

        String actualFolderName = new HomePage(getDriver())
                .createNewFolder(folderName)
                .clickFolder(folderName)
                .getPageHeading();

        String actualEmptyStateMessage = new FolderProjectPage(getDriver())
                .getMessageFromEmptyFolder();

        String actualCreateJobLinkText = new FolderProjectPage(getDriver())
                .getTextWhereClickForCreateJob();

        Boolean isLinkForCreateJobDisplayed = new FolderProjectPage(getDriver())
                .isLinkForCreateJobDisplayed();

        Assert.assertEquals(actualFolderName, folderName);
        Assert.assertEquals(actualEmptyStateMessage, thisFolderIsEmptyMessage);
        Assert.assertEquals(actualCreateJobLinkText, createAJobLinkText);
        Assert.assertTrue(isLinkForCreateJobDisplayed, "newJobLink is NOT displayed");
    }

    @Test
    public void testRenameFolder() {
        final String folderName = "ProjectFolder";
        final String newFolderName = "NewProjectFolder";

        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(folderName)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        //HomePage homePage = new HomePage(getDriver());
        List<String> itemList = new HomePage(getDriver())
                .openItemDropdown(folderName)
                .selectRenameFromDropdown()
                .changeProjectNameWithClear(newFolderName)
                .clickRenameButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(newFolderName));
    }

    @Test
    public void testCreateFolderSpecialCharacters() {
        String[] specialCharacters = {"!", "%", "&", "#", "@", "*", "$", "?", "^", "|", "/", "]", "["};

        new HomePage(getDriver())
                .clickNewItem();

        for (String specChar : specialCharacters) {
            String actualErrorMessage = new CreateNewItemPage(getDriver())
                    .clearItemNameField()
                    .setItemName("Fold" + specChar + "erdate")
                    .getErrorMessage();

            String expectMessage = "» ‘" + specChar + "’ is an unsafe character";

            Assert.assertEquals(actualErrorMessage, expectMessage, "Message is not displayed");
        }
    }
}
