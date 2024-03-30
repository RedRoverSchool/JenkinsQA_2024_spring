package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTheInternetHerokuapp {
    @Test
    public void testPage() throws InterruptedException{
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com");

        WebElement link = driver.findElement(By.linkText("JavaScript Alerts"));
        link.click();

        Thread.sleep(500);

        WebElement jsalert = driver.findElement(By.xpath("//button[.='Click for JS Alert']"));
        jsalert.click();
        driver.switchTo().alert().accept();

        String result = driver.findElement(By.id("result")).getText();
        Assert.assertEquals(result,"You successfully clicked an alert");

        driver.quit();
    }
}
