package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import school.redrover.model.AppearancePage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

@Epic("Manage Jenkins")
public class AppearanceTest extends BaseTest {

    @Test
    @Story("Appearance")
    @Description("Check number of color themes that are available")
    public void testAppearanceQuantityOfThemesViaDashboardDropDown() {

        int quantityOfThemes = new HomePage(getDriver())
                .openDashboardBreadcrumbsDropdown()
                .clickManageFromDashboardBreadcrumbsMenu()
                .clickAppearanceButton()
                .getThemesList()
                .size();

//        returnDefaultTheme();

        Assert.assertEquals(quantityOfThemes, 3);
    }

    @Test
    @Story("Appearance")
    @Description("Change color theme to Dark and check that this saved")
    public void testDarkThemeSwitchNotification() {
        String notificationText = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearanceButton()
                .clickDarkThemeButton()
                .clickApply()
                .getNotificationText();

//        returnDefaultTheme();

        Assert.assertEquals(notificationText, "Saved");
    }

    @Test
    @Story("Appearance")
    @Description("Change color theme to Dark and check that this saved")
    public void testDarkThemeSwitchColor() {
        String backgroundColor = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearanceButton()
                .clickDarkThemeButton()
                .clickApply()
                .getBackgroundColor();

//        returnDefaultTheme();

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

//        returnDefaultTheme();

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

//        returnDefaultTheme();

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

//        returnDefaultTheme();

        Assert.assertTrue(actualThemeApplied.contains("system"));
    }

    @AfterMethod
    public void returnDefaultTheme() {
        AppearancePage appearancePage = new AppearancePage(getDriver());

        if (!appearancePage.getCurrentThemeAttribute().equals("none")) {
            appearancePage.clickDefaultThemeButton()
                    .clickApplyButton();
        }
    }
}