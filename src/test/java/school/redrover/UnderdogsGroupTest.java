package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class UnderdogsGroupTest extends BaseTest {
    private  final static String URL_HOMEPAGE = "https://demoqa.com/";
    @Test
    public void testDemoQAInput() {
        getDriver().get("https://demoqa.com/");
        getDriver().findElement(By.id("close-fixedban")).click();
        getDriver().findElement(By.xpath("//div[@class='category-cards']/div[1]")).click();
        getDriver().findElement(By.xpath("//span[text()='Text Box']")).click();
        String name = "test";
        getDriver().findElement(By.id("userName")).sendKeys(name);
        getDriver().findElement(By.id("submit")).click();
        String result = getDriver().findElement(By.id("name")).getText();

        Assert.assertEquals(result, "Name:" + name);
    }


    @Test
    public void testElementsTextBox() {
        getDriver().get("https://demoqa.com/");
        getDriver().findElement(By.xpath("//*[@Class=\"card mt-4 top-card\"]")).click();
        getDriver().findElement(By.xpath("//*[contains(text(),'Text Box')]")).click();
        String email = "test@gmail.com";

        getDriver().findElement(By.id("userEmail")).sendKeys(email);
        getDriver().findElement(By.id("submit")).click();
        String result = getDriver().findElement(By.id("email")).getText();

        Assert.assertEquals(result, "Email:" + email);
    }
    @Ignore
    @Test
    public void testCheckTheQuantityInTheCart() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://natr.com.tr/en/");
        driver.findElement(By.xpath("//li[@class='search']")).click();

        WebElement searchField = driver.findElement(By.xpath("//input[@id='dgwt-wcas-search-input-2']"));
        searchField.sendKeys("Vitamin");

        Actions actions = new Actions(driver);
        actions.moveToElement(searchField).sendKeys(org.openqa.selenium.Keys.ENTER).perform();

        driver.findElement(By.xpath("//span[@class='onsale']/following-sibling::img[@src='https://natr.com.tr/wp-content/uploads/Витамин-C1.jpg-300x300.png']")).click();
        driver.findElement(By.xpath("//button[@name='add-to-cart']")).click();
        driver.findElement(By.xpath("//a[@title='View your shopping cart']")).click();

        String quantity = driver.findElement(By.xpath("//div[@class='quantity']//input[@value='1']")).getAttribute("value");
        Assert.assertEquals(quantity, "1");

        driver.quit();
    }

    @Test
    public void testCheckBox() {
        getDriver().get("https://demoqa.com/checkbox");
        getDriver().manage().window().maximize();

        getDriver()
                .findElement(By.xpath("//div[@id='tree-node']/ol/li/span/button"))
                .click();
        getDriver().findElement(By.xpath("//*[@id='tree-node']//li[2]//button"))
                .click();
        getDriver().findElement(By.xpath("//*[@for='tree-node-workspace']/span"))
                .click();

        Assert.assertEquals(getDriver().findElement(By.id("result")).getText(),
                "You have selected :\nworkspace\nreact\nangular\nveu");
    }

    @Test
    public void testHomeCheckbox() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        getDriver().get("https://demoqa.com/checkbox");
