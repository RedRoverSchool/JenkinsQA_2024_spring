package school.redrover;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FreestyleProject17Test extends BaseTest {

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