package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class GroupJavaExitCodeZeroTest extends BaseTest {
    private static final String BASE_URL = "http://automationexercise.com";
    private final static String HABR_URL = "https://habr.com/ru/search/";

    @Test
    public void testAllProductsNavigation() throws InterruptedException {
        final String expectedProductsUrl = "https://automationexercise.com/products";
        final String expectedHeader = "ALL PRODUCTS";

        getDriver().get(BASE_URL);

        getDriver().findElement(By.xpath("//a[@href='/products']")).click();
        Thread.sleep(1000);
        if (getDriver().findElement(By.xpath("//ins[@class='adsbygoogle adsbygoogle-noablate']")).isDisplayed()) {
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            jse.executeScript(
                    "const ads = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (ads.length > 0) ads[0].remove();"
            );

            getDriver().findElement(By.xpath("//a[@href='/products']")).click();
        }

        Thread.sleep(2000);

        final String currentUrl = getDriver().getCurrentUrl();

        Assert.assertEquals(currentUrl, expectedProductsUrl);

        final String actualHeader = getDriver().findElement(By.xpath("//div[@class = 'features_items']/h2")).getText();

        Assert.assertEquals(actualHeader, expectedHeader);
    }

    @Test
    public void testFindArticle() throws InterruptedException {
        final String TEST_TEXT = "RedRover School";
        final String expectedResult = "Большая подборка ресурсов и сообществ для тестировщика";

        getDriver().get(HABR_URL);
        getDriver().manage().window().maximize();

        WebElement textArea = getDriver().findElement(By.xpath("//input[@name='q']"));
        textArea.sendKeys(TEST_TEXT);
        textArea.sendKeys(Keys.RETURN);
        Thread.sleep(2000);
        String hrefXpath = "//article[@id='720526']/div[1]/h2/a[@href ='/ru/articles/720526/'][@class='tm-title__link']";
        WebElement firstArticle = getDriver().findElement(By.xpath(hrefXpath));
        String actualResult = firstArticle.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testEightComponents() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement textBox = getDriver().findElement(By.name("my-text"));
        WebElement submitButton = getDriver().findElement(By.cssSelector("button"));
        textBox.sendKeys("Selenium");
        submitButton.click();
        WebElement message = getDriver().findElement(By.id("message"));
        String value = message.getText();

        Assert.assertEquals("Received!", value);
    }

    @Test
    public void testAllProductsNavigationYS() throws InterruptedException{
        final String expectedProductsUrl = "https://automationexercise.com/products";
        final String expectedHeader = "ALL PRODUCTS";

        getDriver().get(BASE_URL);

        getDriver().findElement(By.xpath("//a[@href='/products']")).click();
        Thread.sleep(1000);
        if (getDriver().findElement(By.xpath("//ins[@class='adsbygoogle adsbygoogle-noablate']")).isDisplayed()) {
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            jse.executeScript(
                    "const ads = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (ads.length > 0) ads[0].remove();"
            );

            getDriver().findElement(By.xpath("//a[@href='/products']")).click();
        }

        Thread.sleep(2000);

        final String currentUrl = getDriver().getCurrentUrl();

        Assert.assertEquals(currentUrl, expectedProductsUrl);

        final String actualHeader = getDriver().findElement(By.xpath("//div[@class = 'features_items']/h2")).getText();

        Assert.assertEquals(actualHeader, expectedHeader);
    }
}
