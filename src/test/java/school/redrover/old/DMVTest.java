package school.redrover.old;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import java.util.ArrayList;


@Ignore
public class DMVTest {

    @Test
    public void testSearch() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.dmv.ca.gov/portal/");

        WebElement textBox = driver.findElement(By.id("site-header-search-input"));
        textBox.sendKeys("SPECIAL INTEREST AND PERSONALIZED LICENSE PLATES ORDERS");

        WebElement submitButton = driver.findElement(By.className("site-header__search-submit"));
        submitButton.click();
        Thread.sleep(3000);

        WebElement orderButton = driver.findElement(By.xpath("//a[contains(text(),'Order personalized plates')]"));
        orderButton.click();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        WebElement orderButton2 = driver.findElement(By.xpath("//a[contains(text(),'Order personalized plates')]"));
        orderButton2.click();

        WebElement name = driver.findElement(By.xpath("//h2[@class=\"text--h3 mb-24\"]"));
        String value = name.getText();
        Thread.sleep(2000);
        Assert.assertEquals(value, "Terms & Acknowledgement");

        driver.quit();

    }
}