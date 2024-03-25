package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class MagentoTest {

    private static final String URL = "https://magento.softwaretestingboard.com/";

    @Test
    public void testMagento() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);

        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@id='ui-id-8']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[text()='Jackets']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@id='option-label-size-143-item-166'][1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@option-id='50'][1]")).click();
        Thread.sleep(2000);

        driver.quit();
    }
}
