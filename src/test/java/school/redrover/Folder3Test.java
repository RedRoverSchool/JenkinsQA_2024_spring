package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class Folder3Test extends BaseTest {

    private static final String FOLDER_NAME = "Folder_1";

    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//*[text()='New Item']/ancestor::div[contains(@class,'task')]")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.xpath("//*[text()='Folder']/ancestor::li")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();

        TestUtils.returnToDashBoard(this);
        boolean isFolderExists = !getDriver().findElements(By.xpath("//*[@id='job_" + FOLDER_NAME + "']")).isEmpty();

        Assert.assertTrue(isFolderExists);
    }

    @Test (dependsOnMethods = "testCreate")
    public void testRename() {
        String folderNameNew = "Folder_1_New";

        getDriver().findElement(By.xpath("//a[@href='job/" + FOLDER_NAME + "/' and span]/span")).click();
        getDriver().findElement(By.xpath("//*[text()='Rename']/parent::a")).click();

        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(folderNameNew);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        TestUtils.returnToDashBoard(this);

        boolean isFolderWithNewNameExists =
                !getDriver().findElements(By.xpath("//*[@id='job_" + folderNameNew + "']")).isEmpty();

        boolean isFolderWithOldNameNotExists =
                getDriver().findElements(By.xpath("//*[@id='job_" + FOLDER_NAME + "']")).isEmpty();

        Assert.assertTrue(isFolderWithNewNameExists && isFolderWithOldNameNotExists);
    }

}
