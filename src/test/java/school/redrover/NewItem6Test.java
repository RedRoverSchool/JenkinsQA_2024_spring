package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem6Test extends BaseTest {
    private final String FOLDER_NAME = "MyFolder";
    private final String NEW_FOLDER_NAME = "Your name";

    @Test
    public void testCreateNewFolder(){
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Creates a container')]")));

        getDriver().findElement(By.xpath("//div[contains(text(),'Creates a container')]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='Submit']"))).click();

        String actualFolderName =  getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='main-panel']/h1"))).getText();

        Assert.assertEquals(actualFolderName, FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewFolder")
    public void testNewFolderOnDashboard(){
        getDriver().findElement(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']")).click();

        String actualNewFolderName = getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='jenkins-table__link model-link inside']"))).getText();

        Assert.assertEquals(actualNewFolderName,FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testNewFolderOnDashboard")
    public void testRenameFolder(){
        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).click();

        getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='tasks']//*[contains(@href,'confirm-rename')]"))).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName"))).clear();

        getDriver().findElement(By.name("newName")).sendKeys(NEW_FOLDER_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String actualFolderNewName =  getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1"))).getText();

        Assert.assertEquals(actualFolderNewName,NEW_FOLDER_NAME);
    }
}