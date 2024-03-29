package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class SeleniumExampleTest {

        @Test
        public void eightComponents() {
            WebDriver driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            driver.get("https://www.selenium.dev/selenium/web/web-form.html");

            WebElement textBox = driver.findElement(By.name("my-text"));
            WebElement submitButton = driver.findElement(By.cssSelector("button"));
            textBox.sendKeys("Selenium");
            submitButton.click();
            WebElement message = driver.findElement(By.id("message"));
            String value = message.getText();

            assertEquals("Received!", value);

            driver.quit();
    }
}
