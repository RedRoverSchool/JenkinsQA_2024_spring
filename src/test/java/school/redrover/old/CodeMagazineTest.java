package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class CodeMagazineTest {
    @Test
    public void testMCode() {   // Home_Work 15
        WebDriver driver = new ChromeDriver();
        driver.get("https://thecode.media/");

        WebElement search_button = driver.findElement(By.className("heading-search__open"));
        search_button.click();

        WebElement search_text = driver.findElement(By.className("heading-search__input"));
        search_text.sendKeys("Api");

        search_button.click();

        WebElement found_text = driver.findElement(By.className("search__title"));
        String result_Search = found_text.getText();

        Assert.assertEquals("api", result_Search);

        driver.quit();


    }
}
