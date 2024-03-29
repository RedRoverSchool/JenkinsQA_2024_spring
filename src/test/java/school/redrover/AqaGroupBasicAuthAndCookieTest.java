package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

public class AqaGroupBasicAuthAndCookieTest extends AqaGroupBaseTest {

    @Test
    public void testBasicAuthProtection() {
        ((ChromeDriver) getDriver()).executeCdpCommand(
                "Network.enable", Map.of());
        ((ChromeDriver) getDriver()).executeCdpCommand(
                "Network.setExtraHTTPHeaders",
                Map.of("headers", Map.of("Authorization", "Basic YXV0aG9yaXplZDpwYXNzd29yZDAwMQ==")));

        getDriver().get("https://testpages.eviltester.com/styled/auth/basic-auth-results.html");

        Assert.assertEquals(
                getDriver().findElement(By.id("status")).getText(),
                "Authenticated");
    }

    @Ignore
    @Test
    public void testCSSMediaQueriesSizing() {
        getDriver().get("https://testpages.eviltester.com/styled/css-media-queries.html");

        getDriver().manage().window().setSize(new Dimension(1200, 1080));

        Assert.assertTrue(getDriver().findElement(By.className("s1200")).isDisplayed());
    }

    @Ignore
    @Test
    public void testCookieControlledAdmin() throws InterruptedException {
        getDriver().get("https://testpages.eviltester.com/styled/cookies/adminview.html");

        getDriver().findElement(By.id("username")).sendKeys("Admin");
        getDriver().findElement(By.id("password")).sendKeys("AdminPass");
        getDriver().findElement(By.id("login")).click();
        Thread.sleep(5000);

        Set<Cookie> cookies = getDriver().manage().getCookies();

        Assert.assertEquals(getDriver().findElement(By.id("adminh1")).getText(), "Admin View");
    }

    @Ignore
    @Test
    public void testCookieAddAdmin() throws InterruptedException {
        getDriver().get("https://testpages.eviltester.com");

        getDriver().manage().addCookie(new Cookie("loggedin", "Admin"));
        getDriver().get("https://testpages.eviltester.com/styled/cookies/adminview.html");
        Thread.sleep(5000);

        Set<Cookie> cookies = getDriver().manage().getCookies();
        System.out.println(cookies);

        Assert.assertEquals(
                getDriver().findElement(By.id("adminh1")).getText(),
                "Admin View");
    }
}
