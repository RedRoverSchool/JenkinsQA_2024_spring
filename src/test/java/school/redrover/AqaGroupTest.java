package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AqaGroupTest extends AqaGroupBaseTest {
    private static final String URL = "https://demoqa.com/alerts";

    @Test
    public void testAlert() {
        getDriver().get(URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(15000));

        getDriver().findElement(By.id("alertButton")).click();
        getDriver().switchTo().alert().accept();
        Assert.assertTrue(
                getDriver().findElement(By.id("alertButton")).isDisplayed(),
                "alert is not accepted");
    }

    @Test
    public void testWaitingAlert() {
        getDriver().get(URL);

        getDriver().findElement(By.id("timerAlertButton")).click();
        Alert alert = getWait15().until(ExpectedConditions.alertIsPresent());
        alert.accept();

        Assert.assertTrue(
                getDriver().findElement(By.id("timerAlertButton")).isDisplayed(),
                "alert is not accepted");
    }

    @Test
    public void testAcceptConfirmAlert() {
        getDriver().get(URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(15000));

        scrollIntoView(getDriver().findElement(By.id("confirmButton"))).click();
        getDriver().switchTo().alert().accept();

        Assert.assertEquals(
                getDriver().findElement(By.id("confirmResult")).getText(),
                "You selected Ok",
                "alert is not accepted");
    }

    @Test
    public void testDismissConfirmAlert() {
        getDriver().get(URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(15000));

        scrollIntoView(getDriver().findElement(By.id("confirmButton"))).click();
        getDriver().switchTo().alert().dismiss();

        Assert.assertEquals(
                getDriver().findElement(By.id("confirmResult")).getText(),
                "You selected Cancel",
                "alert is not dismissed");
    }

    @Test
    public void testPromptAlert() {
        getDriver().get(URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(15000));

        scrollIntoView(getDriver().findElement(By.id("promtButton"))).click();
        Alert alert = getDriver().switchTo().alert();
        alert.sendKeys("Irina");
        alert.accept();

        Assert.assertEquals(
                getDriver().findElement(By.id("promptResult")).getText(),
                "You entered Irina",
                "unexpected result");
    }

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