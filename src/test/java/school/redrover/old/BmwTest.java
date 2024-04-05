package school.redrover.old;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import java.time.Duration;

@Ignore
public class BmwTest {
    //           Thread.sleep(2000) Добавлено для спецэффектов
    @Test
    public void testBmwSite() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

        driver.get("https://www.bmwmotorcycles.com/en/home.html");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".svg-icon.svg-icon--search")).click();

        WebElement searchField = driver.findElement(By.cssSelector("input[type='search']"));
        Thread.sleep(2000);
        searchField.sendKeys("CE 04");

        WebElement searchButton = driver.findElement(By.cssSelector("button.search__go"));
        Thread.sleep(2000);
        searchButton.click();

        WebElement link = driver.findElement(By.linkText("https://www.bmwmotorcycles.com/en/models/urban_mobility/ce04.html"));
        Thread.sleep(2000);
        link.click();

        WebElement dataEquipmentLink = driver.findElement(By.linkText("Data & Equipment"));
        Thread.sleep(2000);
        dataEquipmentLink.click();

//        driver.quit();
    }
}

