package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class CalculatorPageTest {

    @Test
    public void testPageCalculator(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://testpages.eviltester.com/styled/index.html");
        WebElement link = driver.findElement(By.id("calculatetest"));
        link.click();
        WebElement textBox1 = driver.findElement(By.id("number1"));
        textBox1.sendKeys("5");
        WebElement textBox2 = driver.findElement(By.id("number2"));
        textBox2.sendKeys("5");
        WebElement button = driver.findElement(By.id("calculate"));
        button.click();
        WebElement result = driver.findElement(By.id("answer"));
        Assert.assertEquals(result.getText(), "10");

        driver.quit();

    }

}


