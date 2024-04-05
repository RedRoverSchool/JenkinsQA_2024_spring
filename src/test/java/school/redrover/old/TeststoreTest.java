package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class TeststoreTest {
    @Test
    public void firstTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://teststore.automationtesting.co.uk/index.php");

        WebElement clothes = driver.findElement(By.cssSelector("#category-3 > a"));
        Actions actions = new Actions(driver);
        actions.moveToElement(clothes).perform();

        WebElement men = driver.findElement(By.cssSelector("#category-4 > a"));
        men.click();

        WebElement Men = driver.findElement(By.className("h1"));

        Assert.assertEquals(Men.getText(), "MEN");
        driver.quit();
        }
}
