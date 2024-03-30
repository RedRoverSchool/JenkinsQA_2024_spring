package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupJavaExitCodeZeroTest extends BaseTest {

    private static final String BASE_URL = "http://automationexercise.com";

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
}
