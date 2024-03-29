package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;


public class AmazonTest {


        @Test
        public void searchTest() {
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.amazon.com");

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            WebElement submitButton = driver.findElement(By.id("nav-search-submit-button"));

            searchBox.sendKeys("Raised Garden Beds");
            submitButton.click();

            WebElement message = driver.findElement(By.xpath("//div[@class='a-section a-spacing-small a-spacing-top-small']"));
            String value = message.getText();
            String[] arr = value.split("\"");
            Assert.assertEquals(arr[1], "Raised Garden Beds");

            driver.quit();
        }
    }


