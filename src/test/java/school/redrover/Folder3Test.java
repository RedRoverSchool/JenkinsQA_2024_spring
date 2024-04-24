package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class Folder3Test extends BaseTest {

    private static final String FOLDER_NAME_FIRST = "Folder_1";
    private static final String FOLDER_NAME_NEW = "Folder_1_New";
    private static final String FOLDER_DESCRIPTION_FIRST = "Some description of the folder.";

    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//*[text()='New Item']/ancestor::div[contains(@class,'task')]")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(FOLDER_NAME_FIRST);
        getDriver().findElement(By.xpath("//*[text()='Folder']/ancestor::li")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();

        TestUtils.returnToDashBoard(this);
        boolean isFolderExists = !getDriver().findElements(By.xpath("//*[@id='job_" + FOLDER_NAME_FIRST + "']")).isEmpty();

        Assert.assertTrue(isFolderExists);
    }

    @Test (dependsOnMethods = "testCreate")
    public void testAddDescription() {
        getDriver().findElement(By.xpath("//a[@href='job/" + FOLDER_NAME_FIRST + "/' and span]/span")).click();
        getDriver().findElement(By.xpath("//*[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(FOLDER_DESCRIPTION_FIRST);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String textInDescription = getDriver().findElement(By.xpath("//*[@id='description']/div")).getText();

        Assert.assertEquals(textInDescription, FOLDER_DESCRIPTION_FIRST);
    }

    @Test (dependsOnMethods = "testAddDescription")
    public void testRename() {

        getDriver().findElement(By.xpath("//a[@href='job/" + FOLDER_NAME_FIRST + "/' and span]/span")).click();
        getDriver().findElement(By.xpath("//*[text()='Rename']/parent::a")).click();

        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(FOLDER_NAME_NEW);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        TestUtils.returnToDashBoard(this);

        boolean isFolderWithNewNameExists =
                !getDriver().findElements(By.xpath("//*[@id='job_" + FOLDER_NAME_NEW + "']")).isEmpty();

        boolean isFolderWithOldNameNotExists =
                getDriver().findElements(By.xpath("//*[@id='job_" + FOLDER_NAME_FIRST + "']")).isEmpty();

        Assert.assertTrue(isFolderWithNewNameExists && isFolderWithOldNameNotExists);
    }

}
