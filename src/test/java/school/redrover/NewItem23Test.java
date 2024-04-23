package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem23Test extends BaseTest {


    @Test
    public void testCreateNewItem() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("Test project");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.className("page-headline")).getText(), "Test project");
    }
}
