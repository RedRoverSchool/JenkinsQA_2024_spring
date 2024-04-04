package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class GuruTest extends BaseTest {
    @Test
    public void testLoginGuru() {

        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        getDriver().get("https://demo.guru99.com/test/login.html");

        getDriver().findElement(By.id("email")).sendKeys("abcd@gmail.com");
        getDriver().findElement(By.name("passwd")).sendKeys("abcdefghlkjl");
        getDriver().findElement(By.id("SubmitLogin")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//h3[normalize-space()='Successfully Logged in...']")).getText(),
                "Successfully Logged in...");
    }
}

