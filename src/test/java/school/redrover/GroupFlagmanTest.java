package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupFlagmanTest extends BaseTest {

    @Test
    public void testSauseDemoLoginForm() {
        getDriver().get("https://www.saucedemo.com/");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Swag Labs");

        WebElement userName = getDriver().findElement(By.name("user-name"));
        userName.sendKeys("standard_user");

        WebElement password = getDriver().findElement(By.name("password"));
        password.sendKeys("secret_sauce");

        WebElement loginButton = getDriver().findElement(By.name("login-button"));
        loginButton.click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }

}
