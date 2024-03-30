package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class GuruTest {
    @Test
    public void FormTest() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://demo.guru99.com/test/login.html");

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("abcd@gmail.com");

        WebElement password = driver.findElement(By.name("passwd"));
        password.sendKeys("abcdefghlkjl");

        WebElement login = driver.findElement(By.id("SubmitLogin"));
        login.click();

        driver.quit();
    }

    @Test
    public void GuruRadioTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demo.guru99.com/test/radio.html");

        WebElement radio1 = driver.findElement(By.id("vfb-7-1"));
        radio1.click();

        WebElement radio2 = driver.findElement(By.id("vfb-7-2"));
        radio2.click();

        WebElement option1 = driver.findElement(By.id("vfb-6-0"));
        option1.click();

        driver.quit();
    }
}

