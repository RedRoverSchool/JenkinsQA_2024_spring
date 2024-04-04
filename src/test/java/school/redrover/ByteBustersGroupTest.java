package school.redrover;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ByteBustersGroupTest extends BaseTest {

    @Test
    public void CoreTest() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        getDriver().get("https://explorer.globe.engineer/");

        WebElement textBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));

        Assert.assertEquals(textBox.getAttribute("placeholder"), "I want to discover...");

        String SearchWord = "IT";
        textBox.sendKeys(SearchWord);
        textBox.sendKeys(Keys.ENTER);

        WebElement searchForElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[span[contains(text(), 'Search for:')]]")));
        String testWord = searchForElement.getText();
        Assert.assertEquals(testWord, SearchWord);
    }

    @Test
    public void testSite() {
        getDriver().get("https://www.saucedemo.com/");

        String title = getDriver().getTitle();
        Assert.assertEquals("Swag Labs", title);

        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement userName = getDriver().findElement(By.id("user-name"));
        userName.sendKeys("visual_user");

        WebElement passw = getDriver().findElement(By.id("password"));
        passw.sendKeys("secret_sauce");

        WebElement submitButton = getDriver().findElement(By.name("login-button"));
        submitButton.click();

        WebElement page = getDriver().findElement(By.className("title"));
        String value = page.getText();

        Assert.assertEquals("Products", value);
    }

    @Test
    public void testLogin() {
        getDriver().get("https://www.saucedemo.com/");

        WebElement login = getDriver().findElement(By.id("user-name"));
        WebElement psw = getDriver().findElement(By.id("password"));
        WebElement signInButton = getDriver().findElement(By.id("login-button"));

        login.sendKeys("standard_user");
        psw.sendKeys("secret_sauce");
        signInButton.submit();

        WebElement displayedPage = getDriver().findElement(By.className("title"));
        String targetText = displayedPage.getText();
        Assert.assertEquals(targetText, "Products");
    }
    @Test
    public void wooordHunttest(){
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        getDriver().get("https://wooordhunt.ru/");

        getDriver().findElement(By.id("hunted_word")).sendKeys("apple");
        getDriver().findElement(By.id("hunted_word_submit")).click();

        String value = getDriver().findElement(By.className("t_inline_en")).getText();

        Assert.assertEquals("яблоко, яблоня, чепуха, лесть, яблочный", value);
    }

    @Test
    public void testSearchOnFlashscore() {
        getDriver().get("https://www.flashscore.ua/");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        getDriver().findElement(By.id("search-window")).click();
        getDriver().findElement(By.cssSelector("input")).sendKeys("aaaaaaaaa");

        Assert.assertEquals(getDriver().findElement(By.className("searchResults__noResult"))
                .getText(), "За Вашим запитом нічого не знайдено.");
    }

    @Test
    public void testAddToCart() {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        getDriver().findElement(By.name("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.name("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();
        getDriver().findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
        getDriver().findElement(By.className("shopping_cart_link")).click();

        Assert.assertEquals(getDriver().findElement(By.className("shopping_cart_badge")).getText(), "1");
        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@id='item_3_title_link']/div[@class = 'inventory_item_name']")).getText(), "Test.allTheThings() T-Shirt (Red)");
    }

    @Test
    public void testRemoveFromCart() {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        getDriver().findElement(By.name("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.name("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();
        getDriver().findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
        getDriver().findElement(By.id("remove-test.allthethings()-t-shirt-(red)")).click();
        getDriver().findElement(By.className("shopping_cart_link")).click();

        Assert.assertTrue(getDriver().findElements(By.className("shopping_cart_badge")).isEmpty());
        Assert.assertTrue(getDriver().findElements(By.className("cart_item")).isEmpty());
    }

    @Test
    public void googleTranslatorTest() {

        getDriver().get("https://translate.google.com/#");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        WebElement textField = getDriver().findElement(By.className("er8xn"));
        textField.sendKeys("Hello World");

        WebElement translationFieldText = getDriver().findElement(By.
                xpath("(//span[@class='ryNqvb'])[1]"));

        Assert.assertEquals(translationFieldText.getText(), "Привет, мир");
    }

    @Test
    public void xpathDinerTest() throws InterruptedException {

        getDriver().get("https://topswagcode.com/xpath/");
        getDriver().manage().window().maximize();

        WebElement inputField = getDriver().findElement(By.
                xpath("(//input[@placeholder='Type in a Xpath selector'])[1]"));
        WebElement levelText = getDriver().findElement(By.xpath("//span[@class='level-text']"));

        inputField.sendKeys("//plate ");
        inputField.sendKeys(Keys.ENTER);

        Thread.sleep(2000); //sorry, i can not use another variants - they do not work!

        Assert.assertEquals(levelText.getText(), "Level 2 of 26");

    }

    @Test
    public void testCounterStrike() {

        WebDriver driver = getDriver();
        driver.get("https://www.counter-strike.net/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));

        WebElement buttonSelectLanguage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".languageselector_InnerWrapper_A5ZD2 .languageselector_LanguageIcon_yTASe")));
        buttonSelectLanguage.click();

        WebElement selectEnglish= driver.findElement(By.className("languageselector_LanguageOption_Kd1K6"));
        selectEnglish.click();

        WebElement languageElement = driver.findElement(By.xpath("//div[@class='languageselector_InnerWrapper_A5ZD2']//span"));
        String languageText = languageElement.getText();

        Assert.assertEquals(languageText, "SELECT LANGUAGE");

    }

    @Test
    public void testCheckITboxCart() throws InterruptedException {
        int priceOfCurrentItem;
        int currentValueOfCart = 0;
        List<Integer> actualValueOfCart = new ArrayList<>();
        List<Integer> expectedValueOfCart = new ArrayList<>();

        getDriver().get("https://www.itbox.ua/");
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        getDriver().findElement(By.xpath("//div[@class='center-part']//input[@name='q']")).sendKeys("SSD Samsung");
        getDriver().findElement(By.xpath("//div[@class='center-part']//button[@class='search-submit center']")).click();
        currentValueOfCart += getItemPrice(1);

        addItemToCart();
        expectedValueOfCart.add(currentValueOfCart);
        actualValueOfCart.add(getCartValue());

        getDriver().navigate().back();
        currentValueOfCart += getItemPrice(2);

        addItemToCart();
        expectedValueOfCart.add(currentValueOfCart);
        actualValueOfCart.add(getCartValue());
        Thread.sleep(200);

        priceOfCurrentItem = Integer.parseInt(getDriver()
                .findElement(By.xpath("//*[@class='stuff-price__row stuff-price__has-sale']//*[@class='stuff-price__digits scada']"))
                .getText());

        getDriver().findElement(By.xpath("(//button[@class='plusmin plusmin-plus '])[2]")).click();
        Thread.sleep(500);

        currentValueOfCart += priceOfCurrentItem;
        expectedValueOfCart.add(currentValueOfCart);
        actualValueOfCart.add(getCartValue());

        getDriver().findElement(By.xpath("(//a[@class='cart-rm js-cart-rm'])[2]")).click();
        getDriver().findElement(By.xpath("(//a[contains(@class, '_remove')])[2]")).click();
        Thread.sleep(500);

        currentValueOfCart -= priceOfCurrentItem * 2;
        expectedValueOfCart.add(currentValueOfCart);
        actualValueOfCart.add(getCartValue());

        getDriver().findElement(By.xpath("//a[contains(@class, 'empty')]")).click();

        for (int i = 0; i < expectedValueOfCart.size(); i++) {
            Assert.assertEquals(actualValueOfCart.get(i), expectedValueOfCart.get(i));
        }
        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//div[@class='not-found__content']"))
                .isDisplayed());
    }

    private void openCart(){
        getDriver().findElement(By.xpath("//ul[contains(@class,'mobile')]//a[contains(@class,'cart')]")).click();
    }
    private int getCartValue(){
        return Integer.parseInt(getDriver()
                .findElement(By.xpath("//div[contains(@class, 'full')]//strong[contains(@class, 'price')]"))
                .getText());
    }

    private void addItem(){
        getDriver().findElement(By.xpath("(//a[@class='add add-cart'])[1]")).click();
    }

    private int getItemPrice(int itemNumber){
        return Integer.parseInt(getDriver()
                .findElement(By.xpath("(//*[@class='stuff-price__digits'])[" + itemNumber + "]"))
                .getText());
    }

    private void pressTwiceEscape(){
        getDriver().findElement(By.xpath("//body")).sendKeys(Keys.ESCAPE);
        getDriver().findElement(By.xpath("//body")).sendKeys(Keys.ESCAPE);
    }

    private void addItemToCart() throws InterruptedException {
        addItem();
        pressTwiceEscape();
        Thread.sleep(200);
        openCart();
    }

    @Test
    public void testSauceDemoPurchase() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        getDriver().get("https://www.saucedemo.com/");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();

        getDriver().findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        getDriver().findElement(By.id("shopping_cart_container")).click();
        getDriver().findElement(By.id("checkout")).click();

        getDriver().findElement(By.id("first-name")).sendKeys("First Name");
        getDriver().findElement(By.id("last-name")).sendKeys("Last Name");
        getDriver().findElement(By.id("postal-code")).sendKeys("123");
        getDriver().findElement(By.id("continue")).click();
        getDriver().findElement(By.name("finish")).click();
        getDriver().findElement(By.name("back-to-products")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }
    @Test
    public void testCheckSortFantasyPlayersByPrice() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        getDriver().manage().window().maximize();

        getDriver().get("https://fantasy.premierleague.com/");
        if(getDriver().findElement(By.id("onetrust-policy-text")).isDisplayed()){
            getDriver().findElement(By.id("onetrust-accept-btn-handler")).click();
        }
        getDriver().findElement(By.xpath("//*[text()='Statistics']")).click();
        new Select(getDriver().findElement(By.id("sort"))).selectByVisibleText("Price");
        List<WebElement> prices = getDriver().findElements(By.xpath("//tr[contains(@class, 'Row')]/td[3]"));

        double currentValue = Double.parseDouble(prices.get(0).getText());
        for (int i = 1; i < prices.size(); i++) {
            double nextValue = Double.parseDouble(prices.get(i).getText());
            Assert.assertTrue(nextValue <= currentValue, "Error of Sort!!");
            currentValue = nextValue;
        }
    }

}