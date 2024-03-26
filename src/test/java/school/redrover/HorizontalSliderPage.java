package school.redrover;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.openqa.selenium.By.xpath;

public class HorizontalSliderPage {

    @Test
    public void testLinkToHorizontalSliderPage() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");

        WebElement dynamicElement = new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        xpath("//a [@href='/horizontal_slider']")));

        WebElement linkToHorizontalSliderPage = driver.findElement(xpath("//a [@href='/horizontal_slider']"));
        linkToHorizontalSliderPage.click();

        String expectedResult = "Horizontal Slider";

        String actualResult = driver.findElement(xpath("//h3 [text() = 'Horizontal Slider']")).getText();

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();
    }

}
