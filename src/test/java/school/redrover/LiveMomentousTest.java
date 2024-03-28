package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LiveMomentousTest {

    @Test
    public void testLiveMomentousStore() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.livemomentous.com/");

        WebElement menuButton = driver.findElement(By.xpath("//button[contains(@class, 'mobile-menu-trigger')]"));
        menuButton.click();

        WebElement bestSellingItems = driver.findElement(By.xpath("//li[contains(@class, 'navigation-list')]/a[@href = '/collections/best-sellers']"));
        bestSellingItems.click();

        WebElement rejectCookiesButton = driver.findElement(By.xpath("//div[@aria-label='close']"));
        rejectCookiesButton.click();

        WebElement item = driver.findElement(By.xpath("//h3[text() = 'Creatine']"));
        Assert.assertTrue(item.isDisplayed());

        driver.quit();
    }
}
