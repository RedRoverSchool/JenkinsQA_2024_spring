package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class DemoQaSelectableTest {

    @Test
    public void selectableTest() {
//        WebDriver driver = new ChromeDriver();
        // Чтобы открыть Chrome в режиме инкогнито:
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        ChromeDriver driver = new ChromeDriver(options);

        driver.get("https://demoqa.com/selectable");
        driver.manage().window().maximize(); // опционально
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        String elementXpath = "//*[@id=\"verticalListContainer\"]/li[1]";
        driver.findElement(By.xpath(elementXpath)).click();
        String status = driver.findElement(By.xpath(elementXpath)).getAttribute("class");
        Boolean isActive = status.contains("active");
        Assert.assertEquals(isActive, true);

        driver.quit();
    }
}
