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

    @Test
    public void testWb() throws InterruptedException {

        getDriver().get("https://www.wildberries.ru");

        Thread.sleep(500);

        WebElement searchInput = getDriver().findElement(By.id("searchInput"));
        searchInput.sendKeys("177390212");

        WebElement searchButton = getDriver().findElement(By.id("applySearchBtn"));
        searchButton.click();

        String currentURL = getDriver().getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.wildberries.ru/catalog/0/search.aspx?search=177390212");
    }

    @Test
    public void testOnliner() throws InterruptedException {

        getDriver().get("https://www.onliner.by/");
        getDriver().manage().window().maximize();

        WebElement linkSmart = getDriver().findElement(By.className("project-navigation__sign"));
        linkSmart.click();

        WebElement linkHonor = getDriver().findElement(By.xpath("(//a[@href = 'https://catalog.onliner.by/mobile/honor/x9b8256glmb'])[2]"));
        linkHonor.click();

        WebElement buttonOffer = getDriver().findElement(By.cssSelector("a.button:nth-child(1) > span:nth-child(1)"));
        buttonOffer.click();

        WebElement buttonFilterDropdown = getDriver().findElement(By.className("input-style__real"));
        buttonFilterDropdown.click();

        WebElement textFilterDropdown = getDriver().findElement(By.xpath("//select/option[2]"));
        textFilterDropdown.click();

        WebElement buttonPay = getDriver().findElement(By.xpath("(//a[@href='https://cart.onliner.by/?action=buyNow&shopId=707&productKey=x9b8256glmb&productId=4246922&positionId=707:4246922001&deliveryType=courier'])[2]"));
        buttonPay.click();

        Thread.sleep(5000);

        WebElement message = getDriver().findElement(By.xpath("//*[@class='cart-form__title cart-form__title_base cart-form__title_nocondensed cart-form__title_condensed-other']"));

        Assert.assertEquals(message.getText(), "Оформление заказа");
    }
}
