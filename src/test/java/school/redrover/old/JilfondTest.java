package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class JilfondTest {

    @Test
    public void testTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://jilfond.ru/");

        WebElement text = driver.findElement(By.name("price[to]"));
        text.sendKeys("5000");

        WebElement button = driver.findElement(By.id("js-search-map"));
        button.click();

    }
}
