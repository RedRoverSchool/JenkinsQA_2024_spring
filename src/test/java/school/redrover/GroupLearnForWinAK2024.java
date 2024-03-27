package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import org.testng.annotations.Test;
import org.junit.Assert;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.time.Duration;
import java.util.Arrays;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GroupLearnForWinAK2024 {
    String login = "academic198405@gmail.com";
    String myAccount = "[aria-label='person']";
    String password = "StateOf2024!";

    @Test
    public void testVisiableSupercaliberSLR98() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));


        driver.get("https://www.trekbikes.com/us/en_US/");
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector(myAccount)).click();
        driver.findElement(By.cssSelector("span[data-v-27f1dc12]")).click();
        driver.findElement(By.id("j_username")).sendKeys(login);
        driver.findElement(By.id("j_password")).sendKeys(password);
        driver.findElement(By.xpath("//span[text()='Log in']")).click();
        driver.findElement(By.cssSelector(myAccount)).click();


        driver.findElement(By.xpath("//span[@data-v-27f1dc12='' and normalize-space(text())='My account']")).click();
        System.out.println(10);
//        driver.findElement(By.id("nav-link-'viewBikesMountainCategoryAll-large")).click();
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//div[@id='layout']//button[@aria-label='Close Modal']"));
        actions.moveToElement(element).click().perform();
    }


    @Test
    public void testVisiableSupercaliberSLR982() throws AWTException, InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));


        driver.get("https://www.trekbikes.com/us/en_US/");
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector(myAccount)).click();
        driver.findElement(By.cssSelector("span[data-v-27f1dc12]")).click();
        driver.findElement(By.id("j_username")).sendKeys(login);
        driver.findElement(By.id("j_password")).sendKeys(password);
        driver.findElement(By.xpath("//span[text()='Log in']")).click();


        driver.findElement(By.xpath("//a[@id='expandMountainBikesMainMenu-large']")).click();
        driver.findElement(By.xpath("(//span[text()='Shop all mountain bikes'])[2]")).click();

        Robot robot = new Robot();
        // Задержка перед началом движения мыши
        TimeUnit.SECONDS.sleep(1);
        // Получаем размеры экрана
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Двигаем мышь к центру экрана
        int centerX = 1160;
        int centerY = 580;
        robot.mouseMove(centerX, centerY);

        // Задержка после движения мыши
        TimeUnit.SECONDS.sleep(2);


        // Выполняем клик левой кнопкой мыши в указанной точке
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        WebElement element = driver.findElement(By.xpath("//h3[contains(text(), 'Fuel EXe 8 GX AXS T-Type')]"));
//        String fuelExeText = element.getText();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int subm = element.getLocation().getY();
        for (int i = 0; i < subm; i += 20) {
            js.executeScript("window.scrollTo(0, " + i + ")");
            Thread.sleep(50);
        }
//        WebElement element = driver.findElement(By.xpath("//h3[contains(text(), 'Fuel EXe 8 GX AXS T-Type')]"));
//        String fuelExeText = element.getText();
//        JavascriptExecutor js = (JavascriptExecutor) driver;

// Прокручиваем страницу до того, чтобы элемент был виден на экране
        js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });", element);
//        Assert.assertEquals(fuelExeText, "Fuel EXe 8 GX AXS T-Type");
        driver.quit();
    }

    @Test
    public void CheckMenuElement() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));


        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();

        System.out.println(1);


        WebElement element = driver.findElement(By.xpath("//*[text()='Book Store Application']"));
        System.out.println(2);
        String fuelExeText = element.getText();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        System.out.println(3);
        int subm = element.getLocation().getY();
        System.out.println(4);
        for (int i = 0; i < subm; i += 20) {
            js.executeScript("window.scrollTo(0, " + i + ")");

        }


        WebElement element1 = driver.findElement(By.xpath("//*[text()='Elements']"));
        element1.click();


        // Список названий элементов для проверки
        List<String> expectedMenuItems = Arrays.asList(
                "Text Box", "Check Box", "Radio Button", "Web Tables", "Buttons",
                "Links", "Broken Links - Images", "Upload and Download", "Dynamic Properties"
        );
        WebElement menuList = driver.findElement(By.xpath("//ul[@class='menu-list']"));
        String menuItemsText = menuList.getText();

        for (String expectedItem : expectedMenuItems) {
            Assert.assertTrue("Меню не содержит элемент: " + expectedItem, menuItemsText.contains(expectedItem));
        }
    }

    @Test
    public void CheckWeAreOnPracticeFormPage() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//h5[text()='Forms']")).click();
        driver.findElement(By.xpath("//span[text()='Practice Form']")).click();

        WebElement practiceFormTitle = driver.findElement(By.xpath("//span[text()='Practice Form']"));

        Assert.assertTrue( practiceFormTitle.isDisplayed());
        driver.quit();
    }
    @Test
    public void CheckDataInput() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//h5[text()='Forms']")).click();
        driver.findElement(By.xpath("//span[text()='Practice Form']")).click();

        driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("Artuom");
        driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Kudryashov");
        WebElement email = driver.findElement(By.xpath("//input[@id='userEmail']"));
        email.sendKeys("artuomkudryashov@gmail.com");
        driver.findElement(By.xpath("//label[@for='gender-radio-1']")).click();
        driver.findElement(By.xpath("//input[@placeholder='Mobile Number']")).sendKeys("66315652253");
        driver.findElement(By.xpath("//input[@id='dateOfBirthInput']")).click();
        driver.findElement(By.xpath("//select[@class='react-datepicker__year-select']")).click();
        driver.findElement(By.xpath("//option[@value='2056']")).click();
        driver.findElement(By.xpath("//select[@class='react-datepicker__month-select']")).click();
        driver.findElement(By.xpath("//option[@value='7']")).click();
        driver.findElement(By.xpath("//*[contains(@aria-label,' August 14th')]")).click();
//        WebElement subject = driver.findElement(By.xpath("//div[@class='subjects-auto-complete__control css-yk16xz-control']/div"));
//        subject.click();
//
//
//
//        subject.sendKeys("fdfd");
        driver.findElement(By.xpath("//label[@for='hobbies-checkbox-1']")).click();
        driver.findElement(By.xpath("//textarea[@id='currentAddress']")).sendKeys("Gotham City, Wayne Manor");

        WebElement submit = driver.findElement(By.xpath("//button[@id='submit']"));
//        String fuelExeText = element.getText();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int subm = submit.getLocation().getY();
        for (int i = 0; i < subm; i += 20) {
            js.executeScript("window.scrollTo(0, " + i + ")");
            Thread.sleep(50);

        }
        submit.click();


        WebElement thanks = driver.findElement(By.xpath("//div[text()='Thanks for submitting the form']"));
        thanks.getText();
        Assert.assertTrue( thanks.isDisplayed());
        String expectedText ="Thanks for submitting the form";

        Assert.assertEquals(expectedText, thanks.getText());
        driver.quit();
    }
}

