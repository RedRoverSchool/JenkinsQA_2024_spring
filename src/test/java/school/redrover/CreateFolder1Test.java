package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateFolder1Test extends BaseTest {

    private void createNewFolder(String folderName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//label/span[text() ='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void openDashboard() {
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    @Test
    public void testNewlyCreatedFolderIsEmptyAJ() {
        final String folderName = "NewProjectFolder";
        final String thisFolderIsEmptyMessage = "This folder is empty";
        final String createAJobLinkText = "Create a job";

        createNewFolder(folderName);
        openDashboard();

        getDriver().findElement(By.linkText(folderName)).click();

        final String actualFolderName = getDriver().findElement(By.xpath("//h1")).getText();
        final String actualEmptyStateMessage = getDriver().findElement(By.xpath("//section[@class='empty-state-section']/h2")).getText();
        final WebElement newJobLink = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        final String actualNewJobLinkText = newJobLink.getText();

        Assert.assertEquals(actualFolderName, folderName);
        Assert.assertEquals(actualEmptyStateMessage, thisFolderIsEmptyMessage);
        Assert.assertEquals(actualNewJobLinkText, createAJobLinkText);
        Assert.assertTrue(newJobLink.isDisplayed(), "newJobLink is NOT displayed");
    }

    @Test
    public void testRenameFolder() {
        final String folderName = "ProjectFolder";
        final String newFolderName = "NewProjectFolder";

        createNewFolder(folderName);
        openDashboard();

        getDriver().findElement(By.xpath("//tr[@id='job_" + folderName + "']/td[3]/a/span")).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[7]/span/a")).click();

        WebElement newName = getDriver().findElement(By.xpath("//div[@class='setting-main']/input"));
        newName.clear();
        newName.sendKeys(newFolderName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        openDashboard();

        String actualResult = getDriver().findElement(By.xpath("//tr[@id='job_" + newFolderName + "']/td[3]/a/span")).getText();
        Assert.assertEquals(actualResult, newFolderName);
    }
}
