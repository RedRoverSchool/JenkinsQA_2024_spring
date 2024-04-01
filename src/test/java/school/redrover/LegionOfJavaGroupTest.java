package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

import static org.openqa.selenium.By.className;
import static org.testng.Assert.assertTrue;

public class LegionOfJavaGroupTest extends BaseTest {

    @Test
    public void testFerosorSearch() {

        getDriver().get("https://ferosor.cl");
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        WebElement textBox =  getDriver().findElement(By.name("s"));
        textBox.sendKeys("alimento");

        getDriver().findElement(By.cssSelector("[type='submit']")).click();
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        getDriver().get("https://ferosor.cl/jardin-y-mascotas/262-alimento-para-perro-cachorro-fit-formula-saco-10-kg-130622000123.html");
        String result = String.valueOf(getDriver().findElement(By.className("h1")).getText());

        Assert.assertEquals(result, "Alimento para Perro cachorro FIT FORMULA Saco 10 kg");
        }

    @Test
    public void testFerosorLogin() {

            getDriver().get("https://ferosor.cl");
            getDriver().findElement(className("login")).click();

            WebElement email =  getDriver().findElement(By.className("form-control"));
            email.sendKeys("test@test.com");

            WebElement password =  getDriver().findElement(By.className("js-child-focus"));
            password.sendKeys("12345");

            getDriver().findElement(By.id("submit-login")).click();
            String result = String.valueOf( getDriver().findElement(By.className("page-header")).getText());

            Assert.assertEquals(result, "Su cuenta");
    }

    @Ignore
    @Test
    public void testSearch() {

        getDriver().get("https://www.amazon.com");

        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement searchBox = getDriver().findElement(By.id("twotabsearchtextbox"));
        WebElement submitButton = getDriver().findElement(By.id("nav-search-submit-button"));

        searchBox.sendKeys("RaisedGardenBeds");
        submitButton.click();

        WebElement message = getDriver().findElement(By.xpath("//div[@class='a-sectiona-spacing-smalla-spacing-top-small']"));
        String value = message.getText();
        String[] arr = value.split("\"");
        Assert.assertEquals(arr[1], "RaisedGardenBeds");

    }
    @Test
    public void testFindElement(){
        getDriver().get("https://www.w3schools.com/");

        WebElement textBox=getDriver().findElement(By.id("search2"));
        textBox.sendKeys("Java Tutorial");
        WebElement button=getDriver().findElement(By.id("learntocode_searchbtn"));
        button.click();
        WebElement text=getDriver().findElement(By.xpath("//*[@id=\"leftmenuinnerinner\"]/a[1]"));

        Assert.assertEquals(text.getText(),"Java HOME");
    }

    @Test
    public void testSeleniumTrainingButton() {

        getDriver().get("https://www.toolsqa.com/");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href = '/selenium-training?q=headers']")));

        WebElement button = getDriver().findElement(By.xpath("//a[@href = '/selenium-training?q=headers']"));
        button.click();

        WebElement text = getDriver().findElement(By.xpath("//div[@class='enroll__heading']"));
        String value = text.getText();

        Assert.assertEquals(value, "Selenium Certification Training | Enroll Now | Study Online");
    }

    @Test
    public void testMyPinterest() {
        WebDriver driver = getDriver();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("https://www.pinterest.com/");

        String title = driver.getTitle();
        Assert.assertEquals("Pinterest", title);

        WebElement buttonLogIn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Log in')]")));
        buttonLogIn.click();

        WebElement userEmailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='email']")));
        userEmailInput.sendKeys("ii8281294@gmail.com");

        WebElement userPasswordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='password']")));
        userPasswordInput.sendKeys("test2024!");

        WebElement buttonSubmit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));
        buttonSubmit.click();

        WebElement homeWebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Home")));

        assertTrue(homeWebElement.isDisplayed());
        assertTrue(homeWebElement.isEnabled());
    }

    @Test
    public void testLiveMomentousStore() {
        WebDriver driver = getDriver();
        driver.get("https://www.livemomentous.com/");

        driver.findElement(By.xpath("//button[contains(@class, 'mobile-menu-trigger')]")).click();
        driver.findElement(By.xpath("//li[contains(@class, 'navigation-list')]/a[@href = '/collections/best-sellers']")).click();
        driver.findElement(By.xpath("//div[@aria-label='close']")).click();

        WebElement item = driver.findElement(By.xpath("//h3[text() = 'Creatine']"));
        Assert.assertTrue(item.isDisplayed());
    }

    @Test
    public void testNobullSearch() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://www.nobullproject.com/");

        driver.findElement(By.id("onetrust-close-btn-container")).click();
        driver.findElement(By.xpath("//*[@data-target = 'search-button']")).click();
        driver.findElement(By.xpath("//input[@name = 'q']")).sendKeys("Tank");
        driver.findElement(By.xpath("//button[@class = 'text-black'][1]")).click();

        Thread.sleep(8000);

        driver.switchTo().frame("attentive_creative");
        driver.findElement(By.id("closeIconContainer")).click();

        driver.switchTo().defaultContent();

        WebElement result = driver.findElement(By.xpath("//span[@class = 'ss__query']"));
        Assert.assertEquals(result.getText(), "TANK");
    }
}