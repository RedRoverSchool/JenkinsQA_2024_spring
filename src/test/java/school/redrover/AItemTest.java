package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class AItemTest extends BaseTest {

    private final String NAME = "Name";
    private final String NEW_NAME = "New Name";
    private final String ITEM_NAME_HINT_TEXT = "» This field cannot be empty, please enter a valid name";
    private List<String> itemsList = null;

    @Test
    public void testCreateItemEmptyNameNegative() {
        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NAME)
                .clearItemNameField()
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.MULTIBRANCH_PIPELINE, new MultibranchPipelineConfigPage(getDriver()));

        Assert.assertFalse(new CreateNewItemPage(getDriver()).okButtonIsEnabled());
    }

    @Test
    public void testCreateNewFolder() {
        itemsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NAME)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FOLDER, new FolderConfigPage(getDriver()))
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemsList.contains(NAME));
    }

    @Test
    public void testRenameFolder() {
        itemsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NAME)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FOLDER, new FolderConfigPage(getDriver()))
                .clickSaveButton()
                .clickLogo()
                .openItemDropdownWithSelenium(NAME)
                .renameFolderFromDropdown()
                .setNewName(NEW_NAME)
                .clickRename()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemsList.contains(NEW_NAME));
    }

    @Test
    public void testCreateMulticonfigurationProjectNegative() {
        new HomePage(getDriver())
                .clickNewItem()
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.MULTI_CONFIGURATION_PROJECT, new MultiConfigurationConfigPage(getDriver()));

        Assert.assertEquals(new CreateNewItemPage(getDriver()).getItemNameHintText(), ITEM_NAME_HINT_TEXT);
    }

    @Test
    public void testCreateMulticonfigurationProject() {
        itemsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NAME)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.MULTI_CONFIGURATION_PROJECT, new MultiConfigurationConfigPage(getDriver()))
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemsList.contains(NAME));
    }

    @Test
    public void testFooterRestApiLinkRGB() {
        Assert.assertEquals(new HomePage(getDriver()).getRestApiLinkColor(), "rgba(20, 20, 31, 1)");
    }
}