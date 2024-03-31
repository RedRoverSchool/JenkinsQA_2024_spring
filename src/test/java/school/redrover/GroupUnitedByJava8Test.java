package school.redrover;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.time.Duration;

public class GroupUnitedByJava8Test extends BaseTest {
    private static final String GLOBALS_QA_LOGIN_LINKS = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
    private static final String GLOBALS_QA_STRING_VALUE = "testPV";

    @Test
    public void testDemoQADoubleClick() {
        getDriver().get("https://demoqa.com/");

        WebElement elementsPage = getDriver().findElement(By.xpath("//h5[text()='Elements']"));
        elementsPage.click();
        WebElement buttons = getDriver().findElement(By.xpath("//span[@class='text' and text()='Buttons']"));
        buttons.click();

        WebElement doubleClickMe = getDriver().findElement(By.id("doubleClickBtn"));
        new Actions(getDriver())
            .doubleClick(doubleClickMe)
            .perform();

        String doubleClickMessageText = getDriver().findElement(By.id("doubleClickMessage")).getText();

        Assert.assertEquals(doubleClickMessageText, "You have done a double click");
    }

    @Test
    public void testLookingForTheSummer() {
        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().get("https://www.onlinetrade.ru/");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name = 'query']"))).sendKeys("лето");
        getDriver().findElement(By.xpath("//input[@type = 'submit']")).click();

        Assert.assertTrue((getDriver().findElement(By.xpath("//h1[contains(text(), 'Найденные товары')]")).isDisplayed()));
    }

    @Test
    public void testCreateNewUserGlobalQAAsManager() {
        WebDriver driver = getDriver();
        driver.get(GLOBALS_QA_LOGIN_LINKS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement managerButton = driver.findElement(By.xpath("//button[@ng-click='manager()']"));
        managerButton.click();

        WebElement addCustomerButton = driver.findElement(By.xpath("//button[@ng-click='addCust()']"));
        addCustomerButton.click();

        WebElement boxFNText = driver.findElement(By.xpath("//input[@ng-model='fName']"));
        boxFNText.sendKeys(GLOBALS_QA_STRING_VALUE);

        WebElement boxLNText = driver.findElement(By.xpath("//input[@ng-model='lName']"));
        boxLNText.sendKeys(GLOBALS_QA_STRING_VALUE);

        WebElement boxPCText = driver.findElement(By.xpath("//input[@ng-model='postCd']"));
        boxPCText.sendKeys(GLOBALS_QA_STRING_VALUE);

        WebElement addCustomerSubmitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        addCustomerSubmitButton.click();

        driver.switchTo().alert().accept();

        WebElement goCustomersButton = driver.findElement(By.xpath("//button[@ng-click='showCust()']"));
        goCustomersButton.click();

        WebElement searchInput = driver.findElement(By.xpath("//input[@ng-model='searchCustomer']"));
        searchInput.sendKeys(GLOBALS_QA_STRING_VALUE);

        WebElement searchResultsTable = driver.findElement(By.xpath("//tbody/tr[last()]/td[1]"));
        Assert.assertEquals(searchResultsTable.getText(), GLOBALS_QA_STRING_VALUE);
    }

    @Test
    public void happyPathLogin() {
        getDriver().get("https://www.saucedemo.com/");

        WebElement name = getDriver().findElement(By.xpath("//input[@id='user-name']"));
        name.sendKeys("visual_user");

        WebElement password = getDriver().findElement(By.xpath("//input[@id='password']"));
        password.sendKeys("secret_sauce");

        getDriver().findElement(By.id("login-button")).click();

        String expectedResult = getDriver().getCurrentUrl();

        Assert.assertEquals("https://www.saucedemo.com/inventory.html", expectedResult);

    }
    @Test
    public void testAlertAppearsAfterItemIsAddedToCart() {

        getDriver().get("https://magento.softwaretestingboard.com/");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));

        WebElement searchField = getDriver().findElement(By.id("search"));
        searchField.sendKeys("Pant");

        WebElement submitButton = getDriver().findElement(By.xpath("//div[@class='actions']/button"));
        submitButton.click();

        WebElement submitShortLink = getDriver().findElement(By.xpath("//a[contains(., 'Cronus')]"));
        submitShortLink.click();

        WebElement submitSize = getDriver().findElement(By.xpath("//*[@id=\"option-label-size-143-item-175\"]"));
        submitSize.click();

        WebElement submitColor = getDriver().findElement(By.xpath("//div[@option-id ='49']"));
        submitColor.click();

        WebElement submitQty = getDriver().findElement(By.id("qty"));
        submitQty.sendKeys("12");

        WebElement submitAddToCart = getDriver().findElement(By.id("product-addtocart-button"));
        submitAddToCart.click();

        WebElement alertShoppingCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='alert']")));

        Assert.assertTrue(alertShoppingCart.isDisplayed());
        Assert.assertEquals(alertShoppingCart.getText(), "You added Cronus Yoga Pant to your shopping cart.");
    }
}
