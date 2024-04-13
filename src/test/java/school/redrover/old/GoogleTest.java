package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class GoogleTest extends BaseTest {

    @Test
    public void testGoogle() throws InterruptedException {
        getDriver().get("https://www.google.com");

        WebElement text = getDriver().findElement(By.id("APjFqb"));
        text.sendKeys("Selenium");

        Thread.sleep(1000); // make it 500

        WebElement button = getDriver().findElement(By.className("gNO89b"));
        button.click();

        WebElement link = getDriver().findElement(By.xpath("//a[@href = 'https://www.selenium.dev/']/h3"));
        String resultText = link.getText();

        Assert.assertEquals(resultText, "Selenium");
    }
}
