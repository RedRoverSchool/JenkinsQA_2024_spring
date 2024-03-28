package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class FirstTestAnn {
    @Test
    public void firstTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.sima-land.ru/");

        WebElement city = driver.findElement(By.xpath("//*[@id=\"page-header\"]/div/div[1]/div/div[1]/div/div/div[2]/button[2]"));
        city.click();


        WebElement catalog = driver.findElement(By.xpath("//*[@id=\"page-header\"]/div/div[2]/div/div[1]/button"));
        catalog.click();

        WebElement summerButton = driver.findElement(By.xpath("//*[@id=\"page-header\"]/div/div[2]/div[3]/div[1]/ul/li[3]/a"));
        summerButton.click();

        WebElement findingElement = driver.findElement(By.xpath("//*[@id=\"design-category__root\"]/div/div[2]/div[1]/div/h1"));
        String value = findingElement.getText();
        assertEquals("Летние товары", value);

        driver.quit();

    }

}
