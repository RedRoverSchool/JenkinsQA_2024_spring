package school.redrover;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Folder5Test extends BaseTest {
    public void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(folderName);

        getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    @Test
    public void deleteFolderTest() {
        final String folderName = "DeleteFolder";

        createFolder(folderName);

        getDriver().findElement(By.xpath("//table//a[@href='job/" + folderName + "/']")).click();

        getDriver().findElement(By.xpath("//a[@data-title='Delete Folder']")).click();

        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        List<WebElement> jobList = getDriver().findElements(
                By.xpath("//table//a[@href='job/" + folderName +"/']"));

        Assert.assertTrue(jobList.isEmpty());
    }
    @Test
    public void deleteFolderFromDropDownTest() {
        final String folderName = "DeleteFolderFromDropDown";

        createFolder(folderName);

        getDriver().findElement(By.xpath("//table//a[@href='job/" + folderName + "/']"));

        WebElement button = getDriver().findElement(By.xpath("//table//button[@class='jenkins-menu-dropdown-chevron']"));
        // Thread.sleep(200);
        //button.click();
        Actions actions = new Actions(getDriver());
        actions.moveToElement(button).build().perform();
        button.click();

        WebElement menuContainer = getDriver().findElement(By.xpath("//div[@id='tippy-5']"));
        WebElement targetLine = getDriver().findElement(By.xpath("//body//button[@class='jenkins-dropdown__item']"));
        actions.moveToElement(menuContainer).moveToElement(targetLine).build().perform();
        targetLine.click();

        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        List<WebElement> jobList = getDriver()
                .findElements(By.xpath("//table//a[@href='job/" + folderName +"/']"));

        Assert.assertTrue(jobList.isEmpty());
    }
}