package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class GroupJavaAutoQARRSchoolTest extends BaseTest {

    @Test
    public void testReturnToHomePageFromInventoryDetailsCard() {

        WebDriver driver = getDriver();
        driver.get("https://www.saucedemo.com");

        driver.findElement(By.name("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.name("login-button")).click();

        WebElement inventoryName = driver.findElement(By.xpath("//div[text() = 'Sauce Labs Bike Light']"));
        inventoryName.click();

        WebElement backToProductBtn = driver.findElement(By.xpath("//button[@id = 'back-to-products']"));
        backToProductBtn.click();

        WebElement headerProductsOnTheHomePage = driver.findElement(By.xpath("//span[text() = 'Products']"));
        String value = headerProductsOnTheHomePage.getText();

        Assert.assertEquals(value, "Products");
    }
    @Test
    public void testDemoQAPracticeForm(){
        getDriver().get("https://demoqa.com/automation-practice-form");

        WebElement firstName = (new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='firstName']"))));
        firstName.sendKeys("Bob");

        getDriver().findElement(By.xpath("//input[@id='lastName']")).sendKeys("Boboc");

        getDriver().findElement(By.xpath("//input[@id='userEmail']")).sendKeys("test@gmail.com");

        getDriver().findElement(By.xpath("//input[@id='userNumber']")).sendKeys("1234567890");

        getDriver().findElement(By.xpath("//input[@id='gender-radio-1']/following-sibling::label['::after']")).click();

        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, 1600)");
        getDriver().findElement(By.id("submit")).click();

        WebElement title = (new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("example-modal-sizes-title-lg"))));
        String value = title.getText();
        Assert.assertEquals(value, "Thanks for submitting the form");
    }

    @Test
    public void testTasksAreaWithDoubleclick() {

        getDriver().get("https://thecode.media/");

        WebElement searchArea = getDriver().findElement(By.className("tab-questions"));

        Action myAction = new Actions(getDriver()).doubleClick(searchArea).build();
        myAction.perform();

        WebElement foundText = getDriver().findElement(By.xpath("(//h1[@class='search__title'])"));

        Assert.assertEquals(foundText.getText(), "Как решить");
    }

    @Test
    public void testCheckTextField() {

        getDriver().get("https://thecode.media/");

        WebElement searchButton = getDriver().findElement(By.className("heading-search__open"));
        searchButton.click();

        WebElement searchText = getDriver().findElement(By.className("heading-search__input"));
        searchText.sendKeys("Api");

        searchButton.click();

        WebElement foundText = getDriver().findElement(By.className("search__title"));

        Assert.assertEquals(foundText.getText(),"api");
    }

    @Test
    public void testAuthorizationVprokat() {
        getDriver().get("https://vprokat.online/");

        getDriver().findElement(By.cssSelector("a[aria-label=\"Профиль\"]")).click();

        getDriver().findElement(By.cssSelector("[autocomplete=email]")).sendKeys("1716.06+111@gmail.com");
        getDriver().findElement(By.cssSelector("[autocomplete=current-password]")).sendKeys("parol123");
        getDriver().findElement(By.xpath("//div//form//div//button[contains(text(), \"Войти\")]")).click();

        getDriver().findElement(By.cssSelector("a[aria-label=\"Профиль\"]")).click();
        getDriver().findElement(By.xpath("//body//div//main//div//button[contains(text(), \"Выйти\")]")).click();
    }
}
