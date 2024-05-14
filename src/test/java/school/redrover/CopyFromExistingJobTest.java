package school.redrover;

import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.TestUtils;

import java.util.List;

public class CopyFromExistingJobTest extends BaseTest{
    @Test
    public void testCopyFromNotExistingJob() {
        TestUtils.createProjectItem(TestUtils.ProjectType.PIPELINE, this, new PipelineConfigPage(getDriver()), "ppp");
        TestUtils.createProjectItem(TestUtils.ProjectType.FREESTYLE_PROJECT, this, new FreestyleConfigPage(getDriver()), "fff");
        TestUtils.createProjectItem(TestUtils.ProjectType.FOLDER, this, new FolderConfigPage(getDriver()), "Folder1");
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
        String folder1 = "folder1";
        String folder2 = "bFolder2";

        String firstLetters ="fol";
        String newItemName  ="someName";

        List<String> firstLettersJobs = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(freestyle1)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FREESTYLE_PROJECT, new FreestyleConfigPage(getDriver()))
                .clickLogo()
                .clickNewItem()
                .setItemName(folder1)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FOLDER, new FolderConfigPage(getDriver()))
                .clickLogo()
                .clickNewItem()
                .setItemName(folder2)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FOLDER, new FolderConfigPage(getDriver()))
                .clickLogo()
                .clickNewItem()
                .setItemName(freestyle2)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FREESTYLE_PROJECT, new FreestyleConfigPage(getDriver()))
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