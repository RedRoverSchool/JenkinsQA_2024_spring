package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject17Test extends BaseTest{
    private static final String PROJECT_NAME = "FreestyleProject Name";

    @Test
    public void testCreateNewFreestyleProject() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[contains(text(),'Freestyle project')]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/div/div/h1")).getText(), "FreestyleProject Name");
    }

}


