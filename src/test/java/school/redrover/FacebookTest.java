package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

    public class FacebookTest {

        private WebDriver driver;

        @BeforeTest
        public void setUp() {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }

        @Test
        public void autorizeInFacebook() {
            driver.get("https://www.facebook.com/?locale=ru_RU");

            WebElement userlogin = driver.findElement(By.id("email"));
            userlogin.sendKeys("test");

            WebElement userpassword = driver.findElement(By.id("pass"));
            userpassword.sendKeys("pass");

            WebElement sumbitButton = driver.findElement(By.name("login"));
            sumbitButton.click();

            WebElement modalWindow = driver.findElement(By.xpath(".//div[text()='Это ваш аккаунт?']"));
            String checkModalWindowName = modalWindow.getText();
            Assert.assertEquals(checkModalWindowName, "Это ваш аккаунт?");

        }

        @AfterTest
        public void cancelBrowser() {
            driver.quit();
        }

    }
