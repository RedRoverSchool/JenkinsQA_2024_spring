package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Exp86Test {
    @Test
    public void testExp86() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://exp86.ru/");

        WebElement textBox = driver.findElement(By.name("q"));
        textBox.sendKeys("Hello world!");

//        How find element "Личный кабинет"
        WebElement registrationHrev = driver.findElement(By.linkText("Личный кабинет"));
        registrationHrev.click();

        WebElement text = driver.findElement(By.className("form_default_star"));
        String value = text.getText();

        Assert.assertEquals("* обязательные поля для заполнения", value);
    }
}
