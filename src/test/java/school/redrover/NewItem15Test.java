package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem15Test extends BaseTest {

    @Test
    public void testNewFolderCreating(){
        getDriver().findElement(By.cssSelector("a[href$=\"/newJob\"]")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys("New Folder");
        getDriver().findElement(By.xpath("//li[contains(@class, '_Folder')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("h1")).getText(), "New Folder");
    }
}
