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
        getWebDriver().get("https://demoqa.com/");

        WebElement elementsPage = getWebDriver().findElement(By.xpath("//h5[text()='Elements']"));
        elementsPage.click();
        WebElement buttons = getWebDriver().findElement(By.xpath("//span[@class='text' and text()='Buttons']"));
        buttons.click();

        WebElement doubleClickMeButton = getWebDriver().findElement(By.id("doubleClickBtn"));
        new Actions(getWebDriver())
                .doubleClick(doubleClickMeButton)
                .perform();

        String doubleClickMessageText = getWebDriver().findElement(By.id("doubleClickMessage")).getText();

        Assert.assertEquals(doubleClickMessageText, "You have done a double click");
    }

    @Test
    public void testLookingForTheSummer() {
        WebDriverWait webDriverWait = new WebDriverWait(getWebDriver(), Duration.ofSeconds(5));

        getWebDriver().get("https://www.onlinetrade.ru/");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name = 'query']"))).sendKeys("лето");
        getWebDriver().findElement(By.xpath("//input[@type = 'submit']")).click();

        Assert.assertTrue((getWebDriver().findElement(By.xpath("//h1[contains(text(), 'Найденные товары')]")).isDisplayed()));
    }

    @Test
    public void testCreateNewUserGlobalQAAsManager() {
        WebDriver driver = getWebDriver();
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
        getWebDriver().get("https://www.saucedemo.com/");

        WebElement name = getWebDriver().findElement(By.xpath("//input[@id='user-name']"));
        name.sendKeys("visual_user");

        WebElement password = getWebDriver().findElement(By.xpath("//input[@id='password']"));
        password.sendKeys("secret_sauce");

        getWebDriver().findElement(By.id("login-button")).click();
        String actualResult = getWebDriver().getCurrentUrl();

        Assert.assertEquals(actualResult, "https://www.saucedemo.com/inventory.html");
    }

    @Test
    public void testAlertAppearsAfterItemIsAddedToCart() {

        getWebDriver().get("https://magento.softwaretestingboard.com/");
        WebDriverWait wait = new WebDriverWait(getWebDriver(), Duration.ofSeconds(2));

        WebElement searchField = getWebDriver().findElement(By.id("search"));
        searchField.sendKeys("Pant");

        WebElement submitButton = getWebDriver().findElement(By.xpath("//div[@class='actions']/button"));
        submitButton.click();

        WebElement submitShortLink = getWebDriver().findElement(By.xpath("//a[contains(., 'Cronus')]"));
        submitShortLink.click();

        WebElement submitSize = getWebDriver().findElement(By.xpath("//*[@id=\"option-label-size-143-item-175\"]"));
        submitSize.click();

        WebElement submitColor = getWebDriver().findElement(By.xpath("//div[@option-id ='49']"));
        submitColor.click();

        WebElement submitQty = getWebDriver().findElement(By.id("qty"));
        submitQty.sendKeys("12");

        WebElement submitAddToCart = getWebDriver().findElement(By.id("product-addtocart-button"));
        submitAddToCart.click();

        WebElement alertShoppingCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='alert']")));

        Assert.assertTrue(alertShoppingCart.isDisplayed());
        Assert.assertEquals(alertShoppingCart.getText(), "You added Cronus Yoga Pant to your shopping cart.");
    }

    @Test
    public void testCheckingAddingCart() {
        WebDriver driver = getWebDriver();

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

        WebDriver driver = getWebDriver();
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

        WebDriver driver = getWebDriver();
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
        getWebDriver().get("https://demoqa.com/");

        WebElement elementsPage = getWebDriver().findElement(By.xpath("//h5[text()='Elements']"));
        elementsPage.click();
        WebElement buttons = getWebDriver().findElement(By.xpath("//span[@class='text' and text()='Buttons']"));
        buttons.click();

        WebElement rightClickMeButton = getWebDriver().findElement(By.id("rightClickBtn"));
        new Actions(getWebDriver())
                .contextClick(rightClickMeButton)
                .perform();

        String contextClickMessageText = getWebDriver().findElement(By.id("rightClickMessage")).getText();

        Assert.assertEquals(contextClickMessageText, "You have done a right click");
    }

    @Test
    public void testPraktikum() throws InterruptedException {
        getWebDriver().get("https://qa-mesto.praktikum-services.ru/signin");

        WebElement eMail = getWebDriver().findElement(By.id("email"));
        eMail.sendKeys("wovibic859@mnsaf.com");

        WebElement password = getWebDriver().findElement(By.id("password"));
        password.sendKeys("123");

        WebElement LoginEnter = getWebDriver().findElement(By.className("auth-form__button"));
        LoginEnter.click();

        Thread.sleep(3000);
        WebElement accountName = getWebDriver().findElement(By.className("profile__title"));
        Assert.assertEquals(accountName.getText(), "Жак-Ив Кусто");
    }

    @Test
    public void testSuccessLoginSaucedemo() {
        getWebDriver().get("https://www.saucedemo.com/");
        getWebDriver().manage().window().maximize();

        getWebDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getWebDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getWebDriver().findElement(By.id("login-button")).click();

        Assert.assertEquals(getWebDriver().findElement(By.xpath("//div[@class='app_logo']")).getText(), "Swag Labs");
    }

    @Test
    public void testClassifiedCheckbox() {
        getWebDriver().get("https://demoqa.com");

        getWebDriver().findElement(By.xpath("//h5[text()='Elements']")).click();
        getWebDriver().findElement(By.id("item-1")).click();
        getWebDriver().findElement(By.className("rct-option-expand-all")).click();
        getWebDriver().findElement(By.xpath("//label[@for='tree-node-classified']")).click();
        String value = getWebDriver().findElement(By.id("result")).getText();

        Assert.assertEquals(value, "You have selected :\n" + "classified");
    }

    @Test
    public void testItemsSortedInReverseOrder() {
        getWebDriver().get("https://www.saucedemo.com/");
        getWebDriver().findElement(By.id("user-name")).sendKeys(STANDARD_USER_LOGIN);
        getWebDriver().findElement(By.id("password")).sendKeys(STANDARD_USER_PASSWORD);
        getWebDriver().findElement(By.id("login-button")).click();

        WebElement itemsSortingCriterion = getWebDriver().findElement(By.className("product_sort_container"));
        Select select = new Select(itemsSortingCriterion);
        select.selectByVisibleText("Name (Z to A)");

        List<WebElement> items = getWebDriver().findElements(By.xpath("//div[@class='inventory_item_name ']"));

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
        getWebDriver().get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");

        Thread.sleep(1500);

        WebElement customerLoginButton = getWebDriver().findElement(By.xpath("//button[@ng-click='customer()']"));
        customerLoginButton.click();

        Thread.sleep(1500);

        Select select = new Select(getWebDriver().findElement(By.id("userSelect")));
        select.selectByIndex(2);

        WebElement loginButton = getWebDriver().findElement(By.xpath("//button[@type = 'submit']"));
        loginButton.click();

        Thread.sleep(1500);

        WebElement welcomeText = getWebDriver().findElement(By.xpath("//strong[text() = ' Welcome ']"));

        Assert.assertEquals(welcomeText.getText(), "Welcome Harry Potter !!");
    }

    @Test
    public void testCartAddItemBikeLight() {
        getWebDriver().get("https://www.saucedemo.com/");
        getWebDriver().findElement(By.id("user-name")).sendKeys(STANDARD_USER_LOGIN);
        getWebDriver().findElement(By.id("password")).sendKeys(STANDARD_USER_PASSWORD);
        getWebDriver().findElement(By.id("login-button")).click();

        WebElement addBikeLightToCartButton = getWebDriver().findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        addBikeLightToCartButton.click();

        WebElement cartClickIcon = getWebDriver().findElement(By.className("shopping_cart_link"));
        cartClickIcon.click();

        WebElement itemInCart = getWebDriver().findElement(By.id("item_0_title_link"));
        itemInCart.getText();

        Assert.assertEquals(itemInCart.getText(), "Sauce Labs Bike Light", "Wrong item in the Cart");
    }

    @Test
    public void testItemsSortedAlphabetically() {
        getWebDriver().get("https://www.saucedemo.com/");
        getWebDriver().findElement(By.id("user-name")).sendKeys(STANDARD_USER_LOGIN);
        getWebDriver().findElement(By.id("password")).sendKeys(STANDARD_USER_PASSWORD);
        getWebDriver().findElement(By.id("login-button")).click();

        List<WebElement> items = getWebDriver().findElements(By.cssSelector("[class^='inventory_item_name']"));

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
        getWebDriver().get("https://www.saucedemo.com/");
        getWebDriver().findElement(By.id("user-name")).sendKeys(STANDARD_USER_LOGIN);
        getWebDriver().findElement(By.id("password")).sendKeys(STANDARD_USER_PASSWORD);
        getWebDriver().findElement(By.id("login-button")).click();

        WebElement itemsSortingCriterion = getWebDriver().findElement(By.className("product_sort_container"));
        String defaultSortingCriterion = new Select(itemsSortingCriterion).getFirstSelectedOption().getText();

        Assert.assertEquals(defaultSortingCriterion, "Name (A to Z)",
                "Default sorting criterion is not alphabetical");
    }
    @Test
    public void testCart() throws InterruptedException {

        getWebDriver().get("https://magento.softwaretestingboard.com/");
        getWebDriver().manage().window().maximize();

        Thread.sleep(3000);
        WebElement salePageButton = getWebDriver().findElement(By.id("ui-id-8"));
        salePageButton.click();
        WebElement shopWomanDealButton = getWebDriver().findElement(By.xpath("//span[@class = 'more button']"));
        shopWomanDealButton.click();
        WebElement bessYogaShortItemLink = getWebDriver().
                findElement(By.xpath("//a[contains(., 'Bess')]"));
        bessYogaShortItemLink.click();
        Thread.sleep(2000);
        WebElement sizeSelect = getWebDriver().findElement(By.xpath("//div[@option-id='171']"));
        sizeSelect.click();
        Thread.sleep(2000);
        WebElement colorSelect = getWebDriver().findElement(By.xpath("//div[@option-id='50']"));
        colorSelect.click();
        WebElement addToCard = getWebDriver().findElement(By.id("product-addtocart-button"));
        addToCard.click();
        Thread.sleep(3000);
        WebElement cartCounterNumber = getWebDriver().findElement(By.xpath("//span[@class ='counter-number']"));
        cartCounterNumber.click();

        Thread.sleep(3000);
        Assert.assertEquals((getWebDriver().findElement(By
                        .xpath("//strong[@class='product-item-name']/a")).getText()),
                "Bess Yoga Short");
        getWebDriver().quit();
    }
}
