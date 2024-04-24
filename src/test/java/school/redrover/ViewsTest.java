package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class ViewsTest extends BaseTest {
    @Test
    public void testGoToMyViewsFromHeaderUserMenu() {
        WebElement userElement  = getDriver().findElement(By.cssSelector("div.login a[href ^= '/user/']"));
        TestUtils.openElementDropdown(this, userElement);
        getDriver().findElement(By.cssSelector("div.tippy-box [href $= 'my-views']")).click();
        Assert.assertTrue(getWait10().until(ExpectedConditions.urlContains("my-views/view/all")));
    }

    @Test
    public void testGoToMyViewFromUsernameDropdown() {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.cssSelector("[data-href$='admin']")))
                .pause(1000)
                .click()
                .perform();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href$='admin/my-views']"))).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("[href$='my-views/']")).isDisplayed());
    }
}
