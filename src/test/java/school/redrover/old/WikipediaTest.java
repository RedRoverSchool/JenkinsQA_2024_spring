package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class WikipediaTest {

    private final static String URL = "https://ru.wikipedia.org/wiki/%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0";

    @Test
    public void testSearchWord() throws InterruptedException {

        String expectedResult = "Абракадабра";

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);

        driver.findElement(By.xpath("//input[@id='searchInput']")).sendKeys("Абракадабра");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@id='searchButton']")).click();
        Thread.sleep(2000);

        String value = driver.findElement(By.xpath("//h1[@id='firstHeading']")).getText();

        Assert.assertEquals(value, expectedResult);

        driver.quit();
    }

    @Test
    public void testTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://ru.wikipedia.org/w/index.php?title=%D0%92%D0%B8%D0%BA%D0%B8%D0%BF%D0%B5%D0%B4%D0%B8%D1%8F&action=edit");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement articuiLink = driver.findElement(By.id("ca-nstab-main"));
        articuiLink.click();
        String value = driver.findElement(By.cssSelector(".mw-page-title-main")).getText();

        Assert.assertEquals(value, "Википедия");
        driver.quit();
    }
}
