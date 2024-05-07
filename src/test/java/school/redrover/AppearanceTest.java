package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class AppearanceTest extends BaseTest {

    void goToManageAppearance() {
        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        getDriver().findElement(By.cssSelector("[href=\"appearance\"]")).click();
    }

    @Test
    public void testAppearanceQuantityOfThemesViaDashboardDropDown() {

        int quantityOfThemes = new HomePage(getDriver())
                .openDashboardBreadcrumbsDropdown()
                .clickManageFromDashboardBreadcrumbsMenu()
                .clickAppearanceButton()
                .getThemesList()
                .size();

        Assert.assertEquals(quantityOfThemes, 3);
    }

    @Test
    public void testDarkThemeSwitchNotification() {
        goToManageAppearance();

        getDriver().findElement(By.cssSelector("[for='radio-block-0']")).click();
        getDriver().findElement(By.name("Apply")).click();

        Assert.assertEquals(
                getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("notification-bar"))).getText(),
                "Saved");
    }

    @Test
    public void testDarkThemeSwitchColor() {
        goToManageAppearance();

        getDriver().findElement(By.cssSelector("[for='radio-block-0']")).click();
        getDriver().findElement(By.name("Apply")).click();

        Assert.assertEquals(
                getDriver().findElement(By.tagName("body")).getCssValue("background-color"),
                "rgba(31, 31, 35, 1)",
                "The background color doesn't match the theme");
    }

    @AfterMethod
    public void returnToNoneTheme() {
        if (!getDriver().findElement(By.tagName("html")).getAttribute("data-theme").equals("none")) {
            goToManageAppearance();
            getDriver().findElement(By.cssSelector("[for='radio-block-2']")).click();
            getDriver().findElement(By.name("Apply")).click();
        }
    }
}