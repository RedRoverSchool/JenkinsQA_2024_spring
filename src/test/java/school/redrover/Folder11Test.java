package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class Folder11Test extends BaseTest {
    @Test
    public void testCreateFolder(){
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("Folder_1");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        TestUtils.returnToDashBoard(this);
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id=\"job_Folder_1\"]/td[3]/a/span")).getText(),
                "Folder_1");
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void testDeleteFolder() {
        getDriver().findElement(By.xpath("//*[@id=\"job_Folder_1\"]/td[3]/a/span")).click();
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[4]/span/a/span[2]")).click();
        getDriver().findElement(By.xpath("//*[@id=\"jenkins\"]/dialog/div[3]/button[1]")).click();

        boolean folderIsDisplayed;
        try {
            WebElement folder = getDriver().findElement(By.xpath("//*[@id=\"job_Folder_1\"]/td[3]/a/span"));
            folderIsDisplayed = folder.isDisplayed();
        } catch (NoSuchElementException e) {
            folderIsDisplayed = false;
        }
        Assert.assertFalse(folderIsDisplayed);
    }
}
