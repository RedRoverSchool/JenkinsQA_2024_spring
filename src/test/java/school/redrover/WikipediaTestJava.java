package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WikipediaTestJava {
    @Test
    public void testWikipedia() throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.wikipedia.org/");
        Thread.sleep(1000);

        WebElement searchInput = driver.findElement(By.id("searchInput"));
        searchInput.sendKeys("Java (programming language)");
        searchInput.submit();
        Thread.sleep(1000);

        WebElement heading = driver.findElement(By.id("firstHeading"));
        Assert.assertTrue(heading.getText().contains("Java"));
        Thread.sleep(2000);

        driver.quit();
    }
}



