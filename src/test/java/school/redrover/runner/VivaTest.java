package school.redrover.runner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;
import java.time.Duration;

public class VivaTest extends BaseTest {
    @Test
    public void testTest() throws InterruptedException {

        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        getDriver().get("https://www.yrt.ca/en/schedules-and-maps/viva-routes.aspx");


        //WebElement textBox = getDriver().findElement(By.name("search")).sendKeys("ffff");
        getDriver().findElement(By.name("search")).sendKeys("ffff");
        getDriver().findElement(By.name("search")).clear();;

        Thread.sleep(1000);
        WebElement submitButton = getDriver().findElement(By.linkText("Schedules and Maps"));


        String currentUrl = getDriver().getCurrentUrl();

        Assert.assertEquals(currentUrl, "https://www.yrt.ca/en/schedules-and-maps/viva-routes.aspx");


    }
}
