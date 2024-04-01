//This class for @test_crafters group.
//URL for test: https://tutorialsninja.com/demo/

package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.concurrent.TimeUnit;

public class TutorialsNinjaTest extends BaseTest {
    private final static String URL = "https://tutorialsninja.com/demo/";

    @Test
    public void testMainPageView() {

        String featered = initialization(URL).findElement(By.xpath("//h3")).getText();

        Assert.assertEquals(featered, "Featured");
    }

    public WebDriver initialization(String url) {
        WebDriver driver = getDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get(url);
        return driver;
    }
}
