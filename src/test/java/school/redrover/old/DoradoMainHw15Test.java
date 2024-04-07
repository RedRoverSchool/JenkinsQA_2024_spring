package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class DoradoMainHw15Test {

    @Test
    public void testAskomdch() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://askomdch.com/");
        driver.findElement(By.id("menu-item-1229")).click();

        WebElement search = driver.findElement(By.id("woocommerce-product-search-field-0"));
        search.sendKeys("jeans");

        search.sendKeys(Keys.RETURN);

        WebElement SearchResults = driver.findElement(By.className("woocommerce-products-header"));
        String value = SearchResults.getText();
        Assert.assertEquals(value, "Search results: “jeans”");

        driver.quit();
    }
}
