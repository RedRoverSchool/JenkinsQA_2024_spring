package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

public class AqaGroupBasicAuthAndCookieTest extends AqaGroupBaseTest {

    @Test
    public void basicAuthProtectionTest() {
        ((ChromeDriver) driver).executeCdpCommand(
                "Network.enable", Map.of());
        ((ChromeDriver) driver).executeCdpCommand(
                "Network.setExtraHTTPHeaders",
                Map.of("headers", Map.of("Authorization", "Basic YXV0aG9yaXplZDpwYXNzd29yZDAwMQ==")));
        driver.get("https://testpages.eviltester.com/styled/auth/basic-auth-results.html");
        Assert.assertEquals(
                driver.findElement(By.id("status")).getText(),
                "Authenticated");
    }

    @Test
    public void CSSMediaQueriesSizingTest() {
        driver.get("https://testpages.eviltester.com/styled/css-media-queries.html");

        driver.manage().window().setSize(new Dimension(1200, 1080));
        Assert.assertTrue(driver.findElement(By.className("s1200")).isDisplayed());
    }

    @Test
    public void cookieControlledAdminTest() throws InterruptedException {
        driver.get("https://testpages.eviltester.com/styled/cookies/adminview.html");
//
        driver.findElement(By.id("username")).sendKeys("Admin");
        driver.findElement(By.id("password")).sendKeys("AdminPass");
        driver.findElement(By.id("login")).click();
        Thread.sleep(5000);

        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println(cookies);

        Assert.assertEquals(driver.findElement(By.id("adminh1")).getText(), "Admin View");
    }

    @Test
    public void cookieAddAdminTest() throws InterruptedException {
        driver.get("https://testpages.eviltester.com");

        driver.manage().addCookie(new Cookie("loggedin", "Admin"));
        driver.get("https://testpages.eviltester.com/styled/cookies/adminview.html");
        Thread.sleep(5000);

        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println(cookies);

        Assert.assertEquals(
                driver.findElement(By.id("adminh1")).getText(),
                "Admin View");
    }
}
