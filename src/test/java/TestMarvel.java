import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestMarvel {
    @Test
    public void testMarvel() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.marvel.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement buttonAccept= driver.findElement(By.id("onetrust-accept-btn-handler"));
        buttonAccept.click();

        WebElement buttonSearch= driver.findElement(By.id("search"));
        buttonSearch.click();

        WebElement text = driver.findElement(By.className("typeahead__input"));
        text.sendKeys("Deadpool");

        text.sendKeys(Keys.ENTER);

        Thread.sleep(1000);

        WebElement link= driver.findElement(By.xpath ("//a[text()='Deadpool (Wade Wilson)'][@href='/characters/deadpool-wade-wilson']"));
        String resultText = link.getText();

        Assert.assertEquals(resultText, "Deadpool (Wade Wilson)");

        driver.quit();
    }
}
