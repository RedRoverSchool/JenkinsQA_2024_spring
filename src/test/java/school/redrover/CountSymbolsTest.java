package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CountSymbolsTest {

    private final String URL = "https://involta.ru/tools/length-chars/";
    private final String TEST_TEXT = "Это мой Текст из русских слов, слова dot и 1 цифры.";

    @Test
    public void testCountSymbols() {

        int expectedResult = 2;

        WebDriver driver = new ChromeDriver();
        driver.get(URL);

        driver.findElement(By.id("input")).sendKeys(TEST_TEXT);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        String text =
                driver.findElement(By.xpath("//div/span[text()='Остальных символов']/following-sibling::span")).getText();

        int actualResult = Integer.parseInt(text.trim());

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();
    }
}
