package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class FacebookTest extends BaseTest {
        @Test
        public void autorizeInFacebook() {
            getDriver().get("https://www.facebook.com/?locale=ru_RU");

            WebElement userlogin = getDriver().findElement(By.id("email"));
            userlogin.sendKeys("test");

            WebElement userpassword = getDriver().findElement(By.id("pass"));
            userpassword.sendKeys("pass");

            WebElement sumbitButton = getDriver().findElement(By.name("login"));
            sumbitButton.click();

            WebElement modalWindow = getDriver().findElement(By.xpath(".//div[text()='Это ваш аккаунт?']"));
            String checkModalWindowName = modalWindow.getText();
            Assert.assertEquals(checkModalWindowName, "Это ваш аккаунт?");

        }
}
