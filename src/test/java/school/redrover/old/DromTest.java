package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class DromTest {
    @Test
    public void testDrom() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://auto.drom.ru/");

        WebElement carBrand = driver.findElement(By.xpath("//input[@placeholder='Марка']"));
        carBrand.click();

        WebElement selectBrand = driver.findElement(By.xpath("//div[@class='css-1r0zrug e1uu17r80']//div[@role='option']"));
        selectBrand.click();

        WebElement notSell = driver.findElement(By.xpath("//label[@for='sales__filter_unsold']"));
        notSell.click();

        WebElement buttonSubmit = driver.findElement(By.xpath("//div[text()='Показать']"));
        buttonSubmit.click();

        WebElement result = driver.findElement(By.cssSelector(".css-qg9fv5.e75dypj1"));
        String resultValue = result.getText();
        Assert.assertEquals("Сначала новые объявления", resultValue);

        driver.quit();

    }
}
