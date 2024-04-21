package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreatedFeestyleProjectMATest extends BaseTest {
@Ignore
@Test
    public void testCreatedFeestyleProject() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.id("name")).sendKeys("123");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[5]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[5]/span/a")).click();

        String expectedResult = getDriver().findElement(By.xpath("//*[@id='side-panel']/div[1]/div[1]/h1")).getText();
        String actualResult = "Configure";

        Assert.assertEquals(actualResult,expectedResult);

    }
}
