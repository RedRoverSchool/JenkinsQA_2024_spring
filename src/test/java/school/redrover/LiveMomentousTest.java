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

    @Test
    public void testNobullSearch() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.nobullproject.com/");

        String title = driver.getTitle();
        Assert.assertEquals(title, "NOBULL Training Shoes, Apparel, and Accessories.");

        WebElement closeCookies = driver.findElement(By.id("onetrust-close-btn-container"));
        closeCookies.click();

        WebElement searchButton = driver.findElement(By.xpath("//*[@data-target = 'search-button']"));
        searchButton.click();

        WebElement floatingSearchField = driver.findElement(By.xpath("//input[@name = 'q']"));
        floatingSearchField.sendKeys("Tank");

        WebElement searchButtonOnFloatingField = driver.findElement(By.xpath("//button[@class = 'text-black'][1]"));
        searchButtonOnFloatingField.click();

        Thread.sleep(8000);

        driver.switchTo().frame("attentive_creative");
        WebElement discountPopUp = driver.findElement(By.id("closeIconContainer"));
        discountPopUp.click();

        driver.switchTo().defaultContent();

        WebElement searchResult = driver.findElement(By.xpath("//span[@class = 'ss__query']"));
        String value = searchResult.getText();
        Assert.assertEquals(value, "TANK");

        driver.quit();
    }
}
