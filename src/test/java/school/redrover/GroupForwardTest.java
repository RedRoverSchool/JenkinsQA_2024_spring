package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupForwardTest {

    @Test
    public void testCostco() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.costco.com/");
        driver.findElement(By.xpath("//*[@id='search-field']")).sendKeys("apple");
        driver.findElement(By.xpath("//*[@id='search-field']")).sendKeys(Keys.ENTER);

        Assert.assertEquals(driver.findElement(By.xpath("//*[@id='crumbs_ul']/li[2]/span")).getText(), "Apple Products");

        driver.quit();
    }
}

