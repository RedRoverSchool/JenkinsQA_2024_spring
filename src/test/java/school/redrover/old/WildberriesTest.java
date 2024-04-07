package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class WildberriesTest {
    @Test
    public void testWild() throws InterruptedException {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.wildberries.ru/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        WebElement profilButton = driver.findElement(By.xpath("//*[@id='basketContent']/div[2]/a"));
        profilButton.click();

        WebElement phoneInput = driver.findElement(By.xpath("//input[@class='input-item']"));
        phoneInput.sendKeys("89675645342");

        WebElement kodButton = driver.findElement(By.id("requestCode"));
        kodButton.click();

        WebElement messageText = driver.findElement(By.xpath("//*[@class='form-block form-block--captcha']//*[@class='sign-in-page__title']"));
        Assert.assertEquals("Введите код с картинки", messageText.getText());

        driver.quit();
    }
}
