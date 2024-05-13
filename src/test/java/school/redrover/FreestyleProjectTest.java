package school.redrover;

import org.testng.Assert;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.List;
import org.testng.annotations.Test;


public class FreestyleProjectTest extends BaseTest {
    final String FREESTYLE_PROJECT_NAME = "Freestyle1";
    private static final String FREESTYLE_PROJECT_DESCRIPTION = "Some description text";
    private static final String NEW_FREESTYLE_PROJECT_NAME = "New Freestyle Project Name";

    @Test
    public void testCreateNewItem(){
        List<String> itemList= new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();
        Assert.assertTrue(itemList.contains(FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testRenameProject() {

        List<String> actualResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickRename()
                .setNewName(NEW_FREESTYLE_PROJECT_NAME)
                .clickRename()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(actualResult.contains(NEW_FREESTYLE_PROJECT_NAME));
    }

}
