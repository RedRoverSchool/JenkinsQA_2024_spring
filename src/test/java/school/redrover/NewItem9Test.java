package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem9Test extends BaseTest {
    
    @Test
    public void testGoToNewJobPage() {

        getDriver().findElement(By.linkText("New Item")).click();

       WebElement title = getDriver().findElement(By.xpath("//*[text()='Enter an item name']"));

        Assert.assertTrue(title.isDisplayed());

    }

}
