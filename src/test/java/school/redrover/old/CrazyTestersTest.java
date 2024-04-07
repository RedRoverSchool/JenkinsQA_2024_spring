package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

@Ignore
public class CrazyTestersTest extends BaseTest {

    @Test
    public void testDragAndDrop() {
        getDriver().get("https://the-internet.herokuapp.com/drag_and_drop");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        WebElement columnA = getDriver().findElement(By.id("column-a"));
        WebElement columnB = getDriver().findElement(By.id("column-b"));
        new Actions(getDriver())
                .dragAndDrop(columnA, columnB)
                .perform();

        Assert.assertEquals(columnB.getText(), "A");
    }

    @Test
    public void testSubmitForm() {
        WebDriver driver = getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("https://demoqa.com/automation-practice-form");

        WebElement textName = driver.findElement(By.id("firstName"));
        textName.sendKeys("Anastasia");

        WebElement textLastName = driver.findElement(By.id("lastName"));
        textLastName.sendKeys("Belugina");

        WebElement radioButton = driver.findElement(By.xpath("//label[text()='Female']"));
        radioButton.click();

        WebElement textMobile = driver.findElement(By.id("userNumber"));
        textMobile.sendKeys("1234567890");

        WebElement submitButton = driver.findElement(By.id("submit"));
        js.executeScript("arguments[0].scrollIntoView();", submitButton);
        submitButton.click();

        WebElement result = driver.findElement(By.id("example-modal-sizes-title-lg"));
        String resultText = result.getText();

        Assert.assertEquals("Thanks for submitting the form", resultText);
    }

    @Test

    public void testDemoQaElementsRadioButtonYes() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");

        Thread.sleep(1000);

        WebElement buttonBookStoreApplication = driver.findElement(
                By.xpath("//h5[text()='Book Store Application']"));
        new Actions(driver).scrollToElement(buttonBookStoreApplication).perform();

        WebElement buttonElements = driver.findElement(
                By.xpath("//h5[text()='Elements']"));
        buttonElements.click();

        WebElement buttonRadioButton = driver.findElement(
                By.xpath("//span[text()='Radio Button']"));
        buttonRadioButton.click();

        WebElement buttonYesRadio = driver.findElement(
                By.xpath("//label[@class='custom-control-label'][1]"));
        buttonYesRadio.click();

        WebElement text = driver.findElement(
                By.xpath("//span[@class='text-success']"));
        String resultText = text.getText();

        Assert.assertEquals(resultText, "Yes");

        driver.quit();

    }

    @Test
    public void placeholderFillTest(){
        WebDriver driver = getDriver();
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.saucedemo.com/");

        WebElement textBoxUser = driver.findElement(By.name("user-name"));
        textBoxUser.sendKeys("standard_user");
        WebElement textBoxPass = driver.findElement(By.id("password"));
        textBoxPass.sendKeys("secret_sauce");
        WebElement submitButton = driver.findElement(By.id("login-button"));

        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html");

    }
    @Test
    public void testW3schools() {

        WebDriver driver = getDriver();
        driver.get("https://www.w3schools.com/");

        WebElement link = driver.findElement(By.linkText("Java Tutorial"));
        link.click();

        WebElement menu = driver.findElement(By.cssSelector("#menubtn_container > span > a"));
        menu.click();

        WebElement link2 = driver.findElement(By.linkText("Java Variables"));
        link2.click();

        WebElement text1 = driver.findElement(By.name("ex1"));
        text1.sendKeys("String");

        WebElement text2 = driver.findElement(By.name("ex2"));
        text2.sendKeys("carName");

        WebElement text3 = driver.findElement(By.name("ex3"));
        text3.sendKeys("\"Volvo\"");

        WebElement button = driver.findElement(By.cssSelector("#w3-exerciseform > div > button"));
        String resultText = button.getText();
        Assert.assertEquals(resultText, "Submit Answer »");
        button.click();
    }

    @Test
    public void testTest3() {
        WebDriver driver = getDriver();
        driver.get("https://www.nike.com/es/");

        WebElement username = driver.findElement(By.id("VisualSearchInput"));
        username.sendKeys("Jordan");

        WebElement submitButton2 = driver.findElement(By.xpath("//button[@*='Buscar']"));
        submitButton2.click();

        String pageUrl = driver.getCurrentUrl();
        Assert.assertEquals(pageUrl, "https://www.nike.com/es/w?q=Jordan&vst=Jordan");
    }

    @Test
    public void testTest2() {
        WebDriver driver = getDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement username = driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");

        WebElement submitButton = driver.findElement(By.id("login-button"));
        submitButton.click();

        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Swag Labs");
    }
}
