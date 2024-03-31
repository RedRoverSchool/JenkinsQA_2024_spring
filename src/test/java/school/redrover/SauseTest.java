package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class SauseTest {

    WebDriver driver = new ChromeDriver();
    private final String SITE = "https://www.saucedemo.com/";
    private final String LOGIN_FIELD = "//input[@id='user-name']";
    private final String PASSWORD_FIELD = "//input[@id='password']";
    private final String LOGIN_BUTTON = "//input[@id='login-button']";

    private String standartUser;
    private String lockedOutUser;
    private String problemUser;
    private String performanceGlitchUser;
    private String errorUser;
    private String visualUser;
    private String password;

    public void login(String login) {
        driver.get(SITE);
        driver.findElement(By.xpath(LOGIN_FIELD)).sendKeys(login);
        driver.findElement(By.xpath(PASSWORD_FIELD)).sendKeys(password);
        driver.findElement(By.xpath(LOGIN_BUTTON)).click();
    }

    @BeforeSuite
    public void getCredentials() {
        driver.get(SITE);

        String[] loginsArray = driver
                .findElement(By.xpath("//div[@id='login_credentials']"))
                .getAttribute("innerText")
                .split("\n");

        standartUser = loginsArray[1];
        lockedOutUser = loginsArray[2];
        problemUser = loginsArray[3];
        performanceGlitchUser = loginsArray[4];
        errorUser = loginsArray[5];
        visualUser = loginsArray[6];

        String[] passArray = driver
                .findElement(By.xpath("//div[@class='login_password']"))
                .getAttribute("textContent")
                .split(":");
        password = passArray[1];

    }





    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}

