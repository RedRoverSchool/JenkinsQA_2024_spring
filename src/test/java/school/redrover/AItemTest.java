package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class AItemTest extends BaseTest {

    @Test
    public void testCreateItemEmptyNameNegative() {
        CreateNewItemPage createNewItemPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Name")
                .clearItemNameField()
                .selectFolder();

        Assert.assertFalse(createNewItemPage.isOkButtonEnabled());
    }

    @Test
    public void testCreateNewFolder() {
        List<String> itemsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Name")
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemsList, "Name", "Item not found");
    }

    @Test
    public void testRenameFolder() {
        List<String> itemsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Name")
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .openItemDropdownWithSelenium("Name")
                .renameFolderFromDropdown()
                .setNewName("New Name")
                .clickRename()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemsList, "New Name", "Item not found");
    }

    @Test
    public void testCreateMulticonfigurationProjectNegative() {
        CreateNewItemPage createNewItemPage = new HomePage(getDriver())
                .clickNewItem()
                .selectMultiConfiguration();

        Assert.assertEquals(createNewItemPage.getItemNameHintText(), "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateMulticonfigurationProject() {
        List<String> itemsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Name")
                .selectMultiConfigurationAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemsList, "Name", "Item not found");
    }
}