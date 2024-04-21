package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject13Test extends BaseTest {

    @Ignore
    @Test
    private void testNewFreestyleProjectCreated(){
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("Freestyle1");
        getDriver().findElement(By.xpath("//label/span[text() ='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(@class, 'job-index-headline page-headline')]")).getText(),
                "Freestyle1");

    }
}
