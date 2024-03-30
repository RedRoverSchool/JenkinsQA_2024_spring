package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MagentoTest {

    @Test
    public void testAlertAppearsAfterRatingIsNotSelected() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://magento.softwaretestingboard.com");

        WebElement element = driver.findElement(By
                .xpath("//span[contains (.,'Women')]"));
        element.click();

        driver.findElement(By
                .xpath("//div[contains(@class, 'categories')]//a[contains(., 'Hoodies')]")).click();

        driver.findElement(By
                .xpath("//a[contains(., 'Circe')]")).click();

        driver.findElement(By
                .xpath("//a[normalize-space(.) = 'Reviews']")).click();

        driver.findElement(By.id("nickname_field")).sendKeys("Chubaka");

        driver.findElement(By.id("summary_field")).sendKeys("My f***ing sh**ty review");

        driver.findElement(By.id("review_field")).sendKeys("bla-bla-bla");

        driver.findElement(By
                .xpath("//button[normalize-space(.) = 'Submit Review']")).click();

        WebElement alert = driver.findElement(By.id("ratings[4]-error"));

        Assert.assertTrue(alert.isDisplayed());
        Assert.assertEquals(alert.getText(), "Please select one of each of the ratings above.");

        driver.quit();
    }
}
