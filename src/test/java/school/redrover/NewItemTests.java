package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItemTests extends BaseTest {
    private static final By OK_BUTTON = By.xpath("//*[@id='ok-button']");
    private static final By MAIN_PAGE = By.xpath("//a[@it]");
    private static final By SAVE_BUTTON = By.xpath("//button[@formnovalidate]");
    private static final By JENKINS_INPUT = By.cssSelector("#name");
    private static final By JENKINS_DASHBOARD = By.className("jenkins-breadcrumbs__list-item");

    @Test
    public void testCreateFreestyleProject() {
        getDriver().findElement(MAIN_PAGE).click();

        getDriver().findElement(JENKINS_INPUT).sendKeys("newFreestyleProject");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(OK_BUTTON).click();

        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertEquals((getDriver().findElement(By.xpath("//h1")).getText()),"newFreestyleProject");
    }

    @Test
    public void testCreatePipelineProject() {

        getDriver().findElement(MAIN_PAGE).click();

        getDriver().findElement(JENKINS_INPUT).sendKeys("Pipeline");
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();

        WebElement pipeLine = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content jenkins-build-caption']"));

        Assert.assertEquals(pipeLine.getText(),"Pipeline");
    }

    @Test
    public void testCreateNewFolder() {
        getDriver().findElement(MAIN_PAGE).click();

        getDriver().findElement(JENKINS_INPUT).sendKeys("NewFolder");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();

        getDriver().findElement(OK_BUTTON).click();

        getDriver().findElement(By.className("jenkins-breadcrumbs__list-item")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//td/*[@href='job/NewFolder/']")).getText(),
                "NewFolder");
    }

    @Test
    public void testFreestyleMovetoFolder() {

        testCreateFreestyleProject();

        getDriver().findElement(By.xpath("//li/*[@href='/']")).click();

        testCreateNewFolder();

        getDriver().findElement(JENKINS_DASHBOARD).click();

        getDriver().findElement(By.id("job_newFreestyleProject")).click();

        getDriver().findElement(By.xpath("//td/a[@href='job/newFreestyleProject/']")).click();

        getDriver().findElement(By.xpath("//*[@href='/job/newFreestyleProject/move']")).click();

        WebElement element = getDriver().findElement(By.name("destination"));
        Select select = new Select(element);
        select.selectByValue("/NewFolder");
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(JENKINS_DASHBOARD).click();

        getDriver().findElement(By.xpath("//td/*[@href]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[text()='newFreestyleProject']")).getText(),
                "newFreestyleProject");
    }
}
