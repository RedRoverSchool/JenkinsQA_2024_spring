package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SileniumTest {
    @Test
    public void testFirst() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/");

        WebElement title = driver.findElement(By.xpath("//h4[@class='h3 mb-3 selenium-webdriver']"));
        Assert.assertEquals(title.getText(), "Selenium WebDriver");
        driver.quit();
    }

    @Test
    public void testList(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/");

        WebElement dropButton= driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
        dropButton.click();

        List<WebElement> dropdownItems = driver.findElements(By.xpath("//div[@class='dropdown-menu show']/a"));

        int expectedItemCount = 8;
        Assert.assertEquals(expectedItemCount, dropdownItems.size());

        driver.quit();

    }
}

