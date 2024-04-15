package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject6Test extends BaseTest {

    final String PROJECT_NAME = "Freestyle project Sv";
    final String PROJECT_DESCRIPTION = "Some description text";

    @Test
    public void testCreateFreestyleProjectWithDescription() {
        getDriver().findElement(By.xpath("//*[@href='newJob']")).click();

        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//*[@name='description']")).sendKeys(PROJECT_DESCRIPTION);
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), PROJECT_NAME);
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description > div:first-child")).getText(), PROJECT_DESCRIPTION);

        getDriver().findElement(By.id("jenkins-home-link")).click();
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-status-nobuilt > :nth-child(3)")).getText(), PROJECT_NAME);
    }
}
