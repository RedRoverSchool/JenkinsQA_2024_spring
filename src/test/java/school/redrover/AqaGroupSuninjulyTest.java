package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class AqaGroupSuninjulyTest extends AqaGroupBaseTest {
    public String calc(String x) {
        return String.valueOf(Math.log(Math.abs(12 * Math.sin(Integer.parseInt(x)))));
    }

    @Test
    public void testHugeForm() {
        driver.get("https://suninjuly.github.io/huge_form.html");

        List<WebElement> elements = driver.findElements(By.tagName("input"));
        for (WebElement item : elements) {
            item.sendKeys("Мой ответ");
        }

        driver.findElement(By.cssSelector("button.btn")).click();

        Assert.assertTrue(
                getWait15()
                        .until(ExpectedConditions.alertIsPresent())
                        .getText()
                        .startsWith("Congrats, you've passed the task!"),
                "You shall not pass");
    }

    @Test
    public void testTextToBePresent() {
        driver.get("https://suninjuly.github.io/explicit_wait2.html");

        WebElement button_book = driver.findElement(By.id("book"));
        getWait15().until(ExpectedConditions.textToBePresentInElementLocated(By.id("price"), "$100"));
        button_book.click();

        driver.findElement(By.id("answer")).sendKeys(calc(driver.findElement(By.id("input_value")).getText()));
        driver.findElement(By.id("solve")).click();

        Assert.assertTrue(
                getWait15()
                        .until(ExpectedConditions.alertIsPresent())
                        .getText()
                        .startsWith("Congrats, you've passed the task!"),
                "You shall not pass");
    }
}
