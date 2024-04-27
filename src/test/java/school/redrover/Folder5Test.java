package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
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
    public void testDeleteFromDropDown() {
        final String folderName = "DeleteFromDropDown";

        createFolder(folderName);

        getDriver().findElement(By.xpath("//table//a[@href='job/" + folderName + "/']"));

        WebElement dropdown = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//td//button[@class='jenkins-menu-dropdown-chevron']")));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dropdown, -(695 - dropdown.getLocation().getX()), -(309 - dropdown.getLocation().getY())).build().perform();
        dropdown.click();

        WebElement targetLine = getDriver().findElement(By.cssSelector("[href='/job/DeleteFromDropDown/doDelete']"));
        Actions actions1 = new Actions(getDriver());
        actions1.moveToElement(targetLine).build().perform();
        targetLine.click();

        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        List<WebElement> jobList = getDriver().findElements(
                By.xpath("//span[.='Organization Folder']"));
        Assert.assertTrue(jobList.isEmpty());
    }
}
