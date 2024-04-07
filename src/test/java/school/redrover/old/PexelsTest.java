package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;


@Ignore
public class PexelsTest {

    @Test
    public void testPexels() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.pexels.com/");

        WebElement text = driver.findElement(By.id("search"));
        text.sendKeys("airplane");

        Thread.sleep(1000);

        WebElement button = driver.findElement(By.className("SearchInput_button__dQPLk"));
        button.click();

        Thread.sleep(1000);

        String link = driver.getCurrentUrl();


        Assert.assertEquals(link, "https://www.pexels.com/search/airplane/");

        driver.quit();
    }
}
