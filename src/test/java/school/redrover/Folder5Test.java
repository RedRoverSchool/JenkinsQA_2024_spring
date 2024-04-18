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

        WebElement element1 = getDriver().findElement(By.xpath("//span[.='DeleteFolderFromDropDown']"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element1).perform();

        getDriver().findElement(By.xpath("//tr[@id='job_DeleteFolderFromDropDown']//button[@class='jenkins-menu-dropdown-chevron']")).click();

        getDriver().findElement(By.cssSelector("[href='/job/DeleteFolderFromDropDown/doDelete']")).click();

        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(), "Welcome to Jenkins!");
    }
}
