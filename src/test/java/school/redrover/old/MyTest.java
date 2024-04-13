package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.Keys;

public class MyTest extends BaseTest {
    @Test
    public void testGoogle() throws InterruptedException {
        getDriver().get("https://www.ucsandiegobookstore.com/");

        WebElement buttonOrder = getDriver().findElement(By.xpath(
                "//a[@class='quickorder-accesspoints-headerlink-link']"));
        buttonOrder.click();

        WebElement buttonItems = getDriver().findElement(By.xpath(
                "//input[@placeholder='Enter SKU or Item Name']"));
        buttonItems.sendKeys("9781608875634-GB"); //Harry Potter
        buttonItems.sendKeys(Keys.ENTER);

        WebElement buttonQuantity = getDriver().findElement(By.id("quantity"));
        buttonQuantity.sendKeys("1");

        WebElement buttonAddItem = getDriver().findElement(By.className("quick-add-box-button"));
        buttonAddItem.click();

        Thread.sleep(1000); // make it 500

        WebElement shoppingCart = getDriver().findElement(By.xpath("//h1[@class='cart-detailed-title']"));
        String resultText = shoppingCart.getText();

        Assert.assertEquals(resultText, "Shopping Cart");

    }
}
