package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CamelCaseGroupTest extends BaseTest {
    @Test
    public void testCheckRedirectionProShop() {
        getDriver().get("https://www.fairfaxicearena.com/");

        WebElement proShopBtn = getDriver().findElement(By.xpath("//a[normalize-space()='Pro Shop']"));
        proShopBtn.click();

        String currentUrl = getDriver().getCurrentUrl();

        WebElement appointmentRequestForm = getDriver().findElement(By.xpath("//span[contains(text(),'Pro Shop Appointment Request Form')]"));

        Assert.assertEquals(currentUrl, "https://www.fairfaxicearena.com/pro-shop.html");
        Assert.assertTrue(appointmentRequestForm.isDisplayed());
    }
    @Test
    public void testTools() throws InterruptedException {

        getDriver().get("https://www.saucedemo.com/");

        WebElement text = getDriver().findElement(By.id("user-name"));
        text.sendKeys("standard_user");

        WebElement text1 = getDriver().findElement(By.id("password"));
        text1.sendKeys("secret_sauce");

        WebElement button = getDriver().findElement(By.id("login-button"));
        button.click();

        Thread.sleep(1000);

        WebElement link = getDriver().findElement(By.className("app_logo"));
        String resultText = link.getText();

        Assert.assertEquals(resultText, "Swag Labs");
    }
}
