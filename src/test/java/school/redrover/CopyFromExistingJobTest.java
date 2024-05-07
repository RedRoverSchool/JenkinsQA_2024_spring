package school.redrover;

import org.openqa.selenium.WebElement;
import school.redrover.model.CreateItemPage;
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
        public void testDropdownMenuContent() throws InterruptedException {
            TestUtils.createNewJob(this, Job.PIPELINE, "ppp");
            TestUtils.createNewJob(this, Job.FREESTYLE, "folff");
            TestUtils.createNewJob(this, Job.FOLDER, "Folder1");

            String firstLetters ="foL";
            List<WebElement> allExistingJobs = getDriver().findElements(By.cssSelector("a.jenkins-table__link.model-link.inside"));
            List<String> allExistingJobsNames = TestUtils.getTexts(allExistingJobs);

            List<String> firstLettersJobs = allExistingJobsNames
                        .stream()
                        .filter(el-> el.substring(0,firstLetters.length()).equalsIgnoreCase(firstLetters))
                        .toList();

            getDriver().findElement(By.cssSelector("a[href$='newJob']")).click();
            getDriver().findElement(By.id("name")).sendKeys("someName");
            getDriver().findElement(By.id("from")).sendKeys(firstLetters);

            Thread.sleep(3000);

            ArrayList<String> allJobFromThisLetterName = new ArrayList<>();
            List<WebElement> allJobFromThisLetter = getDriver().findElements(By.xpath("//input[@id='from']/ following-sibling::div//li"));

            for (WebElement el : allJobFromThisLetter) {
                if(!el.getText().isEmpty()){
                    allJobFromThisLetterName.add(el.getText());
                }
           }

            Assert.assertEquals(allJobFromThisLetterName,firstLettersJobs);
        }
    }