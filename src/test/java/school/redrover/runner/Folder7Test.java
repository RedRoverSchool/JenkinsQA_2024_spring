package school.redrover.runner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Folder7Test extends BaseTest {

    final String OLD_NAME = "Random Folder";
    final String NEW_FOLDER_NAME = "Renamed Folder";

    public void createFolderUsingName(String name, WebDriver driver) {

        driver.findElement(By.linkText("New Item")).click();
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.name("Submit")).click();
    }

    @Test
    public void renameFolderNameViaDropdown() {

        createFolderUsingName(OLD_NAME, getDriver());

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.linkText(OLD_NAME)).click();
        getDriver().findElement(By.linkText("Rename")).click();

        getDriver().findElement(By.xpath("//*[@class='setting-main']/input")).clear();
        getDriver().findElement(By.xpath("//*[@class='setting-main']/input")).sendKeys(
                NEW_FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.linkText(NEW_FOLDER_NAME)).getText(), "Renamed Folder");
    }
}
