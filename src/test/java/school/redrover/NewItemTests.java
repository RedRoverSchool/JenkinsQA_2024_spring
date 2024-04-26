package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItemTests extends BaseTest {
    private static final By OK_BUTTON = By.xpath("//*[@id='ok-button']");
    private static final By MAIN_PAGE = By.xpath("//a[@it]");
    private static final By SAVE_BUTTON = By.xpath("//button[@formnovalidate]");
    private static final By JENKINS_INPUT = By.cssSelector("#name");

    @Test
    public void testCreatePipelineProject() {

        getDriver().findElement(MAIN_PAGE).click();

        getDriver().findElement(JENKINS_INPUT).sendKeys("Pipeline");
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();

        WebElement pipeLine = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content jenkins-build-caption']"));

        Assert.assertEquals(pipeLine.getText(), "Pipeline");
    }

    public void testCreateNewFolder() {
        final String folderName = "NewFolder";

        getDriver().findElement(MAIN_PAGE).click();

        getDriver().findElement(JENKINS_INPUT).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();

        getDriver().findElement(OK_BUTTON).click();

        getDriver().findElement(By.className("jenkins-breadcrumbs__list-item")).click();
    }
}
