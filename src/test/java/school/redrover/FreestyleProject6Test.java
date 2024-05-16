package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject6Test extends BaseTest {

    final String FREESTYLE_PROJECT_NAME = "Freestyle project Sv";
    final String FREESTYLE_PROJECT_DESCRIPTION = "Some description text";
    final String FREESTYLE_PROJECT_RENAME = "Renamed Freestyle project Sv";
    final String FOLDER_NAME = "Folder SV";

    public FreestyleProjectPage createFreestyleProjectWithDescription() {

        return new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSaveButton();
    }

    public void goHome() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    public FolderProjectPage createFolder() {

        return new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton();
    }

    @Ignore
    @Test
    public void testMoveFreestyleProjectToFolder() {
        String expectedText = String.format("Full project name: %s/%s", FOLDER_NAME, FREESTYLE_PROJECT_NAME);

        createFreestyleProjectWithDescription()
                .clickLogo();

        createFolder()
                .clickLogo();

        String actualText = new HomePage(getDriver())
                .chooseCreatedFreestyleProject(FREESTYLE_PROJECT_NAME)
                .clickMove()
                .choosePath(FOLDER_NAME)
                .clickMoveButton()
                .getFullProjectPath();

        Assert.assertTrue(actualText.contains(expectedText), "The text does not contain the expected project name.");

        goHome();
        List<WebElement> freestyleProjectList = getDriver().findElements(By.xpath("//span[contains(text(), '" + FREESTYLE_PROJECT_NAME + "')]"));
        Assert.assertTrue(freestyleProjectList.isEmpty());

        getDriver().findElement(By.xpath("(//*[@class=' job-status-']/td)[3]/a")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[contains(@id,'job_')]/td[3]/a")).getText(), FREESTYLE_PROJECT_NAME);
    }

    @Ignore
    @Test
    public void testRenameFreestyleProject() {

        String os = System.getProperty("os.name").toLowerCase();
        Keys commandKey = os.contains("mac") ? Keys.COMMAND : Keys.CONTROL;

        createFreestyleProjectWithDescription();
        goHome();

        getDriver().findElement(By.cssSelector(".job-status-nobuilt > :nth-child(3) a")).click();

        getDriver().findElement(By.xpath("//a[contains(@href, 'rename')]")).click();
        getDriver().findElement(By.xpath("//*[@name='newName']")).sendKeys(commandKey + "a", Keys.DELETE);
        getDriver().findElement(By.xpath("//*[@name='newName']")).sendKeys(FREESTYLE_PROJECT_RENAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), FREESTYLE_PROJECT_RENAME);

        goHome();
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-status-nobuilt > :nth-child(3)")).getText(), FREESTYLE_PROJECT_RENAME);
    }

}
