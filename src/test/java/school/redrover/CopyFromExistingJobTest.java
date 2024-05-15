package school.redrover;

import school.redrover.model.CreateItemPage;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class CopyFromExistingJobTest extends BaseTest {
    @Test (dependsOnMethods = "testDropdownMenuContent")
    public void testCopyFromNotExistingJob() {
        String notExistingName = "AAA";
        String newItemName = "someName";

        CreateItemPage errorPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(newItemName)
                .setNotExistingJobNameAndClickOkButton(notExistingName);

        Assert.assertTrue(errorPage.getCurrentUrl().endsWith("/createItem"));
        Assert.assertEquals(errorPage.getPageHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessageText(), "No such job: " + notExistingName);
    }

    @Test
    public void testDropdownMenuContent() {
        final String freestyle1 = "folff";
        final String freestyle2 = "folff00";
        final String folder1 = "Folder1";
        final String folder2 = "bFolder2";

        final String firstLetters = "foL";
        final String newItemName = "someName";

        List<String> firstLettersJobs = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(freestyle1)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickNewItem()
                .setItemName(folder1)
                .selectFolderAndClickOk()
                .clickLogo()
                .clickNewItem()
                .setItemName(folder2)
                .selectFolderAndClickOk()
                .clickLogo()
                .clickNewItem()
                .setItemName(freestyle2)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .getJobsBeginningFromThisFirstLetters(firstLetters);

        CreateNewItemPage createNewItemPageFilled = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(newItemName)
                .setItemNameInCopyForm(firstLetters);

        List<String> jobsFromDropdownMenu = createNewItemPageFilled.getDropdownMenuContent();

        Assert.assertEquals(jobsFromDropdownMenu, firstLettersJobs);
    }

}