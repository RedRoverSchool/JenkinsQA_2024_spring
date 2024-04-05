package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class DemowebshopTest {
    @Test
    public void testDemowebshop() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demowebshop.tricentis.com/");

        WebElement text = driver.findElement(By.id("small-searchterms"));
        text.sendKeys("computer");

        Thread.sleep(1000);  //ждет секунду

        WebElement button = driver.findElement(By.xpath("//input[@class='button-1 search-box-button']"));
        button.click();
        Thread.sleep(2000);

        WebElement SearchKeyword = driver.findElement(By.cssSelector(".page-title"));
        String resultText = SearchKeyword.getText();



        Assert.assertEquals(resultText, "Search");

        driver.quit();
    }

    @Test
    public void testDemowebshop1() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demowebshop.tricentis.com/");

        WebElement button = driver.findElement(By.xpath("//a[@href = '/computers']"));
        button.click();
        Thread.sleep(2000);

        WebElement SearchKeyword = driver.findElement(By.cssSelector(".page-title"));
        String resultText = SearchKeyword.getText();

        Assert.assertEquals(resultText, "Computers");

        driver.quit();
    }

    @Test
    public void testDemowebshop2() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demowebshop.tricentis.com/");

        WebElement button = driver.findElement(By.xpath("//ul[@class='list' ]//li[@class='inactive']//a[@href='/computers']"));
        button.click();
        Thread.sleep(2000);

        WebElement button1 = driver.findElement(By.xpath("//ul[@class='sublist' ]//li[@class='inactive']//a[@href = '/desktops']"));
        button1.click();
        Thread.sleep(2000);

        WebElement SearchKeyword = driver.findElement(By.cssSelector(".page-title"));
        String resultText = SearchKeyword.getText();

        Assert.assertEquals(resultText, "Desktops");

        driver.quit();
    }
}
