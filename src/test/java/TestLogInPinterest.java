import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestLogInPinterest {

    @Test
    public void testMyPinterest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.pinterest.com/");
        Thread.sleep(500);
        String title = driver.getTitle();
        Assert.assertEquals("Pinterest", title);

        WebElement buttonLogIn = driver.findElement(By.xpath("//div[contains(text(),'Log in')]"));
        buttonLogIn.click();
        Thread.sleep(500);

        WebElement userEmailInput = driver.findElement(By.xpath("//input[@id='email']"));
        userEmailInput.sendKeys("gian.chase@rediffmail.com");
        Thread.sleep(1000);

        WebElement userPasswordInput = driver.findElement(By.xpath("//input[@name='password']"));
        userPasswordInput.sendKeys("gian2024!");
        Thread.sleep(500);

        WebElement buttonSubmit = driver.findElement(By.xpath("//button[@type='submit']"));
        buttonSubmit.click();
        Thread.sleep(500);

        driver.quit();
    }
}

