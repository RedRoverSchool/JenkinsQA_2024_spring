package school.redrover.old;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class KIATest2 {

    @Test
    public void testTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.kia-club.com.ua//ucp.php?mode=login");
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("standard_user");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("Superuser2");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"login\"]/div[1]/div/div/fieldset/dl[4]/dd/input[3]"));

        submitButton.click();

        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "kia-club.com.ua. | Личный раздел •");

        driver.quit();
        driver.quit();
    }
}