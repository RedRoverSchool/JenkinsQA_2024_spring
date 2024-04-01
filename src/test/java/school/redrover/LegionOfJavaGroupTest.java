package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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

    public void testLogin(){
        getDriver().get("https://practicetestautomation.com/practice-test-login/");
        WebElement userName = getDriver().findElement(By.name("username"));
        userName.sendKeys("student");
        WebElement password = getDriver().findElement(By.name("password"));
        password.sendKeys("Password123");
        WebElement submitButton = getDriver().findElement(By.id("submit"));
        submitButton.click();
        WebElement result = getDriver().findElement(By.xpath("//h1"));
        String resultText = result.getText();
        Assert.assertEquals("Logged In Successfully", resultText);
    }

    @Test
    public void testAnimalplanetShows() {

        getDriver().get("https://www.animalplanet.com/");
        getDriver().manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        WebElement aria_labelElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='siteLogo-Sz8OyJHj']")));
        assertTrue(aria_labelElement.isDisplayed());
        assertTrue(aria_labelElement.isEnabled());

        WebElement shows = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Shows')]")));
        shows.click();

        WebElement ClickWildLife = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Wildlife')]")));
        ClickWildLife.click();

        WebElement listOfShows = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='subtabsWrapper-TXB_Y6RY']")));
        assertTrue(listOfShows.isDisplayed());
        assertTrue(listOfShows.isEnabled());
    }
}