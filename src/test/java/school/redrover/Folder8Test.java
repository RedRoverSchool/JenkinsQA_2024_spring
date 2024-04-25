package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder8Test extends BaseTest {
    public void createFolder() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Folder");
        getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Ignore
    @Test
    public void testRenameFolder() {
        createFolder();
        Actions actions = new Actions(getDriver());

        getDriver().findElement(By.xpath("//td/a[@href='job/Folder/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/Folder/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys("NewFolder");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String result = getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText();
        Assert.assertEquals(result, "NewFolder");
    }
}