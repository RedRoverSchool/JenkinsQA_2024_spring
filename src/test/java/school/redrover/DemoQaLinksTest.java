package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DemoQaLinksTest {

    @Test
    public void linkTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/links");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(300));

        driver.findElement(By.linkText("Created")).click();

        WebElement text = driver.findElement(By.cssSelector("#linkResponse > b:nth-child(1)"));
        Assert.assertEquals(text.getText(), "201");


        driver.quit();

    }
}
