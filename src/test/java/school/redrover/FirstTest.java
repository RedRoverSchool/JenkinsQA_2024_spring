package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstTest {

    @Test
    public void testSeleniumTrainingButton() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.toolsqa.com/");

        WebElement button = driver.findElement(By.xpath("//a[@href = '/selenium-training?q=headers']"));
        button.click();

        WebElement text = driver.findElement(By.xpath("//div[@class='enroll__heading']"));
        String value = text.getText();

        Assert.assertEquals(value, "Selenium Certification Training | Enroll Now | Study Online");

        driver.close();
    }
}
