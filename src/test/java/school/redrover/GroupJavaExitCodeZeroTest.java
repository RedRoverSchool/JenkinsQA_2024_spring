package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupJavaExitCodeZeroTest extends BaseTest {

    private static final String BASE_URL = "http://automationexercise.com";

    private final static String HABR_URL = "https://habr.com/ru/search/";

    private final static String TEST_TEXT = "RedRover School";

    private final static String expectedResult = "Большая подборка ресурсов и сообществ для тестировщика";


    @Test
    public void testAllProductsNavigation() throws InterruptedException {
        final String expectedProductsUrl = "https://automationexercise.com/products";
        final String expectedHeader = "ALL PRODUCTS";

        getDriver().get(BASE_URL);

        getDriver().findElement(By.xpath("//a[@href='/products']")).click();
        Thread.sleep(1000);
        if(getDriver().findElement(By.xpath("//ins[@class='adsbygoogle adsbygoogle-noablate']")).isDisplayed()) {
            JavascriptExecutor jse = (JavascriptExecutor)getDriver();
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
