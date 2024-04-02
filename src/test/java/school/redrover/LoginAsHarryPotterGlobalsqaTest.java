package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginAsHarryPotterGlobalsqaTest {

    @Test
    public void testLoginAsCustomerHarryPotterGlobalsqa() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");

        Thread.sleep(2000);

        WebElement customerLoginButton = driver.findElement(By.xpath("//button[@ng-click='customer()']"));
        customerLoginButton.click();

        Thread.sleep(2000);

        Select select = new Select(driver.findElement(By.id("userSelect")));
        select.selectByIndex(2);

        WebElement loginButton = driver.findElement(By.xpath("//button[@type = 'submit']"));
        loginButton.click();

        Thread.sleep(2000);

        WebElement welcomeText = driver.findElement(By.xpath("//strong[text() = ' Welcome ']"));
        String s = welcomeText.getText();

        Assert.assertEquals(s, "Welcome Harry Potter !!");

        driver.quit();
    }
}
