package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class TestQADemo {
    @Test
    public void testToolsQA() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/text-box");

        String title = driver.getTitle();
        Assert.assertEquals(title, "DEMOQA");

        WebElement primaryImage = driver.findElement(By.xpath("//img[@src = '/images/Toolsqa.jpg']"));
        Assert.assertTrue(primaryImage.isDisplayed());
        driver.quit();
    }
}
