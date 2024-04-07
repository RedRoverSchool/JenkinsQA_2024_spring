package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.ArrayList;

@Ignore
public class PostmanTest {

    @Test
    public void testPostman() throws InterruptedException {
//  открытие браузера
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.postman.com/");
//  переход на Products - Download Postman
        WebElement productButton = driver.findElement(By.xpath("//button/span[text() = 'Product']"));
        productButton.click();
        Thread.sleep(2000);
        WebElement downloadPostman = driver.findElement(By.xpath("//button/a/div/span[text() = 'Download Postman']"));
        downloadPostman.click();
//  сравнение названия вкладки с нужным
        String title = driver.getTitle();
        Assert.assertEquals("Download Postman | Get Started for Free", title);
        Thread.sleep(2000);
//  прокрутка страницы вниз на 600 пикселей
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 600);");
        Thread.sleep(2000);
//  нажатие на Try the Web Version
        WebElement tryWeb = driver.findElement(By.xpath("//a[text() = 'Try the Web Version']"));
        tryWeb.click();
//  переход на новую автоматически открытую вкладку для выполнения действий
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());   // получение списка всех открытых вкладок
        driver.switchTo().window(tabs.get(tabs.size() - 1));   // переключение на последнюю вкладку
//  заполнение полей для регистрации и нажатие на кнопку регистрации
        Thread.sleep(2000);
        WebElement email = driver.findElement(By.xpath("//input[@id = 'email']"));
        email.sendKeys("rrehr56757rt46@mail.ru");
        Thread.sleep(1000);
        WebElement username = driver.findElement(By.xpath("//input[@id = 'username']"));
        username.sendKeys("Geofr6863104");
        Thread.sleep(1000);
        WebElement password = driver.findElement(By.xpath("//input[@id = 'password']"));
        password.sendKeys("_fhr776Yt5490)");
        Thread.sleep(1000);
        WebElement signUp = driver.findElement(By.xpath("//button[@id = 'sign-up-btn']"));
        signUp.click();
//  ожидание открытия страницы и сравнение названия вкладки с нужным
        Thread.sleep(10000);
        String title2 = driver.getTitle();
        Assert.assertEquals("Postman API Platform", title2);

        driver.quit();
    }
}
