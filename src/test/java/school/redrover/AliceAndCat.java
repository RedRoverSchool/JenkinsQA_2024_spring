package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AliceAndCat {
    @Test
    public void testAliceAndCat() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://aliceandcat.ru/");
        WebElement harryPotterButton = driver.findElement(By.id("menu-item-1590"));
        harryPotterButton.click();
        WebElement wizardingBooksBuy = driver.findElement(By.xpath("//*[@data-productid='1460']"));
        wizardingBooksBuy.click();
        WebElement checkoutForm = driver.findElement(By.className("woocommerce"));
        Assert.assertNotNull(checkoutForm, "Element Checkout Form is not found");

        driver.quit();
    }
}
