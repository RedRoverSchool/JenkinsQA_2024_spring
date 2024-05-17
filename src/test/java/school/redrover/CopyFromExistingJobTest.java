package school.redrover;

import school.redrover.model.CreateItemPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CopyFromExistingJobTest extends BaseTest{

    @Test
    public void testDropdownMenuContent()  {
        String freestyle1 = "folff";
        String freestyle2 = "folff00";
        String folder1 = "Folder1";
        String folder2 = "bFolder2";

        String firstLetters ="foL";
        String newItemName  ="someName";

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

            List<String> jobsFromDropdownMenu = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(newItemName)
                .setItemNameInCopyForm(firstLetters)
                .getDropdownMenuContent();

        Assert.assertEquals(jobsFromDropdownMenu,firstLettersJobs);
        }

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


    }