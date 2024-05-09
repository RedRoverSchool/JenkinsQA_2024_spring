package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.FreestylePage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject6Test extends BaseTest {

    final String FREESTYLE_PROJECT_NAME = "Freestyle project Sv";
    final String FREESTYLE_PROJECT_DESCRIPTION = "Some description text";
    final String FREESTYLE_PROJECT_RENAME = "Renamed Freestyle project Sv";
    final String FOLDER_NAME = "Folder SV";

    public FreestylePage createFreestyleProjectWithDescription() {

        return new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .inputDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSave();
    }

    public void goHome() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    public void createFolder() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']/ancestor::span[@class='task-link-wrapper ']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class, '_folder')]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
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

    @Test
    public void testDeleteFreestyleProject() {

        createFreestyleProjectWithDescription();
        goHome();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-status-nobuilt > :nth-child(3)")).getText(), FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.cssSelector(".job-status-nobuilt > :nth-child(3) a")).click();

        getDriver().findElement(By.xpath("//a[@data-title='Delete Project']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testMoveFreestyleProjectToFolder() {
        createFreestyleProjectWithDescription();
        goHome();

        createFolder();
        goHome();

        getDriver().findElement(By.xpath("//span[contains(text(), '" + FREESTYLE_PROJECT_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'move')]")).click();

        WebElement dropDownPath = getDriver().findElement(By.xpath("//select[@name='destination']"));
        Select simpleDropDown = new Select(dropDownPath);
        simpleDropDown.selectByValue("/" + FOLDER_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String expectedText = String.format("Full project name: %s/%s", FOLDER_NAME, FREESTYLE_PROJECT_NAME);
        String actualText = getDriver().findElement(By.xpath("//div[contains(text(), 'Full project name:')]")).getText();
        Assert.assertTrue(actualText.contains(expectedText), "The text does not contain the expected project name.");

        goHome();
        List<WebElement> freestyleProjectList = getDriver().findElements(By.xpath("//span[contains(text(), '" + FREESTYLE_PROJECT_NAME + "')]"));
        Assert.assertTrue(freestyleProjectList.isEmpty());

        getDriver().findElement(By.xpath("(//*[@class=' job-status-']/td)[3]/a")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[contains(@id,'job_')]/td[3]/a")).getText(), FREESTYLE_PROJECT_NAME);
    }

}
