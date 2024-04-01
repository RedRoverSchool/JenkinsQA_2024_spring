package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class GroupJavaExitCodeZeroTest extends BaseTest {
    private static final String BASE_URL = "https://automationexercise.com";
    private final static String HABR_URL = "https://habr.com/ru/search/";

    private String getRandomEmail() {
        int n = 15;
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString() + "@gmail.com";
    }

    @Test
    public void testAllProductsNavigation() throws InterruptedException {
        final String expectedProductsUrl = "https://automationexercise.com/products";
        final String expectedHeader = "ALL PRODUCTS";

        getDriver().get(BASE_URL);

        getDriver().findElement(By.xpath("//a[@href='/products']")).click();
        Thread.sleep(1000);
        if (getDriver().findElements(By.xpath("//ins[@class='adsbygoogle adsbygoogle-noablate']")).size() > 0) {
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            jse.executeScript(
                    "const ads = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (ads.length > 0) ads[0].remove();"
            );

            getDriver().findElement(By.xpath("//a[@href='/products']")).click();
        }

        Thread.sleep(2000);

        final String currentUrl = getDriver().getCurrentUrl();

        Assert.assertEquals(currentUrl, expectedProductsUrl);

        final String actualHeader = getDriver().findElement(By.xpath("//div[@class = 'features_items']/h2")).getText();

        Assert.assertEquals(actualHeader, expectedHeader);
    }

    @Test
    public void testFindArticle() throws InterruptedException {
        final String TEST_TEXT = "RedRover School";
        final String expectedResult = "Большая подборка ресурсов и сообществ для тестировщика";

        getDriver().get(HABR_URL);
        getDriver().manage().window().maximize();

        WebElement textArea = getDriver().findElement(By.xpath("//input[@name='q']"));
        textArea.sendKeys(TEST_TEXT);
        textArea.sendKeys(Keys.RETURN);
        Thread.sleep(2000);
        String hrefXpath = "//article[@id='720526']/div[1]/h2/a[@href ='/ru/articles/720526/'][@class='tm-title__link']";
        WebElement firstArticle = getDriver().findElement(By.xpath(hrefXpath));
        String actualResult = firstArticle.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testEightComponents() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement textBox = getDriver().findElement(By.name("my-text"));
        WebElement submitButton = getDriver().findElement(By.cssSelector("button"));
        textBox.sendKeys("Selenium");
        submitButton.click();
        WebElement message = getDriver().findElement(By.id("message"));
        String value = message.getText();

        Assert.assertEquals("Received!", value);
    }

    @Test
    public void testAllProductsNavigationYS() throws InterruptedException{
        final String expectedProductsUrl = "https://automationexercise.com/products";
        final String expectedHeader = "ALL PRODUCTS";

        getDriver().get(BASE_URL);

        getDriver().findElement(By.xpath("//a[@href='/products']")).click();
        Thread.sleep(1000);
        if (!getDriver().findElements(By.xpath("//ins[@class='adsbygoogle adsbygoogle-noablate']")).isEmpty()) {
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            jse.executeScript(
                    "const ads = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (ads.length > 0) ads[0].remove();"
            );

            getDriver().findElement(By.xpath("//a[@href='/products']")).click();
        }

        Thread.sleep(2000);

        final String currentUrl = getDriver().getCurrentUrl();

        Assert.assertEquals(currentUrl, expectedProductsUrl);

        final String actualHeader = getDriver().findElement(By.xpath("//div[@class = 'features_items']/h2")).getText();

        Assert.assertEquals(actualHeader, expectedHeader);
    }

    @Test
    void testVerifyScrollUpUsingArrowButtonAndScrollDown()
            throws InterruptedException {

        final String expectedSubscription = "SUBSCRIPTION";
        final String expectedText = "Full-Fledged practice website for Automation Engineers";

        getDriver().get(BASE_URL);
        getDriver().manage().window().maximize();

        ((JavascriptExecutor)getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
        WebElement subscription = getDriver().findElement(By.xpath("//h2[contains(text(), 'Subscription')]"));

        Assert.assertTrue(subscription.isDisplayed());
        Assert.assertEquals(subscription.getText(), expectedSubscription);

        getDriver().findElement(By.id("scrollUp")).click();
        Thread.sleep(1000);

        By xpath = By.xpath("//h2[contains(text(), '" + expectedText + "')]");
        WebElement textElement = getDriver().findElement(xpath);

        Assert.assertTrue(textElement.isDisplayed());
        Assert.assertEquals(textElement.getText(), expectedText);
    }

    @Test(invocationCount = 1)
    public void testSubscriptionInCartPage()
            throws InterruptedException {

        final String expectedTitle = "Automation Exercise";
        final String expectedSubscription = "SUBSCRIPTION";
        final String expectedText = "You have been successfully subscribed!";

        getDriver().manage().window().maximize();
        getDriver().get(BASE_URL);
        Assert.assertEquals(getDriver().getTitle(), expectedTitle);

        getDriver().findElement(By.xpath("//a[contains(text(), 'Cart')]")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), BASE_URL + "/view_cart");
        ((JavascriptExecutor)getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");

        Thread.sleep(1000);
        WebElement subscription = getDriver().findElement(By.xpath("//h2[contains(text(), 'Subscription')]"));

        Assert.assertTrue(subscription.isDisplayed());
        Assert.assertEquals(subscription.getText(), expectedSubscription);

        getDriver().findElement(By.id("susbscribe_email")).sendKeys("qwerty@gmail.com");
        getDriver().findElement(By.id("subscribe")).click();

        WebElement successMessage = getDriver().findElement(By.id("success-subscribe"));
        Assert.assertTrue(successMessage.isDisplayed());
        Assert.assertEquals(successMessage.getText(), expectedText);
    }

    @Test(invocationCount = 1)
    public void testAddReviewOnProduct()
            throws InterruptedException {

        final String expected = "ALL PRODUCTS";
        final String review = "WRITE YOUR REVIEW";

        getDriver().get(BASE_URL);
        getDriver().manage().window().maximize();

        getDriver().findElement(By.xpath("//a[@href='/products']")).click();

        Thread.sleep(1000);
        new Actions(getDriver()).moveByOffset(50, 50).click().perform();
        Thread.sleep(2000);

        Assert.assertEquals(getDriver().getCurrentUrl(), BASE_URL + "/products");

        By products = By.xpath("//h2[contains(@class, 'title text-center') and text()='All Products']");

        WebElement element = getDriver().findElement(products);
        Assert.assertTrue(element.isDisplayed());
        Assert.assertEquals(element.getText(), expected);

        List<WebElement> cards = getDriver().findElements(By.xpath("//a[text()='View Product']"));

        int randomIndex = new Random().nextInt(cards.size());
        System.out.println(randomIndex);
        WebElement randomCard = cards.get(randomIndex);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", randomCard);

        randomCard.click();

        Thread.sleep(1000);
        new Actions(getDriver()).moveByOffset(50, 50).click().perform();
        Thread.sleep(2000);

        By reviews = By.xpath("//li[@class='active']//a[@href='#reviews']");

        WebElement webElement = getDriver().findElement(reviews);

        Assert.assertTrue(webElement.isDisplayed());
        Assert.assertEquals(webElement.getText(), review);

        getDriver().findElement(By.id("name")).sendKeys("Jack");
        getDriver().findElement(By.id("email")).sendKeys("test@gmail.com");
        getDriver().findElement(By.id("review")).sendKeys("This is a test review.");

        ((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, 500);");

        getDriver().findElement(By.id("button-review")).click();

        By selector = By.cssSelector("#review-section .alert-success.alert span");
        WebElement elementSelector = getDriver().findElement(selector);

        String reviewMessage = "Thank you for your review.";
        Assert.assertTrue(elementSelector.isDisplayed());
        Assert.assertEquals(elementSelector.getText(), reviewMessage);
    }
    @Test
    public void testContactUs() {
        final String expectedMessage = "Success! Your details have been submitted successfully.";
        final String name = "Tom";
        final String subjectMessage = "Claim";
        final String textMessage = "My problem is....";

        getDriver().get(BASE_URL);
        getDriver().findElement(By.xpath("//a[@href='/contact_us']")).click();
        getDriver().findElement(By.xpath("//input[@placeholder ='Name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//input[@placeholder ='Email']")).sendKeys(getRandomEmail());
        getDriver().findElement(By.xpath("//input[@placeholder ='Subject']")).sendKeys(subjectMessage);
        getDriver().findElement(By.id("message")).sendKeys(textMessage);
        getDriver().findElement(By.xpath("//input[@name ='submit']")).click();
        getDriver().switchTo().alert().accept();
        String actualMessage = getDriver().findElement(By.xpath("//div[@class='status alert alert-success']")).getText();

        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testFindWomenBootsPage() throws InterruptedException {
        final String expectedElement = "Women'S Boots";
        final  String BASE_URL = "https://www.6pm.com/";

        getDriver().get(BASE_URL);
        getDriver().manage().window().maximize();
        getDriver().findElement(By.xpath("//a[@href='/shoes']")).click();

        Thread.sleep(3000);

        getDriver().findElement(By.xpath("//li[1]/button")).click();
        getDriver().findElement(By.xpath("//a[@data-eventvalue='S-NAV-WomensBoots']")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), BASE_URL
                + "women-boots/CK_XARCz1wHAAQHiAgMBAhg.zso?s=isNew/desc/goLiveDate/desc/recentSalesStyle/desc/");

        Thread.sleep(3000);

        By womensBoots = By.xpath("//h1[@class='px-z']");
        String actualElement = getDriver().findElement(womensBoots).getText();

        Assert.assertEquals(actualElement, expectedElement);
    }

    @Test
    public void testSignUpAndDeleteAccount()
            throws InterruptedException {

        final String expectedResultName = "Elizabeth";
        final String expectedResult = "New User Signup!";
        final String expectedCreated = "ACCOUNT CREATED!";
        final String expectedDeleted = "ACCOUNT DELETED!";
        final String expectedResultAccount = "ENTER ACCOUNT INFORMATION";

        getDriver().manage().window().maximize();
        getDriver().get(BASE_URL);

        getDriver().findElement(By.linkText("Signup / Login")).click();

        WebElement signupHeader = getDriver().findElement(By.xpath("//div[@class='signup-form']//h2"));

        Assert.assertTrue(signupHeader.isDisplayed());
        Assert.assertEquals(signupHeader.getText(), expectedResult);

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys("Elizabeth");
        getDriver().findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys("alice%d@gmail.com".formatted(new Random().nextInt(10000)));
        getDriver().findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        WebElement accountInfo = getDriver().findElement(By.xpath("//h2[@class='title text-center']"));

        Assert.assertTrue(accountInfo.isDisplayed());
        Assert.assertEquals(accountInfo.getText(), expectedResultAccount);

        getDriver().findElement(By.id("newsletter")).click();
        getDriver().findElement(By.id("id_gender1")).click();
        getDriver().findElement(By.id("password")).sendKeys("password");
        getDriver().findElement(By.id("first_name")).sendKeys("Jack");
        getDriver().findElement(By.id("last_name")).sendKeys("Sparrow");
        getDriver().findElement(By.id("company")).sendKeys("Pearl");
        getDriver().findElement(By.id("address1")).sendKeys("East");
        getDriver().findElement(By.id("address2")).sendKeys("India");
        getDriver().findElement(By.id("country")).sendKeys("Caribbean");
        getDriver().findElement(By.id("state")).sendKeys("Port Royal");
        getDriver().findElement(By.id("city")).sendKeys("Jamaica");
        getDriver().findElement(By.id("zipcode")).sendKeys("12345");
        getDriver().findElement(By.id("mobile_number")).sendKeys("+380990909999");

        WebElement createAccount = getDriver().findElement(By.cssSelector("button[data-qa='create-account']"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", createAccount);
        createAccount.click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("b")).getText(), expectedCreated);

        getDriver().findElement(By.linkText("Continue")).click();
        new Actions(getDriver()).moveByOffset(50, 50).click().perform();
        Thread.sleep(1000);

        WebElement loginName = getDriver().findElement(By.xpath("//li/a/b"));

        Assert.assertTrue(loginName.isDisplayed());
        Assert.assertEquals(loginName.getText(), expectedResultName);

        getDriver().findElement(By.linkText("Delete Account")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("b")).getText(), expectedDeleted);

        getDriver().findElement(By.linkText("Continue")).click();
    }

    @Test
    public void testAddToCartFromRecommendedItems()
            throws InterruptedException {
        getDriver().manage().window().maximize();
        getDriver().get(BASE_URL);

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);

        WebElement recommendedItems = getDriver().findElement(By.xpath("//h2[contains(text(), 'recommended items')]"));

        Assert.assertTrue(recommendedItems.isDisplayed());

        getDriver().findElement(By.xpath("//a[@class='btn btn-default add-to-cart']")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.partialLinkText("View Cart")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), BASE_URL + "/view_cart");
        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@id='product-1']")).isDisplayed());
    }
}
