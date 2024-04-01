package school.redrover;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import school.redrover.runner.BaseTest;
import static org.testng.Assert.assertTrue;

public class GroupLearnForWinAK2024Test extends BaseTest {
    private static final String LOGIN = "academic198405@gmail.com";
    private static final String MY_ACCOUNT = "[aria-label='person']";
    private static final String PASSWORD = "StateOf2024!";
    private static final String URL = "https://www.trekbikes.com/us/en_US/";
    private static final String SECOND_URL = "https://demoqa.com/";

    @Test
    public void testLoginSuccessful()  {
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        driver.get(URL);
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector(MY_ACCOUNT)).click();
        driver.findElement(By.cssSelector("span[data-v-27f1dc12]")).click();
        driver.findElement(By.id("j_username")).sendKeys(LOGIN);
        driver.findElement(By.id("j_password")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//span[text()='Log in']")).click();
        driver.findElement(By.cssSelector(MY_ACCOUNT)).click();

        driver.findElement
                (By.xpath("//button[@class='nav-utility-bar-control__button tippy-active']")).click();
        WebElement logout = driver.findElement(By.xpath("//a[@content='Log out']"));
        String actText = logout.getText();
        String expText = "Log out";
        Assert.assertEquals(actText,expText);
    }

    @Test
    public void testVisibleSuperCaliberSLR982() throws AWTException, InterruptedException {
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        driver.get(URL);
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector(MY_ACCOUNT)).click();
        driver.findElement(By.cssSelector("span[data-v-27f1dc12]")).click();
        driver.findElement(By.id("j_username")).sendKeys(LOGIN);
        driver.findElement(By.id("j_password")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//span[text()='Log in']")).click();

        driver.findElement(By.xpath("//a[@id='expandMountainBikesMainMenu-large']")).click();
        driver.findElement(By.xpath("(//span[text()='Shop all mountain bikes'])[2]")).click();

        Robot robot = new Robot();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = 1160;
        int centerY = 580;
        robot.mouseMove(centerX, centerY);
        TimeUnit.SECONDS.sleep(2);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        WebElement element = driver.findElement(By.xpath("//h3[contains(text(), 'Fuel EXe 8 GX AXS T-Type')]"));
        String fuelExeText = element.getText();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });", element);
        Assert.assertEquals(fuelExeText, "Fuel EXe 8 GX AXS T-Type");
    }

    @Test
    public void testCheckMenuElement() {
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        driver.get(SECOND_URL);
        driver.manage().window().maximize();

        WebElement element = driver.findElement(By.xpath("//*[text()='Book Store Application']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int subm = element.getLocation().getY();
        for (int i = 0; i < subm; i += 20) {
            js.executeScript("window.scrollTo(0, " + i + ")");
        }

        WebElement element1 = driver.findElement(By.xpath("//*[text()='Elements']"));
        element1.click();

        List<String> expectedMenuItems = Arrays.asList(
                "Text Box", "Check Box", "Radio Button", "Web Tables", "Buttons",
                "Links", "Broken Links - Images", "Upload and Download", "Dynamic Properties"
        );
        WebElement menuList = driver.findElement(By.xpath("//ul[@class='menu-list']"));
        String menuItemsText = menuList.getText();

        for (String expectedItem : expectedMenuItems) {
            assertTrue(menuItemsText.contains(expectedItem), "Меню не содержит элемент: " + expectedItem);
        }
    }

    @Test
    public void testCheckWeAreOnPracticeFormPage() {
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        driver.get(SECOND_URL);
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//h5[text()='Forms']")).click();
        driver.findElement(By.xpath("//span[text()='Practice Form']")).click();

        WebElement practiceFormTitle = driver.findElement(By.xpath("//span[text()='Practice Form']"));

        assertTrue( practiceFormTitle.isDisplayed());
    }

    @Test
    public void testCheckDataInput() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        String expectedText ="Thanks for submitting the form";

        driver.get(SECOND_URL);
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//h5[text()='Forms']")).click();
        driver.findElement(By.xpath("//span[text()='Practice Form']")).click();

        driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("Artuom");
        driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Kudryashov");
        WebElement email = driver.findElement(By.xpath("//input[@id='userEmail']"));
        email.sendKeys("artuomkudryashov@gmail.com");
        driver.findElement(By.xpath("//label[@for='gender-radio-1']")).click();
        driver.findElement(By.xpath("//input[@placeholder='Mobile Number']")).sendKeys("6666666666");
        driver.findElement(By.xpath("//input[@id='dateOfBirthInput']")).click();
        driver.findElement(By.xpath("//select[@class='react-datepicker__year-select']")).click();
        driver.findElement(By.xpath("//option[@value='2056']")).click();
        driver.findElement(By.xpath("//select[@class='react-datepicker__month-select']")).click();
        driver.findElement(By.xpath("//option[@value='7']")).click();
        driver.findElement(By.xpath("//*[contains(@aria-label,' August 14th')]")).click();
        driver.findElement(By.xpath("//label[@for='hobbies-checkbox-1']")).click();
        driver.findElement(By.xpath("//textarea[@id='currentAddress']")).sendKeys("Gotham City, Wayne Manor");
        WebElement submit = driver.findElement(By.xpath("//button[@id='submit']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int subm = submit.getLocation().getY();
        for (int i = 0; i < subm; i += 20) {
            js.executeScript("window.scrollTo(0, " + i + ")");
            Thread.sleep(50);
        }
        submit.click();

        WebElement thanks = driver.findElement(By.xpath("//div[text()='Thanks for submitting the form']"));
        thanks.getText();
        assertTrue( thanks.isDisplayed());

        Assert.assertEquals(thanks.getText(), expectedText);
    }

    @Test
    public void testChamplainValleyTrailIsMenu() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        driver.get("https://www.google.com/");
        driver.manage().window().maximize();
        WebElement search_field = driver.findElement(By.xpath( "//textarea[@class='gLFyf']"));
        search_field.sendKeys("Empire State Trail");
        search_field.sendKeys(Keys.ENTER);
        WebElement eST = driver.findElement(By.xpath("(//cite[text()='https://empiretrail.ny.gov'])[1]"));
        eST.click();

        WebElement viewMap = driver.findElement(By.xpath("//a[text()='View Map']"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({ behavior:" +
                " 'smooth', block: 'center', inline: 'center' });", viewMap);
        viewMap.click();

        WebElement hVGT = driver.findElement(By.xpath("//a[@data-trail='hudson-valley-greenway-trail']"));
        WebElement eCT = driver.findElement(By.xpath("//a[@data-trail='erie-canalway-trail']"));
        WebElement cVT = driver.findElement(By.xpath("//a[@data-trail='champlain-valley-trail']"));

        WebElement insideNYCToP = driver.findElement(By.xpath("//h4[text()='New York City to Poughkeepsie']"));
        String textInSideNYCToP = insideNYCToP.getText();
        WebElement insideBtoR = driver.findElement(By.xpath("//h4[contains(text(), 'Rochester') and contains(text(), 'Buffalo')]"));
        String textInSideBtoR = insideBtoR.getText();
        WebElement inSideECT = driver.findElement(By.xpath("//h4[contains(text(), 'Albany') and contains(text(), 'Whitehall')]"));
        String textInSideEct = inSideECT.getText();
        List <WebElement> menuWays = driver.findElements(By.xpath("//div[@class='accordion']"));
        for (WebElement menu : menuWays) {
            boolean containsText = menu.getText().contains(textInSideNYCToP) ||
                    menu.getText().contains(textInSideBtoR) ||
                    menu.getText().contains(textInSideEct);
            assertTrue(containsText, "Expected text not found in menu: " + menu.getText());
        }
    }
}

