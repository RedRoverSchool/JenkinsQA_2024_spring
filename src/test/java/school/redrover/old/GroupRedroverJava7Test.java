package school.redrover.old;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Ignore
public class GroupRedroverJava7Test extends BaseTest {

    @Test
    public void testSearch() {
        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement textBox = getDriver().findElement(By.name("my-text"));
        textBox.sendKeys("Selenium");

        WebElement submitButton = getDriver().findElement(By.cssSelector("button"));
        submitButton.click();

        WebElement message = getDriver().findElement(By.id("message"));
        String value = message.getText();

        Assert.assertEquals(value, "Received!");
    }

    @Test
    public void testSubmitOrder() {
        getDriver().get("https://play1.automationcamp.ir/");

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", getDriver().findElement(By.xpath("//h5[contains(text(), 'Sample Pages')]")));

        getDriver().findElement(By.xpath("//h5[contains(text(), 'Sample Pages')]/parent::div/following-sibling::div//a")).click();

        WebElement user = getDriver().findElement(By.xpath("//input[@id='user']"));
        user.click();
        user.clear();
        user.sendKeys("admin");

        WebElement password = getDriver().findElement(By.xpath("//input[@id='password']"));
        password.click();
        password.clear();
        password.sendKeys("admin");

        getDriver().findElement(By.xpath("//button[@id='login']")).click();

        WebElement header = getDriver().findElement(By.xpath("//h3"));

        Assert.assertEquals(header.getText(), "Dinesh's Pizza House");
    }

    @Test
    public void testApplitoolsDemo() {

        //pass on the target URL
        getDriver().get("https://demo.applitools.com/");

        //find username field and send over an input
        WebElement userNameField = getDriver().findElement(By.id("username"));
        userNameField.sendKeys("standard_user");

        // find password field and send over an input
        WebElement passwordField = getDriver().findElement(By.id("password"));
        passwordField.sendKeys("12345qwera");

        // find log-in button and do the click
        getDriver().findElement(By.id("log-in")).click();

        //find the static text visible after the user is logged in
        WebElement h6FindText = getDriver().findElement(By.xpath(("(//h6[@class='element-header'])[1]")));
        String h6String = h6FindText.getText();

        //check if the "Financial Overview" text exists
        Assert.assertEquals(h6String, "Financial Overview");
    }

    @Test
    public void testItemSearch() throws InterruptedException {
        getDriver().get("https://www.applebees.com/en");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        getDriver().manage().window().maximize();
        Thread.sleep(1000);

        WebElement menuButton = getDriver().findElement(By.xpath("(//ul/li/a[@href='/en/menu'])[1]"));
        menuButton.click();

        WebElement searchMenu = getDriver().findElement(By.xpath("//a[@href='/en/menu-search']"));
        searchMenu.click();

        WebElement textBox = getDriver().findElement(By.xpath("//input[contains(@class,'menu-search')]"));
        textBox.sendKeys("salmon");

        WebElement massage = getDriver().findElement(By.xpath("//a[@href='/en/menu/seafood/blackened-cajun-salmon']"));
        String value = massage.getText();

        Assert.assertEquals(value, "Blackened Cajun Salmon");
    }

    @Test
    public void testLocationList() {
        List<String> expectedLocationsNames = List.of("Bensalem", "Doylestown", "Langhorne", "Levittown", "Perkasie", "Quakertown", "Yardley-Makefield");
        List<String> actualLocationNames = new ArrayList<>();

        getDriver().get("https://buckslib.org/");
        getDriver().manage().window().maximize();

        getDriver().findElement(By.xpath("//*[contains(text(),' Locations')]")).click();

        for (String locationName : expectedLocationsNames) {
            String locationText = getDriver()
                    .findElement(By.xpath("//div[@id='wp-faqp-accordion-1']/div/div[@class='faq-title']/h4[text()='" + locationName + "']")).getText();
            actualLocationNames.add(locationText);
        }

        Assert.assertEquals(actualLocationNames, expectedLocationsNames);
    }

    @Test
    public void linkTest() {
        getDriver().get("https://the-internet.herokuapp.com/redirector");
        getDriver().manage().window().maximize();

        getDriver().findElement(By.id("redirect")).click();
        getDriver().findElement(By.xpath("//*[@id=\"content\"]/div/ul/li[3]/a")).click();
        String textStatusCode = getDriver().findElement(By.xpath("//*[@id=\"content\"]/div/p")).getText();
        boolean isStatus = textStatusCode.contains("404 status code");

        Assert.assertTrue(isStatus);
    }

    @Test
    public void datePicker() {
        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement myDate = getDriver().findElement(By.name("my-date"));
        myDate.click();

        WebElement weekDay = getDriver().findElement(By.xpath("//thead/tr[3]/th[1]"));
        String text = weekDay.getText();

        Assert.assertEquals(text,"Su");
    }

