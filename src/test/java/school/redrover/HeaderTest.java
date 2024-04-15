package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class HeaderTest extends BaseTest {

    @Test
    public void testLogoJenkins() {

    getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();
    getDriver().findElement(By.id("jenkins-name-icon")).click();

    Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Welcome to Jenkins!");
    }
}
