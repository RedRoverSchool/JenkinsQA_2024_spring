package school.redrover;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.CreateItemPage;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.TestUtils.*;
import school.redrover.runner.TestUtils;
import java.util.ArrayList;
import java.util.List;

public class CopyFromExistingJobTest extends BaseTest{
    @Test
    public void testCopyFromNotExistingJob() {
        TestUtils.createNewJob(this, Job.PIPELINE, "ppp");
        TestUtils.createNewJob(this, Job.FREESTYLE, "fff");
        TestUtils.createNewJob(this, Job.FOLDER, "Folder1");
        String notExistingName ="AAA";

        CreateItemPage errorPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("someName")
                .setItemNameInCopyForm(notExistingName)
                .clickOkButton();

        Assert.assertTrue(errorPage.getCurrentUrl().endsWith("/createItem"));
        Assert.assertEquals(errorPage.getPageHeaderText(),"Error");
        Assert.assertEquals(errorPage.getErrorMessageText(),"No such job: " + notExistingName);
}

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
    }