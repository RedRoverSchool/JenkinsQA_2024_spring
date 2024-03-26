package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;



public class TestAlert {

    @Test
    public void testAlertAppearsAfterItemIsAddedToCart() {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.get("https://magento.softwaretestingboard.com/");

        WebElement searchField = driver.findElement(By.id("search"));
        searchField.sendKeys("Pant");

        WebElement submitButton = driver.findElement(By.xpath("//div[@class='actions']/button"));
        submitButton.click();

        WebElement submitShortLink = driver.findElement(By.xpath("//a[contains(., 'Cronus')]"));
        submitShortLink.click();

        WebElement submitSize = driver.findElement(By.xpath("//*[@id=\"option-label-size-143-item-175\"]"));
        submitSize.click();

        WebElement submitColor = driver.findElement(By.xpath("//div[@option-id ='49']"));
        submitColor.click();

        WebElement submitQty = driver.findElement(By.id("qty"));
        submitQty.sendKeys("12");

        WebElement submitAddToCart = driver.findElement(By.id("product-addtocart-button"));
        submitAddToCart.click();

        WebElement alertShoppingCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//div[@role='alert']")));

        Assert.assertTrue(alertShoppingCart.isDisplayed());
        Assert.assertEquals(alertShoppingCart.getText(),
                "You added Cronus Yoga Pant to your shopping cart.");
        driver.quit();
    }
}
