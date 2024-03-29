package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class SauceCheckAmountItemsTest extends SauceSuccessLoginTest{
    @Test
    public void testCheckAmountItems() throws InterruptedException {
        int expectedResult = 6;
        login();
        Thread.sleep(1000);
        List<WebElement> items = driver.findElements(By.xpath("//div[@class='inventory_item']"));
        int actualResult = items.size();
        Assert.assertEquals(actualResult, expectedResult);
    }
}