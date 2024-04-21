package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
}
