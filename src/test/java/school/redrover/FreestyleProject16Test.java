package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FreestyleProject16Test extends BaseTest {
    final String PROJECT_NAME = "MD first project";
    final String NEW_PROJECT_NAME = "Rename MD first project";
    final String PROJECT_DESCRIPTION = "I like my project!";


    @Test
    public void testCreateFirstTest(){

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        String projectName = getDriver().findElement(By.tagName("h1")).getText();

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.xpath
                ("//span[text()='" + PROJECT_NAME + "']")).click();
        getDriver().findElement(By.linkText("Configure")).click();
        String general = getDriver().findElement(By.tagName("h2")).getText();

        Assert.assertEquals(projectName, PROJECT_NAME);
        Assert.assertEquals(general, "General");
    }

    @Test
    public void testAddDescription(){

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.xpath
                ("//span[text()='" + PROJECT_NAME + "']")).click();
        getDriver().findElement(By.xpath("//*[@id='description-link']")).click();
        getDriver().findElement(By.name
                ("description")).sendKeys(PROJECT_DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();

        String description = getDriver().findElement
                (By.xpath("//*[@id='description']/div[1]")).getText();

        Assert.assertEquals(description, PROJECT_DESCRIPTION);
    }

    @Test (dependsOnMethods = {"testCreateFirstTest"})
    public void testRenameFirstProject(){

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.xpath
                ("//span[text()='" + PROJECT_NAME + "']")).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();
        String newProjectName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(newProjectName, NEW_PROJECT_NAME);
    }
    @Test (dependsOnMethods = "testRenameFirstProject")
    public void testDeleteFirstProject(){

        getDriver().findElement(By.xpath
                ("//span[text()='" + NEW_PROJECT_NAME + "']")).click();
        getDriver().findElement(By.xpath
                ("//span[text()='Delete Project']")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins']/dialog/div[3]/button[1]")).click();
        getDriver().findElement(By.id("search-box")).sendKeys(NEW_PROJECT_NAME, Keys.RETURN);

        String not_found = getDriver().findElement(By.xpath("//*[@id='main-panel']/div")).getText();

        Assert.assertEquals(not_found, "Nothing seems to match.");
    }

    @Test
    public void testCreateFreestyleProject() {
        final String freestyleProjectName = "FreestyleProjectTest";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']"))
                .click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        String actualFreestyleName = getDriver()
                .findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText();
        Assert.assertEquals(actualFreestyleName, freestyleProjectName);

    }
}
