package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem7Test extends BaseTest {
    @Test
    public void testVerifyCreateNewItemPage() {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();

        String actualResult = getDriver().findElement(By.xpath("//*[@class='h3']")).getText();

        Assert.assertEquals(actualResult, "Enter an item name");
    }
}
