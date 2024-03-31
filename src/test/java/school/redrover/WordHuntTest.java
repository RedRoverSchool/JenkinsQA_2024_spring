package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class WordHuntTest extends BaseTest {
    @Test
    public void wooordHunttest(){
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get("https://wooordhunt.ru/");

        WebElement inputBox = driver.findElement(By.id("hunted_word"));
        inputBox.sendKeys("apple");

        WebElement submitButton = driver.findElement(By.id("hunted_word_submit"));
        submitButton.click();

        WebElement translation = driver.findElement(By.className("t_inline_en"));
        String value = translation.getText();

        Assert.assertEquals("яблоко, яблоня, чепуха, лесть, яблочный", value);
        driver.quit();

    }
}