//        getDriver().findElement(new By.ByCssSelector("[class='rct-icon rct-icon-uncheck']")).click();
        getDriver().findElement(By.xpath("//*[@for='tree-node-home']")).click();

        WebElement results = getDriver().findElement(By.id("result"));
        String textHome = """
                You have selected :
                home
                desktop
                notes
                commands
                documents
                workspace
                react
                angular
                veu
                office
                public
                private
                classified
                general
                downloads
                wordFile
                excelFile""";

        Assert.assertEquals(results.getText(), textHome);
    }

    @Ignore
    @Test
    public void numberOfTheCarsPresented() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://av.by/");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[@class='button button--default button--block button--large']"))));
        driver.findElement(By.xpath("//button[@class='button button--default button--block button--large']")).click();
        ((JavascriptExecutor) driver).executeScript("javascript:window.scrollBy(0,500)");
        List<WebElement> carsModels = driver.findElements(By.xpath("//span[@class='catalog__title']"));
        Assert.assertEquals(carsModels.size(), 30);
        driver.quit();
    }

    @Test
    public void testRightSideAdvertisement() {
        getDriver().get("https://demoqa.com/");
        getDriver().manage().window().maximize();

        getDriver().findElement(By.xpath("//h5[contains(text(),'Elements')]")).click();

        Assert.assertTrue(getDriver().findElement(By.id("RightSide_Advertisement")).isDisplayed());
    }


    @Test
    public void testAddToCartButton() throws InterruptedException {
        getDriver().get("https://natr.com.tr/shop/?s=Vitamin&post_type=product&dgwt_wcas=1");
        getDriver().manage().window().maximize();

        WebElement buttonAddToСart = getDriver().findElement(By.xpath("(//a[@class ='button product_type_simple add_to_cart_button ajax_add_to_cart'])[1]"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(buttonAddToСart).click(buttonAddToСart).perform();

        String order = buttonAddToСart.getAttribute("aria-label").replaceAll(".*“(.*)”.*", "$1");
        Thread.sleep(4000);
        WebElement buttonShoppingBag = getDriver().findElement(By.xpath("(//span[@class = 'cart-items-count count header-icon-counter'])[1]"));
        buttonShoppingBag.click();

        Thread.sleep(4000);

        String element = getDriver().findElement(By.xpath("(//li[@class='woocommerce-mini-cart-item mini_cart_item']/a)[2]")).getText();

        Assert.assertEquals(element.trim(), order);
    }
    @Test
    public  void testVerifyTitle() {
        getDriver().get(URL_HOMEPAGE);

        Assert.assertEquals(getDriver().getTitle(), "DEMOQA", "Not equal your message with title of page");
    }

    @Ignore
    @Test
    public void testSearchByName() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://uk.coach.com/");

        driver.findElement(By.xpath("//p[contains(text(),'Women')]")).click();
        Thread.sleep(4000);

        driver.findElement(By.xpath("//div[contains(text(),'Backpacks')]")).click();
        Thread.sleep(4000);

        WebElement actualText = driver.findElement(By.xpath("//h1[@class = 'chakra-text css-zy3pag']"));

        Assert.assertEquals(actualText.getText(), "WOMEN'S BACKPACKS");
    }

    @Test
    public void testAutomationTestingOnline() throws InterruptedException {
        getDriver().get("https://automationintesting.online/");

        String name = "Anhelina";
        WebElement fieldName = getDriver().findElement(By.xpath("//*[@id=\"name\"]"));
        fieldName.sendKeys(name);

        WebElement fieldEmail = getDriver().findElement(By.xpath("//*[@id=\"email\"]"));
        fieldEmail.sendKeys("qa@mail.com");

        WebElement fieldPhone = getDriver().findElement(By.xpath("//*[@id=\"phone\"]"));
        fieldPhone.sendKeys("01234567890");

        String subject = "rooms";
        WebElement fieldSubject = getDriver().findElement(By.xpath("//*[@id=\"subject\"]"));
        fieldSubject.sendKeys(subject);

        WebElement fieldMessage = getDriver().findElement(By.xpath("//*[@id=\"description\"]"));
        fieldMessage.sendKeys("Hello. I'm Anhelina.");

        WebElement buttonSubmit = getDriver().findElement(By.xpath("//*[@id=\"submitContact\"]"));
        buttonSubmit.click();

        Thread.sleep(500);
        WebElement actualText = getDriver().findElement(By.xpath("//div[@class = \"row contact\"]/div[2]"));

        Assert.assertEquals(actualText.getText(), "Thanks for getting in touch " + name + "!" + "\n"
                                                        + "We'll get back to you about" + "\n"
                                                        + subject + "\n"
                                                        + "as soon as possible.");

    }

    @Test
    public void testMainTest() throws InterruptedException {
        getDriver().get("https://art-complex.ru/brand/kl-acoustics/");
        Thread.sleep(2000);
        getDriver().findElement(By.id("mod_search_searchword")).sendKeys("INVOTONE H829");
        Thread.sleep(3000);
        getDriver().findElement(By.id("sr0")).click();
        getDriver().findElement(By.id("tocart_3341")).click();
        getDriver().findElement(By.id("count_3341")).sendKeys("2");
        String resultText = getDriver().findElement(By.className("shop-cart-div-1")).getText();

        Assert.assertEquals(resultText, "Минимальный заказ в нашем магазине 10 000 руб.");
    }

    @Test
    public void testSauceDemo() throws InterruptedException {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().manage().window().maximize();

        Assert.assertEquals ("Swag Labs", getDriver().findElement (By.className("login_logo")).getText());

        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();

        Thread.sleep(1000);

        Assert.assertEquals ("Swag Labs", getDriver().findElement (By.className("app_logo")).getText());
    }
}
