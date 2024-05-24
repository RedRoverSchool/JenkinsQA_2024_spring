package school.redrover;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import school.redrover.model.AppearancePage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class AppearanceTest extends BaseTest {

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
        String notificationText = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearanceButton()
                .clickDarkThemeButton()
                .clickApply()
                .getNotificationText();

        Assert.assertEquals(notificationText, "Saved");
    }

    @Test
    public void testDarkThemeSwitchColor() {
        String backgroundColor = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearanceButton()
                .clickDarkThemeButton()
                .clickApply()
                .getBackgroundColor();

        Assert.assertEquals(
                backgroundColor,
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
            new HomePage(getDriver())
                    .clickLogo()
                    .clickManageJenkins()
                    .clickAppearanceButton()
                    .clickDefaultThemeButton()
                    .clickApplyButton();
        }
    }
}