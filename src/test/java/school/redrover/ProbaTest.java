package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ProbaTest {

    @Test
    public void testDZ() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://exp86.ru/");

        WebElement text = driver.findElement(By.name("q"));
        text.sendKeys("Hello world");


        WebElement button = driver.findElement(By.name("Поиск"));
        button.click();


        WebElement linc = driver.findElement(By.className("notetext"));
        String result = linc.getText();

        Assert.assertEquals(result, "К сожалению, на ваш поисковый запрос ничего не найдено.");

        driver.quit();
    }
}