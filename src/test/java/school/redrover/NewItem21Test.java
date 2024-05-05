package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem21Test extends BaseTest {

    private final String newFolderName = "New Folder" ;
    private final String pipeline = "Pipeline";

    @Test
            public void testCreateFolder(){

        getDriver().findElement(
                By.xpath("//a[contains(@class,'task-link-no-confirm ')][@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(newFolderName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'com_cloudbees_hudson')]")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']")).click();

        WebElement newFolderElement = getDriver().findElement(
                By.xpath("//a[@href='job/New%20Folder/'][contains(@class,'jenkins-table__link')]"));
        Assert.assertTrue(newFolderElement.isDisplayed());
    }

    @Test (dependsOnMethods = "testCreateFolder")

    public void testVerifyFolderIsEmpty(){

        WebElement newFolderElement = getDriver().findElement(
                By.xpath("//a[@href='job/New%20Folder/'][contains(@class,'jenkins-table__link')]"));
        newFolderElement.click();

        String actualResult = getDriver().findElement(By.xpath("//h2[text()='This folder is empty']")).getText();
        Assert.assertEquals(actualResult,"This folder is empty");
    }

    @Test (dependsOnMethods = "testVerifyFolderIsEmpty")

    public void testVerifyNewItemCanBeMovedToFloder() {

        getDriver().findElement(
                By.xpath("//a[contains(@class,'task-link-no-confirm ')][@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(pipeline);

        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/Pipeline/move']")).click();

        getDriver().findElement(By.xpath("//select")).click();

        getDriver().findElement(By.xpath("//option[@value='/New Folder']")).click();

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[@class='model-link'][@href='/job/New%20Folder/']")).click();

        WebElement pipelineItem = getDriver().findElement(
                By.xpath("//a[@href='job/Pipeline/'][contains(@class,'jenkins-table__link')]"));
        Assert.assertTrue(pipelineItem.isDisplayed());
    }

}
