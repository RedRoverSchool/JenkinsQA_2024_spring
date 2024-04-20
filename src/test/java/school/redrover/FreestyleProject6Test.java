package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject6Test extends BaseTest {

    final String PROJECT_NAME = "Freestyle project Sv";
    final String PROJECT_DESCRIPTION = "Some description text";
    final String PROJECT_RENAME = "Renamed Freestyle project Sv";

    public void createFreestyleProjectWithDescription() {
        getDriver().findElement(By.xpath("//*[@href='newJob']")).click();

        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//*[@name='description']")).sendKeys(PROJECT_DESCRIPTION);
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();
    }

    public void goHome() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    @Ignore
    @Test
    public void testCreateFreestyleProjectWithDescription() {

        createFreestyleProjectWithDescription();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), PROJECT_NAME);
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description > div:first-child")).getText(), PROJECT_DESCRIPTION);

        goHome();
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-status-nobuilt > :nth-child(3)")).getText(), PROJECT_NAME);
    }

    @Test
    public void testRenameFreestyleProject() {

        String os = System.getProperty("os.name").toLowerCase();
        Keys commandKey = os.contains("mac") ? Keys.COMMAND : Keys.CONTROL;

        createFreestyleProjectWithDescription();
        goHome();

        getDriver().findElement(By.cssSelector(".job-status-nobuilt > :nth-child(3) a")).click();

        getDriver().findElement(By.xpath("//a[contains(@href, 'rename')]")).click();
        getDriver().findElement(By.xpath("//*[@name='newName']")).sendKeys(commandKey + "a", Keys.DELETE);
        getDriver().findElement(By.xpath("//*[@name='newName']")).sendKeys(PROJECT_RENAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), PROJECT_RENAME);

        goHome();
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-status-nobuilt > :nth-child(3)")).getText(), PROJECT_RENAME);
    }

    @Test
    public void testDeleteFreestyleProject() {

        createFreestyleProjectWithDescription();
        goHome();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-status-nobuilt > :nth-child(3)")).getText(), PROJECT_NAME);
        getDriver().findElement(By.cssSelector(".job-status-nobuilt > :nth-child(3) a")).click();

        getDriver().findElement(By.xpath("//a[@data-title='Delete Project']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Welcome to Jenkins!");
    }

}
