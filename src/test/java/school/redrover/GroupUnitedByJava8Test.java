package school.redrover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class GroupUnitedByJava8Test extends BaseTest {
    private static final String GLOBALS_QA_LOGIN_LINKS = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
    private static final String GLOBALS_QA_STRING_VALUE = "testPV";
    private static final String STANDARD_USER_LOGIN = "standard_user";
    private static final String STANDARD_USER_PASSWORD = "secret_sauce";
    private static final String EXPECTED_TEXT = "Sauce Labs Backpack";

    @Test
    public void testDemoQADoubleClick() {
        getDriver().get("https://demoqa.com/");

        WebElement elementsPage = getDriver().findElement(By.xpath("//h5[text()='Elements']"));
        elementsPage.click();
        WebElement buttons = getDriver().findElement(By.xpath("//span[@class='text' and text()='Buttons']"));
        buttons.click();

        WebElement doubleClickMeButton = getDriver().findElement(By.id("doubleClickBtn"));
        new Actions(getDriver())
                .doubleClick(doubleClickMeButton)
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
    public void testHappyPathLogin() {
        getDriver().get("https://www.saucedemo.com/");

        WebElement name = getDriver().findElement(By.xpath("//input[@id='user-name']"));
        name.sendKeys("visual_user");

        WebElement password = getDriver().findElement(By.xpath("//input[@id='password']"));
        password.sendKeys("secret_sauce");

        getDriver().findElement(By.id("login-button")).click();
        String actualResult = getDriver().getCurrentUrl();

        Assert.assertEquals(actualResult, "https://www.saucedemo.com/inventory.html");
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

    @Test
    public void testCheckingAddingCart() {
        WebDriver driver = getDriver();

        driver.get("https://www.saucedemo.com/");

        WebElement userName = driver.findElement(By.id("user-name"));
        userName.sendKeys(STANDARD_USER_LOGIN);
        WebElement userPassword = driver.findElement(By.id("password"));
        userPassword.sendKeys(STANDARD_USER_PASSWORD);
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement addCartButton = driver.findElement(By.name("add-to-cart-sauce-labs-backpack"));
        addCartButton.click();
        WebElement shoppingCart = driver.findElement(By.className("shopping_cart_link"));
        shoppingCart.click();

        WebElement cartList = driver.findElement(By.id("item_4_title_link"));

        Assert.assertEquals(cartList.getText(), EXPECTED_TEXT);
    }

    @Test
    public void testAddingItemToCart() {

        WebDriver driver = getDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement login = driver.findElement(By.id("user-name"));
        login.sendKeys("standard_user");

        WebElement pass = driver.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement addButton = driver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        addButton.click();

        WebElement shoppingCart = driver.findElement(By.id("shopping_cart_container"));
        shoppingCart.click();

        WebElement labsBikeText = driver.findElement(By.id("item_0_title_link"));
        String resultText = labsBikeText.getText();

        Assert.assertEquals(resultText, "Sauce Labs Bike Light");
    }

    @Test
    public void testLogoutUser() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement login = driver.findElement(By.id("user-name"));
        login.sendKeys("visual_user");

        WebElement pass = driver.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement burgerButton = driver.findElement(By.id("react-burger-menu-btn"));
        burgerButton.click();

        Thread.sleep(1000);

        WebElement logoutButton = driver.findElement(By.id("logout_sidebar_link"));
        logoutButton.click();

        String textTitle = driver.getTitle();
        Assert.assertEquals(textTitle, "Swag Labs");
    }

    @Test
    public void testDemoQARightClick() {
        getDriver().get("https://demoqa.com/");

        WebElement elementsPage = getDriver().findElement(By.xpath("//h5[text()='Elements']"));
        elementsPage.click();
        WebElement buttons = getDriver().findElement(By.xpath("//span[@class='text' and text()='Buttons']"));
        buttons.click();

        WebElement rightClickMeButton = getDriver().findElement(By.id("rightClickBtn"));
        new Actions(getDriver())
                .contextClick(rightClickMeButton)
                .perform();

        String contextClickMessageText = getDriver().findElement(By.id("rightClickMessage")).getText();

        Assert.assertEquals(contextClickMessageText, "You have done a right click");
    }

    @Test
    public void testPraktikum() throws InterruptedException {
        getDriver().get("https://qa-mesto.praktikum-services.ru/signin");

        WebElement eMail = getDriver().findElement(By.id("email"));
        eMail.sendKeys("wovibic859@mnsaf.com");

        WebElement password = getDriver().findElement(By.id("password"));
        password.sendKeys("123");

        WebElement LoginEnter = getDriver().findElement(By.className("auth-form__button"));
        LoginEnter.click();

        Thread.sleep(3000);
        WebElement accountName = getDriver().findElement(By.className("profile__title"));
        Assert.assertEquals(accountName.getText(), "Жак-Ив Кусто");
    }

    @Test
    public void testSuccessLoginSaucedemo() {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().manage().window().maximize();

        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='app_logo']")).getText(), "Swag Labs");
    }

    @Test
    public void testClassifiedCheckbox() {
        getDriver().get("https://demoqa.com");

        getDriver().findElement(By.xpath("//h5[text()='Elements']")).click();
        getDriver().findElement(By.id("item-1")).click();
        getDriver().findElement(By.className("rct-option-expand-all")).click();
        getDriver().findElement(By.xpath("//label[@for='tree-node-classified']")).click();
        String value = getDriver().findElement(By.id("result")).getText();

        Assert.assertEquals(value, "You have selected :\n" + "classified");
    }

    @Test
    public void testItemsSortedInReverseOrder() {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().findElement(By.id("user-name")).sendKeys(STANDARD_USER_LOGIN);
        getDriver().findElement(By.id("password")).sendKeys(STANDARD_USER_PASSWORD);
        getDriver().findElement(By.id("login-button")).click();

        WebElement itemsSortingCriterion = getDriver().findElement(By.className("product_sort_container"));
        Select select = new Select(itemsSortingCriterion);
        select.selectByVisibleText("Name (Z to A)");

        List<WebElement> items = getDriver().findElements(By.xpath("//div[@class='inventory_item_name ']"));

        List<String> itemsNames = new ArrayList<>();
        for (WebElement itemName : items) {
            String name = itemName.getText();
            itemsNames.add(name);
        }

        List<String> expectedSortedNames = new ArrayList<>(itemsNames);
        expectedSortedNames.sort(Collections.reverseOrder());

        Assert.assertEquals(itemsNames, expectedSortedNames);
    }

    @Test
    public void testLoginAsCustomerHarryPotterGlobalsqa() throws InterruptedException {
        getDriver().get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");

        Thread.sleep(1500);

        WebElement customerLoginButton = getDriver().findElement(By.xpath("//button[@ng-click='customer()']"));
        customerLoginButton.click();

        Thread.sleep(1500);

        Select select = new Select(getDriver().findElement(By.id("userSelect")));
        select.selectByIndex(2);

        WebElement loginButton = getDriver().findElement(By.xpath("//button[@type = 'submit']"));
        loginButton.click();

        Thread.sleep(1500);

        WebElement welcomeText = getDriver().findElement(By.xpath("//strong[text() = ' Welcome ']"));

        Assert.assertEquals(welcomeText.getText(), "Welcome Harry Potter !!");
    }

    @Test
    public void testCartAddItemBikeLight() {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().findElement(By.id("user-name")).sendKeys(STANDARD_USER_LOGIN);
        getDriver().findElement(By.id("password")).sendKeys(STANDARD_USER_PASSWORD);
        getDriver().findElement(By.id("login-button")).click();

        WebElement addBikeLightToCartButton = getDriver().findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        addBikeLightToCartButton.click();

        WebElement cartClickIcon = getDriver().findElement(By.className("shopping_cart_link"));
        cartClickIcon.click();

        WebElement itemInCart = getDriver().findElement(By.id("item_0_title_link"));
        itemInCart.getText();

        Assert.assertEquals(itemInCart.getText(), "Sauce Labs Bike Light", "Wrong item in the Cart");
    }

    @Test
    public void testItemsSortedAlphabetically() {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().findElement(By.id("user-name")).sendKeys(STANDARD_USER_LOGIN);
        getDriver().findElement(By.id("password")).sendKeys(STANDARD_USER_PASSWORD);
        getDriver().findElement(By.id("login-button")).click();

        List<WebElement> items = getDriver().findElements(By.cssSelector("[class^='inventory_item_name']"));

        List<String> itemsNames = new ArrayList<>();
        for (WebElement itemName : items) {
            String name = itemName.getText();
            itemsNames.add(name);
        }

        List<String> expectedSortedNames = new ArrayList<>(itemsNames);
        Collections.sort(expectedSortedNames);

        Assert.assertEquals(itemsNames, expectedSortedNames, "Items are not sorted alphabetically");
    }

    @Test
    public void testDefaultSortingCriterion() {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().findElement(By.id("user-name")).sendKeys(STANDARD_USER_LOGIN);
        getDriver().findElement(By.id("password")).sendKeys(STANDARD_USER_PASSWORD);
        getDriver().findElement(By.id("login-button")).click();

        WebElement itemsSortingCriterion = getDriver().findElement(By.className("product_sort_container"));
        String defaultSortingCriterion = new Select(itemsSortingCriterion).getFirstSelectedOption().getText();

        Assert.assertEquals(defaultSortingCriterion, "Name (A to Z)",
                "Default sorting criterion is not alphabetical");
    }
}
