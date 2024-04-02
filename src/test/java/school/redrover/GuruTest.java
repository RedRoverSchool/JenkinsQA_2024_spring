package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GuruTest extends BaseTest {
    @Test
    public void FormTest() {

        WebDriver driver = getDriver();
        getDriver().get("https://demo.guru99.com/test/login.html");

        getDriver().findElement(By.id("email")).sendKeys("abcd@gmail.com");
        getDriver().findElement(By.name("passwd")).sendKeys("abcdefghlkjl");
        getDriver().findElement(By.id("SubmitLogin")).click();

        driver.quit();
    }

    @Test
    public void GuruRadioTest() {
        WebDriver driver = getDriver();
        getDriver().get("https://demo.guru99.com/test/radio.html");

        getDriver().findElement (By.xpath("//input[@value='Option 1']")).click();
        getDriver().findElement(By.xpath("//input[@value='Option 2']")).click();
        getDriver().findElement(By.xpath("//input[@value='Option 3']")).click();
        getDriver().findElement(By.xpath("//input[@value='checkbox1']")).click();
        getDriver().findElement(By.xpath("//input[@value='checkbox2']")).click();
        getDriver().findElement(By.xpath("//input[@value='checkbox3']")).click();

        driver.quit();
    }
}

