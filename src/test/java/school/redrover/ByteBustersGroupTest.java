package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

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
    public void testCounterStrike() {

        WebDriver driver = getDriver();
        driver.get("https://www.counter-strike.net/");

        WebElement buttonSelectLanguage= driver.findElement(By.className("languageselector_LanguageIcon_yTASe"));
        buttonSelectLanguage.click();

        WebElement selectEnglish= driver.findElement(By.className("languageselector_LanguageOption_Kd1K6"));
        selectEnglish.click();

        WebElement languageElement = driver.findElement(By.xpath("//div[@class='languageselector_InnerWrapper_A5ZD2']//span"));
        String languageText = languageElement.getText();

        Assert.assertEquals(languageText, "SELECT LANGUAGE");

        driver.quit();
    }
}