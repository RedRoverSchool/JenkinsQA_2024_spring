package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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

        Assert.assertEquals(textBox.getAttribute("placeholder"), "I want to discover...");

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
    public void testLogin() {
        getDriver().get("https://www.saucedemo.com/");

        WebElement login = getDriver().findElement(By.id("user-name"));
        WebElement psw = getDriver().findElement(By.id("password"));
        WebElement signInButton = getDriver().findElement(By.id("login-button"));

        login.sendKeys("standard_user");
        psw.sendKeys("secret_sauce");
        signInButton.submit();

        WebElement displayedPage = getDriver().findElement(By.className("title"));
        String targetText = displayedPage.getText();
        Assert.assertEquals(targetText, "Products");
    }
    @Test
    public void wooordHunttest(){
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        getDriver().get("https://wooordhunt.ru/");

        getDriver().findElement(By.id("hunted_word")).sendKeys("apple");
        getDriver().findElement(By.id("hunted_word_submit")).click();

        String value = getDriver().findElement(By.className("t_inline_en")).getText();

        Assert.assertEquals("яблоко, яблоня, чепуха, лесть, яблочный", value);

    }

    @Test
    public void testSearchOnFlashscore() {
        getDriver().get("https://www.flashscore.ua/");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        getDriver().findElement(By.id("search-window")).click();
        getDriver().findElement(By.cssSelector("input")).sendKeys("aaaaaaaaa");

        Assert.assertEquals(getDriver().findElement(By.className("searchResults__noResult"))
                .getText(), "За Вашим запитом нічого не знайдено.");
    }

}