    @Test
    public void testSwagLogin() {
        getDriver().get("https://www.saucedemo.com/?ref=hackernoon.com");

        WebElement usernameInput = getDriver().findElement(By.id("user-name"));
        usernameInput.sendKeys("standard_user");

        WebElement passwordInput = getDriver().findElement(By.id("password"));
        passwordInput.sendKeys("secret_sauce");

        WebElement loginButton = getDriver().findElement(By.id("login-button"));
        loginButton.click();
        WebElement shopCartButton = getDriver().findElement(By.id("shopping_cart_container"));

        Assert.assertTrue(shopCartButton.isDisplayed(), "Shop cart doesn't displayed");
    }

    @Test
    public void testPlaceAnOrder() {
        getDriver().get("https://www.papajohns.com/");
        getDriver().manage().window().maximize();

        getDriver().findElement(By.xpath("//*[@href=' /order/menu']")).click();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        getDriver().findElement(By.cssSelector("[aria-label=\"Order Now Pepperoni Pizza\"]")).click();

        WebElement text = getDriver().findElement(By.xpath("//h2[text()='Find Your Store']"));
        String value = text.getText();
        Assert.assertEquals(value,"FIND YOUR STORE");
    }
    @Test
    public void testYMCA() {
        getDriver().get("https://ymcacapecod.org/");

        WebElement textBox = getDriver().findElement(By.className("field"));
        WebElement searchButton = getDriver().findElement(By.className("submit"));
        textBox.sendKeys("pool");
        searchButton.click();

        getDriver().findElement(By.xpath("//footer//a[@href='/programs/swimming/']")).click();

        WebElement text = getDriver().findElement(By.xpath("//h1[@class='program-hero__title']"));
        String value = text.getText();
        Assert.assertEquals(value, "SWIM LESSONS");
    }

    @Test
    public void testRegistration() {
        getDriver().get("https://ymcacapecod.org/");
        getDriver().manage().window().maximize();

        getDriver().findElement(By.xpath("//*[@href='/join/register/']")).click();
        String originalWindow = getDriver().getWindowHandle();
        Assert.assertEquals(getDriver().getWindowHandles().size(), 1);

        getDriver().findElement(By.xpath("//*[@title='Register']")).click();
        for (String windowHandle : getDriver().getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                getDriver().switchTo().window(windowHandle);
                break;
            }
        }

        getDriver().findElement(By.xpath("//a[contains(text(),'Login')]")).click();

        WebElement username = getDriver().findElement(By.xpath("//input[@id='user_name']"));
        username.sendKeys("1234@gmail.com");
        getDriver().findElement(By.id("submit_button")).click();

        String signup = getDriver().findElement(By.xpath("//h2")).getText();

        Assert.assertEquals(signup,"Sign up for an account");
    }

    @Test
    public void testMouseHover() {
        getDriver().get("https://play1.automationcamp.ir/");

        WebElement buttonMouseViewPage = getDriver().findElement(By.xpath("//a[starts-with(@*,'mouse')]"));
        buttonMouseViewPage.click();

        WebElement elementToHoverOver = getDriver().findElement(By.xpath("//button[@class='dropbtn']"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(elementToHoverOver).perform();

        WebElement subMenuItem = getDriver().findElement(By.id("dd_python"));
        subMenuItem.click();

        WebElement text = getDriver().findElement(By.id("hover_validate"));
        String value = text.getText();

        Assert.assertEquals(value, "Python");
    }

    @Test
    public void testDatalist() {

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement dataList = getDriver().findElement(By.name("my-datalist"));
        dataList.click();

        WebElement options = getDriver().findElement(By.cssSelector("datalist#my-options option:nth-child(2)"));
        String optionValue = options.getAttribute("value");
        dataList.sendKeys(optionValue);

        Assert.assertEquals(optionValue,"New York");

    }

    @Test
    public void testPositiveLogin() throws InterruptedException {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().manage().window().maximize();
        WebElement usernameField = getDriver().findElement(By.id("user-name"));
        usernameField.sendKeys("standard_user");
        WebElement passwordField = getDriver().findElement(By.id("password"));
        passwordField.sendKeys("secret_sauce");

        getDriver().findElement(By.id("login-button")).click();
        String actualUrl="https://www.saucedemo.com/inventory.html";
        String expectedUrl= getDriver().getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);
    }

    @Test
    public  void testSwag() {
        getDriver().get("https://www.saucedemo.com/v1/");
        getDriver().manage().window().maximize();

        WebElement userNameField = getDriver().findElement(By.id("user-name"));
        userNameField.sendKeys("locked_out_user");

        WebElement passwordField = getDriver().findElement(By.id("password"));
        passwordField.sendKeys("secret_sauce");

        getDriver().findElement(By.id("login-button")).click();

        WebElement actualErrorMessage = getDriver().findElement(By.xpath("//h3[@data-test='error']"));

        Assert.assertEquals(actualErrorMessage.getText(), "Epic sadface: Sorry, this user has been locked out.");
    }
}