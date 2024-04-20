package school.redrover;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FreestyleProject17Test extends BaseTest {

    @Test(dependsOnMethods = "testCreateNewFreestyleProject")
    public  void testAddDescription() {

        getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject%20Name/']/span")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/FreestyleProject%20Name/configure']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("Description of " + PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description>:first-child")).getText(),"Description of " + PROJECT_NAME);
    }

}

    private final String JOB_NAME = "YS_jenkins_ui";


    @Test
    void createNewFreestyleProjectTest() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(JOB_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("[type='submit']")).click();
        getDriver().findElement(By.cssSelector("[name='description']"))
                .sendKeys("This test is trying to create a new freestyle job");
        getDriver().findElement(By.cssSelector("[name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), JOB_NAME);
    }
}