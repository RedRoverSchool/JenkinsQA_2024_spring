package school.redrover;

import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.*;
import school.redrover.runner.*;

public class FreestyleProjectTest extends BaseTest {

    @Test
    public void testCreateFreestyleProjectJob() {
        String expectedHeading = "My First Freestyle project";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(expectedHeading);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        String actualHeading = getDriver()
                .findElement(By.xpath("//table//a[@href='job/My%20First%20Freestyle%20project/']")).getText();

        Assert.assertEquals(actualHeading, expectedHeading);
    }

}
