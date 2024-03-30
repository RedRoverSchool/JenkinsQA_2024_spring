package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class ByteBustersGroupTest extends BaseTest {

        @Test
        public void CoreTest() {

            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
            getDriver().get("https://explorer.globe.engineer/");

            wait.until((ExpectedCondition<Boolean>) webDriver ->
                    ((org.openqa.selenium.JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

            WebElement textBox = getDriver().findElement(By.name("q"));
            String placeholder = getDriver().findElement(By.name("q")).getAttribute("placeholder");

            Assert.assertEquals(placeholder, "I want to discover...");
            textBox.sendKeys("IT");
            textBox.sendKeys(Keys.ENTER);

            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"IT\"]")));

        }

    @Test
    public void testSite() {

        getDriver().get("https://www.saucedemo.com/");

        String title = getDriver().getTitle();
        Assert.assertEquals("Swag Labs", title);

        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement userName = getDriver().findElement(By.id("user-name"));
        userName.sendKeys("visual_user");

        WebElement passw = getDriver().findElement(By.id("password"));
        passw.sendKeys("secret_sauce");

        WebElement submitButton = getDriver().findElement(By.name("login-button"));
        submitButton.click();

        WebElement page = getDriver().findElement(By.className("title"));
        String value = page.getText();

        Assert.assertEquals("Products", value);
    }
    @Test
    public void wooordHunttest() {

        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));


        getDriver().get("https://wooordhunt.ru/");

        WebElement inputBox = getDriver().findElement(By.id("hunted_word"));
        inputBox.sendKeys("apple");

        WebElement submitButton = getDriver().findElement(By.id("hunted_word_submit"));
        submitButton.click();

        WebElement translation = getDriver().findElement(By.className("t_inline_en"));
        String value = translation.getText();

        Assert.assertEquals("яблоко, яблоня, чепуха, лесть, яблочный", value);


    }
}
