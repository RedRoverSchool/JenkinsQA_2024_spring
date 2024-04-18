package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject16Test extends BaseTest {
    final String PROJECT_NAME = "MD first project";
    @Test
    public void testCreateFirstTest(){

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        String projectName = getDriver().findElement(By.tagName("h1")).getText();

        getDriver().findElement(By.xpath
                ("//*[@id='breadcrumbs']/li[1]")).click();
        getDriver().findElement(By.xpath
                ("//span[text()='" + PROJECT_NAME + "']")).click();
        getDriver().findElement(By.xpath
                ("//*[@id='tasks']/div[5]/span/a")).click();
        String general = getDriver().findElement(By.tagName("h2")).getText();

        Assert.assertEquals(projectName, PROJECT_NAME);
        Assert.assertEquals(general, "General");
    }
}
