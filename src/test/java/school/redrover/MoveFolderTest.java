package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class MoveFolderTest extends BaseTest {

    private static final String FIRST_FOLDER_NAME = "First folder";
    private static final String SECOND_FOLDER_NAME = "Second folder";

    @Test
    public void testMoveFolderToFolder() {
        new HomePage(getDriver())
                .createNewFolder(FIRST_FOLDER_NAME);
        new HomePage(getDriver()).clickLogo();

        new HomePage(getDriver())
                .createNewFolder(SECOND_FOLDER_NAME);
        new HomePage(getDriver()).clickLogo();

        new HomePage(getDriver())
                .openItemDropdown(FIRST_FOLDER_NAME)
                .chooseFolderToMove()
                .chooseFolderFromListAndSave(SECOND_FOLDER_NAME);

        List<String> folderNameList = new HomePage(getDriver())
                .clickFolder(SECOND_FOLDER_NAME)
                .getItemListInsideFolder();

        Assert.assertEquals(folderNameList.get(0), FIRST_FOLDER_NAME);
    }
}
