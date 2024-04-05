package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Ignore
public class JavAngryTest extends BaseTest {

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
        getDriver().get("https://demoqa.com/");

        String expectedText = "You have done a double click";

        getDriver().findElement(By.xpath("//h5[text()='Elements']")).click();
        getDriver().findElement(By.xpath("//span[text() = 'Buttons']")).click();
        WebElement clickable = getDriver().findElement(By.id("doubleClickBtn"));
        new Actions(getDriver())
                .doubleClick(clickable)
                .perform();

        String actualText = getDriver().findElement(By.id("doubleClickMessage")).getText();

        Assert.assertEquals(actualText, expectedText);
    }

    @Test
    void testActionCardsTitlesDemoQAMainPage() {
        getDriver().get("https://demoqa.com/");

        List<String> expectedBannerTitles = new ArrayList<>(Arrays.asList("Elements", "Forms", "Alerts, Frame & Windows",
                "Widgets", "Interactions", "Book Store Application"));
        List<WebElement> elementList = getDriver().findElements(By.xpath("//div[@class='card mt-4 top-card']"));
        List<String> actualBannerTitles = WebElementToString(elementList);

        Assert.assertEquals(actualBannerTitles, expectedBannerTitles);
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
        getDriver().get("https://portal.311.nyc.gov/");
        getDriver().findElement(By.xpath("//input[@aria-label='Search']")).sendKeys("concerts");
        getDriver().findElement(By.className("search-magnify")).click();

        WebElement widgets = getDriver().findElement(By.xpath("//ul[@class='pagination']"));
        int deltaY = widgets.getRect().y;
        new Actions(getDriver())
                .scrollByAmount(0, deltaY)
                .perform();
        getDriver().findElement(By.xpath("//ul[@class='pagination']"));
        getDriver().findElement(By.linkText("2")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://portal.311.nyc.gov/search/?q=concerts&page=2");
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

    @Test
    void testFields() {
        WebDriver webDriver = getDriver();
        webDriver.get("https://letcode.in/test");

        webDriver.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();
        webDriver.findElement(By.id("fullName")).sendKeys("Ali Ian");
        webDriver.findElement(By.id("join")).sendKeys(" and happy \t");
        webDriver.findElement(By.id("clearMe")).clear();

        Assert.assertTrue(webDriver.findElement(By.id("noEdit")).isDisplayed());
    }

    @Test
    public void registrationTest() {
        WebDriver driver = getDriver();
        driver.get("https://demoqa.com/webtables");
        driver.manage().window().maximize();

        WebElement addBtn = driver.findElement(By.cssSelector("#addNewRecordButton"));
        addBtn.click();

        WebElement firstName = driver.findElement(By.id("firstName"));
        firstName.sendKeys("Irina");

        WebElement lastName = driver.findElement(By.id("lastName"));
        lastName.sendKeys("Romanova");

        WebElement email = driver.findElement(By.id("userEmail"));
        email.sendKeys("test@test.ru");

        WebElement age = driver.findElement(By.id("age"));
        age.sendKeys("18");

        WebElement salary = driver.findElement(By.id("salary"));
        salary.sendKeys("180");

        WebElement department = driver.findElement(By.id("department"));
        department.sendKeys("IT");

        WebElement btnSubmit = driver.findElement(By.id("submit"));
        btnSubmit.click();

        WebElement currentName = driver.findElement(By.xpath("//div[contains(text(),'Irina')]"));
        String curName = currentName.getText();
        Assert.assertEquals(curName, "Irina");
    }

    @Test
    public void testDatika() {
        getDriver().get("https://datika.me/ru/");

        WebElement textBox = getDriver().findElement(By.id("search"));
        textBox.sendKeys("Акция");

        WebElement submitButton = getDriver().findElement(By.xpath("//*[@id='header']/div/div[2]/form/button"));
        submitButton.click();

        WebElement message = getDriver().findElement(By.xpath("//*[@id='page-content']/h1"));
        String value = message.getText();

        Assert.assertEquals(value, "По запросу «Акция»");
    }
}

