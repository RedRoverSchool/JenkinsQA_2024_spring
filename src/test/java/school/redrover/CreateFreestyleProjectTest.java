package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateFreestyleProjectTest extends BaseTest {

    @Test
    public void testCreateFreestyleProject() {
        final String expectedProjectName = "new Freestyle project";

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).clear();
        getDriver().findElement(By.id("name")).sendKeys(expectedProjectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        String newProjectName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(newProjectName, expectedProjectName);

    }
}
