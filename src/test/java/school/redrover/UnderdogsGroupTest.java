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
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class UnderdogsGroupTest {
    private  final static String URL_HOMEPAGE = "https://demoqa.com/";
    @Test
    public void testDemoqaInput() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/");
        driver.findElement(By.id("close-fixedban")).click();
        driver.findElement(By.xpath("//div[@class='category-cards']/div[1]")).click();
        driver.findElement(By.xpath("//span[text()='Text Box']")).click();
        String name = "test";

        driver.findElement(By.id("userName")).sendKeys(name);
        driver.findElement(By.id("submit")).click();
        String result = driver.findElement(By.id("name")).getText();

        Assert.assertEquals(result, "Name:" + name);

        driver.quit();
    }

    @Test
    public void testElementsTextBox() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/");
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]")).click();
        driver.findElement(By.xpath("(//*[@id=\"item-0\"])[1]")).click();
        String email = "test@gmail.com";

        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("submit")).click();
        String result = driver.findElement(By.id("email")).getText();

        Assert.assertEquals(result, "Email:" + email);

        driver.quit();
    }

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
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/checkbox");

        driver
                .findElement(By.xpath("//div[@id='tree-node']/ol/li/span/button"))
                .click();
        driver.findElement(By.xpath("//*[@id='tree-node']//li[2]//button"))
                .click();
        driver.findElement(By.xpath("//*[@for='tree-node-workspace']/span"))
                .click();
        try {
            Assert.assertEquals(driver.findElement(By.id("result")).getText(),
                    "You have selected :\nworkspace\nreact\nangular\nveu");
        } finally {
            driver.quit();
        }
    }

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
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");
        driver.findElement(By.xpath("//h5[contains(text(),'Elements')]")).click();

        Assert.assertTrue(driver.findElement(By.id("RightSide_Advertisement")).isDisplayed());

        driver.quit();
    }

    @Test
    public void testAddToCartButton() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://natr.com.tr/shop/?s=Vitamin&post_type=product&dgwt_wcas=1");

        WebElement buttonAddToСart = driver.findElement(By.xpath("(//a[@class ='button product_type_simple add_to_cart_button ajax_add_to_cart'])[1]"));

        Actions actions = new Actions(driver);
        actions.moveToElement(buttonAddToСart).click(buttonAddToСart).perform();

        String order = buttonAddToСart.getAttribute("aria-label").replaceAll(".*“(.*)”.*", "$1");

        WebElement buttonShoppingBag = driver.findElement(By.xpath("(//span[@class = 'cart-items-count count header-icon-counter'])[1]"));
        buttonShoppingBag.click();

        Thread.sleep(4000);

        String element = driver.findElement(By.xpath("(//li[@class='woocommerce-mini-cart-item mini_cart_item']/a)[2]")).getText();

        Assert.assertEquals(element.trim(), order);

        driver.quit();
    }
    @Test
    public  void testVerifyTitle() {
        WebDriver driver = new ChromeDriver();
        driver.get(URL_HOMEPAGE);

        Assert.assertEquals(driver.getTitle(), "DEMOQA", "Not equal your message with title of page");
        driver.quit();
    }

    @Test
    public void searchByName() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://uk.coach.com/");

        driver.findElement(By.xpath("//p[contains(text(),'Women')]")).click();
        Thread.sleep(4000);

        driver.findElement(By.xpath("//div[contains(text(),'Backpacks')]")).click();
        Thread.sleep(4000);

        WebElement actualText = driver.findElement(By.xpath("//h1[@class = 'chakra-text css-zy3pag']"));

        Assert.assertEquals(actualText.getText(), "WOMEN'S BACKPACKS");
    }
}
