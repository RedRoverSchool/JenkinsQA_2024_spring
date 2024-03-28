package school.redrover;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FTest {
    @Test
    public void TestTest() throws InterruptedException {


        WebDriver driver = new ChromeDriver();
        driver.get("https://exp86.ru");

        Thread.sleep(1000);

        WebElement text = driver.findElement(By.xpath("//input[@type='text']"));
        text.sendKeys("Привет мир!");

        WebElement buttonSearch = driver.findElement(By.className("searh_btn"));
        buttonSearch.click();

        Thread.sleep(1000);

        WebElement button = driver.findElement(By.xpath("//a[@href='/user/']"));
        button.click();

        WebElement string = driver.findElement(By.className("form_default_star"));
        String result = string.getText();

        Assert.assertEquals("* обязательные поля для заполнения", result);

        driver.quit();


    }
}
