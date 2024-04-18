package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class NewItemFreestyleProjectTest extends BaseTest {
    @Test
    public void DashboardMenu(){
        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys("1");

        String result = getDriver().findElement(By.cssSelector("input[class='jenkins-input']")).getAttribute("value");
        //String input = getDriver().findElement(By.id("name")).getText();
        getDriver().findElement(By.xpath("//li[@tabindex='0']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();


        Assert.assertEquals(result, "1");
      // WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        //WebElement inputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='1']"))); // Ожидание появления элемента

        //Assert.assertEquals(inputElement.getAttribute("value"), "1"); // Проверка значения атрибута "value"

        //Assert.assertEquals(getDriver().findElement(By.xpath("//input[@value='1']")).getText(), "1");
        //System.out.println(getDriver()
          //     .findElement(By.xpath("//input[@class='jenkins-input']")).);

    }



    @Ignore
    @Test
    public void testGoogleTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("http://google.com");
        WebElement text = driver.findElement(By.xpath("//input[@class='truncate']"));
        text.sendKeys("Selenium");

        Thread.sleep(1000);

        WebElement button = driver.findElement(By.xpath("//a[@href='https://www.selenium.dev/']/h3"));
        //String resultText = link.getText();

        //Assert.assertEquals(resultText, "Selenium");
        driver.quit();
    }
}
