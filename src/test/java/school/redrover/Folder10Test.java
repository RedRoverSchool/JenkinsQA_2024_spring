package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder10Test extends BaseTest {

    final String FOLDER_NAME = "Folder SV";
    final String PIPELINE_NAME = "Pipeline Sv";

    public void goHome() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    public void createFolder() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']/ancestor::span[@class='task-link-wrapper ']")).click();

        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class, '_folder')]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    @Test
    public void testCreateFolder() {
        createFolder();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), FOLDER_NAME);

        goHome();
        Assert.assertEquals(getDriver().findElement(By.xpath("(//*[@class=' job-status-']/td)[3]")).getText(), FOLDER_NAME);
    }

    @Test(dependsOnMethods = ("testCreateFolder"))
    public void testCreateJobPipelineInFolder() {
        getDriver().findElement(By.xpath("(//*[@class=' job-status-']/td)[3]/a")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'newJob')]/ancestor::span[@class='task-link-wrapper ']")).click();

        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[contains(@class, 'label') and normalize-space(text())='Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String expectedText = String.format("Full project name: %s/%s", FOLDER_NAME, PIPELINE_NAME);
        String actualText = getDriver().findElement(By.xpath("//div[contains(text(), 'Full project name:')]")).getText();
        Assert.assertTrue(actualText.contains(expectedText), "The text does not contain the expected project name.");

        goHome();
        getDriver().findElement(By.xpath("(//*[@class=' job-status-']/td)[3]/a")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[contains(@id,'job_')]/td[3]/a")).getText(), PIPELINE_NAME);

    }

}
