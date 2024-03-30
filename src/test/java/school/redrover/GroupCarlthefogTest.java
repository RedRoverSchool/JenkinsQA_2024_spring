package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupCarlthefogTest {

    private final static String FULL_NAME = "John Smith";
    private final static String EMAIL = "john.smith@gmail.com";
    private final static String ADDRESS = "5th Ave, New York";
    private final static String PERM_ADDRESS = "5th Ave, New York";


    @Test
    public void testSaucedemo() {
        String expectedPageName = "Swag Labs";

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String actualPageName = driver.findElement(By.xpath("//div[@class= 'app_logo']")).getText();

        Assert.assertEquals(expectedPageName, actualPageName);
        driver.quit();
    }

    @Test
    public void testPageHeader() {
        String expectedPageHeader1 = "99 Bottles of Beer";
        String expectedPageHeader2 = "Welcome to 99 Bottles of Beer";

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.99-bottles-of-beer.net/");

        String actualPageHeader1 = driver.findElement(By.xpath("//h1")).getText();
        String actualPageHeader2 = driver.findElement(By.xpath("//div[@id='main']//h2")).getText();

        Assert.assertEquals(expectedPageHeader1, actualPageHeader1);
        Assert.assertEquals(expectedPageHeader2, actualPageHeader2);

        driver.quit();
    }

    @Test
    public void testElementsList() {
        int expectedElementsQuantity = 6;
        List<String> expectedElementsHeader = new ArrayList<>(Arrays.asList(
                "Elements",
                "Forms",
                "Alerts, Frame & Windows",
                "Widgets",
                "Interactions",
                "Book Store Application"));

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");

        int actualElementsQuantity = driver.findElements(By.xpath("//div[@class='card-body']")).size();

        List<WebElement> elementsHeaderList = driver.findElements(By.xpath("//div[@class='card-body']"));
        List<String> actualElementsHeader = WebElementsToString(elementsHeaderList);

        Assert.assertEquals(expectedElementsQuantity, actualElementsQuantity);
        Assert.assertEquals(expectedElementsHeader,actualElementsHeader);

        driver.quit();
    }

    public static List<String> WebElementsToString(List<WebElement> elementsHeaderList) {
        List<String> stringList = new ArrayList<>();
        for (WebElement element : elementsHeaderList) {
            stringList.add(element.getText());
        }

        return stringList;
    }

    @Test
    public void testElementsButtonMenuList() {
        int expectedMenuListQuantity = 9;
        List<String> expectedItemName = new ArrayList<>(Arrays.asList(
                "Text Box",
                "Check Box",
                "Radio Button",
                "Web Tables",
                "Buttons",
                "Links",
                "Broken Links - Images",
                "Upload and Download",
                "Dynamic Properties"));

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");

        driver.findElement(By.xpath("//div[@id='app']/div/div/div[2]/div/div[1]/div/div[1]")).click();
        String currentURL = driver.getCurrentUrl();

        int actualMenuListQuantity = driver.findElements(By.xpath("//div[@class='element-list collapse show']//li"))
                .size();
        List<WebElement> elementsHeaderList = driver.findElements(By.xpath("//div[@class='element-list collapse show']//li"));
        List<String> actualItemName = WebElementsToString(elementsHeaderList);

        Assert.assertTrue(currentURL.contains("elements"));
        Assert.assertEquals(expectedMenuListQuantity, actualMenuListQuantity);
        Assert.assertEquals(expectedItemName, actualItemName);

        driver.quit();
    }

    @Test
    public void testLibrary() {

        String expectedHeader1 = "For Adults";
        String expectedHeader2 = "Kids Recommended";

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.olathelibrary.org/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        WebElement servicesTab = driver.findElement(By.xpath("//a[text()='Services']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(servicesTab).build().perform();

        driver.findElement(By.xpath("//a[text()='Adult Book Recommendations']")).click();
        WebElement header1 = driver.findElement(By.xpath("//header[@id='widget_6876_11653_2227']"));
        String actualHeader1 = header1.getText();

        Assert.assertEquals(actualHeader1, expectedHeader1);

        WebElement kidsTab = driver.findElement(By.xpath("//a[text()='Kids']"));
        actions.moveToElement(kidsTab).build().perform();

        driver.findElement(By.xpath("//a[text()='Kids Recommended']")).click();
        WebElement header2 = driver.findElement(By.xpath("//header[@id='widget_4280_11723_2315']"));
        String actualHeader2 = header2.getText();

        Assert.assertEquals(actualHeader2, expectedHeader2);

        driver.quit();
    }

    @Test
    public void testTexBoxUseForm() {

        String expectedHeader = "Text Box";

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/text-box");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        String actualHeader = driver.findElement(By.xpath("//div[@id='app']//h1")).getText();

        driver.findElement(By.cssSelector("input#userName")).sendKeys(FULL_NAME);
        driver.findElement(By.cssSelector("input#userEmail")).sendKeys(EMAIL);
        driver.findElement(By.cssSelector("textarea#currentAddress")).sendKeys(ADDRESS);
        driver.findElement(By.cssSelector("textarea#permanentAddress")).sendKeys(PERM_ADDRESS);

        Actions act = new Actions(driver);
        act.sendKeys(Keys.PAGE_DOWN).build().perform();
        WebElement submitButton = driver.findElement(By.cssSelector("button#submit"));
        submitButton.click();

        WebElement expectedName = driver.findElement(By.cssSelector("p#name"));
        WebElement expectedEmail = driver.findElement(By.cssSelector("p#email"));
        WebElement expectedCurrentAddress = driver.findElement(By.cssSelector("p#currentAddress"));
        WebElement expectedPermanentAddress = driver.findElement(By.cssSelector("p#permanentAddress"));

        Assert.assertEquals(actualHeader, expectedHeader);
        Assert.assertEquals(expectedName.getText(), "Name:" + FULL_NAME);
        Assert.assertEquals(expectedEmail.getText(), "Email:" + EMAIL);
        Assert.assertEquals(expectedCurrentAddress.getText(), "Current Address :" + ADDRESS);
        Assert.assertEquals(expectedPermanentAddress.getText(), "Permananet Address :" + PERM_ADDRESS);

        driver.quit();
    }
}
