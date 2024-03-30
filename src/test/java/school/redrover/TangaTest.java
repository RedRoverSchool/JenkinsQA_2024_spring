package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class TangaTest {
    @Test
    public void tangaTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.tanga.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        WebElement textBox = driver.findElement(By.name("q"));
        textBox.sendKeys("5959595959");
//        textBox.sendKeys(Keys.ENTER);

        WebElement button = driver.findElement(By.xpath("//button[@aria-label='Search']"));
        button.click();

        WebElement message = driver.findElement(By.xpath("//h3"));
        String value = message.getText();

        Assert.assertEquals("No products found", value);

        driver.quit();

    }
}
