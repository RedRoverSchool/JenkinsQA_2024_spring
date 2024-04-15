package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem4Test extends BaseTest {
    final String newItemURL = "http://localhost:8080/view/all/newJob";
    final String newItemButton = "//a[@href='/view/all/newJob']";

    @Test
    public void testEnterNewItemPageFromLeftPanel(){
        getDriver().findElement(By.xpath(newItemButton)).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), newItemURL);
    }

    @Test
    public void testEnterNewItemPageFromWelcomeBlock(){
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:8080/newJob");
    }
}