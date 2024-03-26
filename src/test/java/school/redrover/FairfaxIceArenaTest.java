package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class FairfaxIceArenaTest {
    @Test
    public void checkRedirectionProShopTest() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get("https://www.fairfaxicearena.com/");

        WebElement proShopBtn = driver.findElement(By.xpath("//a[normalize-space()='Pro Shop']"));
        proShopBtn.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.fairfaxicearena.com/pro-shop.html");

        WebElement appointmentRequestForm = driver.findElement(By.xpath("//span[contains(text(),'Pro Shop Appointment Request Form')]"));
        boolean appointmentRequestFormVisible = appointmentRequestForm.isDisplayed();
        Assert.assertTrue(appointmentRequestFormVisible);

        driver.quit();
    }
}
