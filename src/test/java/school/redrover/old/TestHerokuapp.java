package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class TestHerokuapp {
    @Test
    public void testHerokuapp() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");

        WebElement textBox = driver.findElement(By.cssSelector("#content > ul > li:nth-child(1) > a"));
        textBox.click();

        WebElement message = driver.findElement(By.xpath("/html/body/div[2]/div/div/h3"));
        String value = message.getText();
        Assert.assertEquals("A/B Test Variation 1", value);

        driver.quit();
    }
}
