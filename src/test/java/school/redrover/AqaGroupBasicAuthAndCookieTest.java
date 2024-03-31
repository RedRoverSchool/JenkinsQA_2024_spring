package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AqaGroupBasicAuthAndCookieTest extends AqaGroupBaseTest {

    @Test
    public void testCSSMediaQueriesSizing() {
        getDriver().get("https://testpages.eviltester.com/styled/css-media-queries.html");

        getDriver().manage().window().setSize(new Dimension(1200, 1080));

        Assert.assertTrue(getDriver().findElement(By.className("s1200")).isDisplayed());
    }

    @Test
    public void testCookieControlledAdmin() {
        getDriver().get("https://testpages.eviltester.com/styled/cookies/adminview.html");

        getDriver().findElement(By.id("username")).sendKeys("Admin");
        getDriver().findElement(By.id("password")).sendKeys("AdminPass");
        getDriver().findElement(By.id("login")).click();

        Assert.assertEquals(getDriver().manage().getCookieNamed("loggedin").getValue(),
                "Admin");

        Assert.assertEquals(getDriver().findElement(By.id("adminh1")).getText(),
                "Admin View");
    }

    @Test
    public void testAdminLoginWithCookie() {
        getDriver().get("https://testpages.eviltester.com");

        getDriver().manage().addCookie(new Cookie("loggedin", "Admin"));

        getDriver().get("https://testpages.eviltester.com/styled/cookies/adminview.html");

        Assert.assertEquals(
                getDriver().findElement(By.id("adminh1")).getText(),
                "Admin View");
    }

    @Test
    public void testAdminLoginWithCookieAsSuperAdmin() {
        getDriver().get("https://testpages.eviltester.com");

        getDriver().manage().addCookie(new Cookie("loggedin", "Admin"));

        getDriver().get("https://testpages.eviltester.com/styled/cookies/superadminview.html");

        Assert.assertEquals(
                getDriver().findElement(By.id("adminh1")).getText(),
                "Admin View");
    }

    @Test
    public void testSuperAdminLoginWithCookieAsAdmin() {
        getDriver().get("https://testpages.eviltester.com");

        getDriver().manage().addCookie(new Cookie("loggedin", "SuperAdmin"));

        getDriver().get("https://testpages.eviltester.com/styled/cookies/adminview.html");

        Assert.assertEquals(
                getDriver().findElement(By.id("adminh1")).getText(),
                "Admin View");
    }

    @Test
    public void testSuperAdminLoginWithCookie() {
        getDriver().get("https://testpages.eviltester.com");

        getDriver().manage().addCookie(new Cookie("loggedin", "SuperAdmin"));

        getDriver().get("https://testpages.eviltester.com/styled/cookies/superadminview.html");

        Assert.assertEquals(
                getDriver().findElement(By.id("superadminh1")).getText(),
                "Super Admin View");
    }
}
