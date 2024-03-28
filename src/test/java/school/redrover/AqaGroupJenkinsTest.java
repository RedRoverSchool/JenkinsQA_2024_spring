package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AqaGroupJenkinsTest extends AqaGroupBaseTest {

    private void login() {
        getDriver().get("http://localhost:8080/login");

        getDriver().findElement(By.id("j_username")).sendKeys("amalgammy");
        getDriver().findElement(By.id("j_password")).sendKeys("admin");
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testAuthJenkins() {
        login();

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("a[href = '/logout']")).isDisplayed());
    }

    @Test
    public void testLogoutJenkins() {
        login();

        getDriver().findElement(By.cssSelector("a[href = '/logout']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.tagName("h1")).getText(),
                "Sign in to Jenkins");
    }

    @Test
    public void testNewItemJenkins() {
        login();

        getDriver().findElement(By.cssSelector("#side-panel > div > div")).click();
        getDriver().findElement(By.id("name")).sendKeys("my_number_one");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector("a[href = '/']")).click();

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("a[href = 'job/my_number_one/']"))
                        .isDisplayed());
    }
}