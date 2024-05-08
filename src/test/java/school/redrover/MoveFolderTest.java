package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class MoveFolderTest extends BaseTest {private static final String FIRST_FOLDER_NAME = "First folder";
    private static final String SECOND_FOLDER_NAME = "Second folder";

    @Test
    public void testMoveFolderToFolder() {
        List<String> folderNameList = new HomePage(getDriver())
                .createNewFolder(FIRST_FOLDER_NAME)
                .clickLogo()
                .createNewFolder(SECOND_FOLDER_NAME)
                .clickLogo()
                .openItemDropdown(FIRST_FOLDER_NAME)
                .chooseFolderToMove()
                .chooseFolderFromListAndSave(SECOND_FOLDER_NAME)
                .clickFolder(SECOND_FOLDER_NAME)
                .getItemListInsideFolder();

        Assert.assertEquals(folderNameList.get(0), FIRST_FOLDER_NAME);
    }
}

