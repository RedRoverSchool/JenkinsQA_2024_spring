package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem15Test extends BaseTest {
    @Test
    public void testNewItem15Test(){
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1)> span > a")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys("New Folder");
        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-nested-projects\"]/ul/li[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[2]/div/div[2]/input"))
                .sendKeys("Example");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(), "Example");

    }
}
