package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder8Test extends BaseTest {
    public void create() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Folder");
        getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testRename() {
        create();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td/a[@href='job/Folder/']"))).click();
        getDriver().findElement(By.xpath("//a[@href='/job/Folder/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys("NewFolder");
        getWait2().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name='Submit']"))).click();

        String result = getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText();
        Assert.assertEquals(result, "NewFolder");
    }
}