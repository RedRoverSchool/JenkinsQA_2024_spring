package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HabrTest {

    private final static String HABR_URL = "https://habr.com/ru/search/";

    private final static String TEST_TEXT = "RedRover School";

    private final static String expectedResult = "Большая подборка ресурсов и сообществ для тестировщика";

    @Test
    public void testFindArticle () throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get(HABR_URL);
        driver.manage().window().maximize();

        WebElement textArea = driver.findElement(By.xpath("//input[@name='q']"));
        textArea.sendKeys(TEST_TEXT);
        textArea.sendKeys(Keys.RETURN);
        Thread.sleep(2000);
        String hrefXpath = "//article[@id='720526']/div[1]/h2/a[@href ='/ru/articles/720526/'][@class='tm-title__link']";
        WebElement firstArticle = driver.findElement(By.xpath(hrefXpath));
        String actualResult = firstArticle.getText();

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();
    }
}
