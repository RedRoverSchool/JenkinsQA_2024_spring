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
}
