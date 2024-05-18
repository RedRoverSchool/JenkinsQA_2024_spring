package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import school.redrover.model.AppearancePage;
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
        String result = new AppearancePage(getDriver())
                .clickDarkThemeButton()
                .clickApply()
                .colorSchema();

        Assert.assertEquals(result, "dark");
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

    @Test
    public void testDarkThemeApply() {
        final String actualThemeApplied = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearanceButton()
                .clickDarkThemeButton()
                .clickApplyButton()
                .getCurrentThemeAttribute();

        Assert.assertEquals(actualThemeApplied, "dark");
    }

    @Test
    public void testDefaultThemeApply() {
        final String actualThemeApplied = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearanceButton()
                .clickDefaultThemeButton()
                .clickApplyButton()
                .getCurrentThemeAttribute();

        Assert.assertEquals(actualThemeApplied, "none");
    }

    @Test
    public void testSystemThemeApply() {
        final String actualThemeApplied = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearanceButton()
                .clickSystemThemeButton()
                .clickApplyButton()
                .getCurrentThemeAttribute();

        Assert.assertTrue(actualThemeApplied.contains("system"));
    }

    @AfterMethod
    public void returnToNoneTheme() {
        AppearancePage appearancePage = new AppearancePage(getDriver());

        if (!appearancePage
                .getCurrentThemeAttribute()
                .equals("none")) {
            goToManageAppearance();
            appearancePage
                    .clickDefaultThemeButton()
                    .clickApplyButton();
        }
    }
}