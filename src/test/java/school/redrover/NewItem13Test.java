package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem13Test extends BaseTest {

    @Ignore
    @Test
    public void testCreateFreestyleProject() {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("MyNewProject");
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "MyNewProject");
    }
}
