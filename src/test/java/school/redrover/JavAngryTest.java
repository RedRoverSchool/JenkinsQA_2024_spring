package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavAngryTest {

    @Test
    void testPriceCheckMainPageShoppingPage() {
        WebDriver webDriver = new ChromeDriver();
        final String login = "standard_user";
        final String password = "secret_sauce";
        final String itemToBuy = "Sauce Labs Bike Light".toLowerCase().replaceAll(" ", "-");
        webDriver.get("https://www.saucedemo.com/");
        webDriver.findElement(By.id("user-name")).sendKeys(login);
        webDriver.findElement(By.id("password")).sendKeys(password);
        webDriver.findElement(By.id("login-button")).click();
        String expectedResult = webDriver.findElement(
                By.xpath("//button[contains(@name, '" + itemToBuy + "')]/../div")).getText();
        webDriver.findElement(By.xpath("//button[@id='add-to-cart-" + itemToBuy + "']")).click();
        webDriver.findElement(By.className("shopping_cart_link")).click();
        String actualResult = webDriver.findElement(
                By.xpath("//button[contains(@name, '" + itemToBuy + "')]/../div")).getText();

        Assert.assertEquals(actualResult, expectedResult);
        webDriver.quit();
    }

    @Test
    void testDoubleClickButton() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");

        String expectedText = "You have done a double click";

        driver.findElement(By.xpath("//h5[text()='Elements']")).click();
        driver.findElement(By.xpath("//span[text() = 'Buttons']")).click();
        WebElement clickable = driver.findElement(By.id("doubleClickBtn"));
        new Actions(driver)
                .doubleClick(clickable)
                .perform();

        String actualText = driver.findElement(By.id("doubleClickMessage")).getText();

        Assert.assertEquals(actualText, expectedText);

        driver.quit();
    }

    @Test
    void testMainPageBannerTitlesText() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");
        List<String> expectedBannerTitles = new ArrayList<>(Arrays.asList("Elements", "Forms", "Alerts, Frame & Windows", "Widgets", "Interactions", "Book Store Application"));
        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='card mt-4 top-card']"));

        List<String> actualBannerTitles = WebElementToString(elementList);

        Assert.assertEquals(actualBannerTitles, expectedBannerTitles);
        Assert.assertTrue(actualBannerTitles.contains("Book Store Application"));

        driver.quit();
    }

    public static List<String> WebElementToString(List<WebElement> elementList) {
        List<String> stringList = new ArrayList<>();
        for (WebElement element : elementList) {
            stringList.add(element.getText());
        }

        return stringList;
    }

    @Test
    public void testCheckBox1() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/checkbox");
        WebElement checkBox = driver.findElement(By.className("rct-checkbox"));
        checkBox.click();
        WebElement resultText = driver.findElement(By.id("result"));
        resultText.getText();

        Assert.assertEquals(resultText.getText(), "You have selected :\n" +
                "home\n" +
                "desktop\n" +
                "notes\n" +
                "commands\n" +
                "documents\n" +
                "workspace\n" +
                "react\n" +
                "angular\n" +
                "veu\n" +
                "office\n" +
                "public\n" +
                "private\n" +
                "classified\n" +
                "general\n" +
                "downloads\n" +
                "wordFile\n" +
                "excelFile");
        driver.quit();

    }

    @Test
    public void testBuyBlueJeans() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        driver.manage().window().maximize();
        driver.get("https://askomdch.com/");
        driver.findElement(By.xpath("//a[@href='/store'][text()='Shop Now']")).click();
        driver.findElement(By.xpath("//a[contains(@aria-label,'Basic Blue Jeans')]")).click();
        driver.findElement(By.xpath("//a[@class='added_to_cart wc-forward']")).click();
        String pageName = driver.findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(pageName, "Cart");

    }

    @Test
    public void testInputAndPagination() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://portal.311.nyc.gov/");
        driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys("concerts");
        driver.findElement(By.className("search-magnify")).click();
        WebElement widgets = driver.findElement(By.xpath("//ul[@class='pagination']"));
        int deltaY = widgets.getRect().y;
        new Actions(driver)
                .scrollByAmount(0, deltaY)
                .perform();
        driver.findElement(By.xpath("//ul[@class='pagination']"));
        driver.findElement(By.linkText("2")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://portal.311.nyc.gov/search/?q=concerts&page=2");
        driver.quit();
    }

    @Test
    public void testBuyBracelet() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().window().maximize();
        driver.get("https://askomdch.com/");

        driver.findElement(By.xpath("//a[contains(text(), 'Check Out')]")).click();
        driver.findElement(By.xpath("//a[contains(@aria-label,'Bangle Bracelet')]")).click();
        driver.findElement(By.xpath("//a[@class='added_to_cart wc-forward']")).click();
        String subtotal = driver.findElement(By.xpath("//td[@class='product-subtotal']")).getText();

        Assert.assertEquals(subtotal, "$25.00");
        driver.quit();
    }

    @Test
    public void testCheckoutForm() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://askomdch.com/");

        driver.findElement(By.xpath("//a[@class='wp-block-button__link']")).click();
        driver.findElement(By.xpath("//a[contains(@data-product_id,'1193') and text()='Add to cart']")).click();
        driver.findElement(By.xpath("(//a[@href='https://askomdch.com/cart/' and text()='View cart'])[3]")).click();
        driver.findElement(By.xpath("//a[@class='checkout-button button alt wc-forward']")).click();
        driver.findElement(By.xpath("//input[@id='billing_first_name']")).sendKeys("Ivan");
        driver.findElement(By.xpath("//input[@id='billing_last_name']")).sendKeys("Ivanov");
        driver.findElement(By.xpath("(//span[@class='select2-selection select2-selection--single'])[1]")).click();
        // driver.findElement(By.xpath("(//span[@class='select2-selection__rendered' and @title='Belarus'])[1]")).click();
        driver.findElement(By.xpath("(//span[@class='select2-selection__rendered' and @title='United States (US)'])[1]")).click();
        driver.findElement(By.xpath("//input[@id='billing_address_1']")).sendKeys("125, Lenina street");
        driver.findElement(By.xpath("//input[@id='billing_city']")).sendKeys("Sacramento");
        driver.findElement(By.xpath("//span[@id='select2-billing_state-container']")).click();
        driver.findElement(By.xpath("//input[@name='billing_postcode']")).click();
        driver.findElement(By.xpath("//input[@name='billing_postcode']")).sendKeys("94203");
        driver.findElement(By.xpath("//input[@name='billing_email']")).click();
        driver.findElement(By.xpath("//input[@name='billing_email']")).sendKeys("Ivanov@mail.ru");
        driver.findElement(By.xpath("//input[@id='createaccount']")).click();
        driver.findElement(By.xpath("//input[@name='account_username']")).sendKeys("ivanov12");
        driver.findElement(By.xpath("//input[@name='account_password']")).sendKeys("password");
        driver.findElement(By.xpath("//input[@id='payment_method_cod']")).click();
        driver.findElement(By.xpath("//button[@name='woocommerce_checkout_place_order']")).click();
        driver.quit();
    }
}

