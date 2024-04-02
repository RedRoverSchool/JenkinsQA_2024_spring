package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class EcosiaTest {
    @Test
    public void testGoodLuck() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.ecosia.org/");

        WebElement form = driver.findElement(By.className("search-form__input"));
        form.sendKeys("weather in LA");

        WebElement search = driver.findElement(By.xpath("//button[@data-test-id='search-form-submit']"));
        search.click();

        WebElement menu = driver.findElement(By.linkText("More"));
        menu.click();

        driver.quit();



    }
}
