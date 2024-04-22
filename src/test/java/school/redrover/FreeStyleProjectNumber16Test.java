package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreeStyleProjectNumber16Test extends BaseTest {

    @Test
    public void testFreeStyleProjectExists() {
        String projectName = "New FreeStyle Project";

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//*[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//*[@type='submit']")).click();
        getDriver().findElement(By.xpath("//button[contains(@class, 'jenkins-button--primary')]")).click();
        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();

        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//span[text()='" + projectName + "']"))
                .isDisplayed());
    }
}
