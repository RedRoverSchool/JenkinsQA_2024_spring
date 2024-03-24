package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class AqaGroupSuninjulyTest extends AqaGroupBaseTest {
    @Test
    public void testHugeForm() {
        String link = "https://suninjuly.github.io/huge_form.html";
        driver.get(link);

        List<WebElement> elements = driver.findElements(By.tagName("input"));
        for (WebElement item : elements) {
            item.sendKeys("Мой ответ");
        }

        WebElement button = driver.findElement(By.cssSelector("button.btn"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        Assert.assertTrue(alert.getText().startsWith("Congrats, you've passed the task!"), "wrong answer");
    }
}
