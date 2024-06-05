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
    @Story("US_09.005 Appearance")
    @Description("Check number of color themes that are available")
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
    @Story("US_09.005 Appearance")
    @Description("Change color theme to Dark and wait notification 'Saved'")
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
    @Story("US_09.005 Appearance")
    @Description("Check background color after switch to Dark theme")
    public void testDarkThemeColor() {
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
    @Story("US_09.005 Appearance")
    @Description("Change color theme to Dark and check It")
    public void testDarkThemeApply() {
        final String actualThemeApplied = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearanceButton()
                .clickDarkThemeButton()
                .clickApplyButton()
                .getCurrentThemeAttribute();

        Assert.assertEquals(actualThemeApplied, "dark");
    }

    @Story("US_09.005 Appearance")
    @Description("Change color theme to Default and check It")
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

    @Story("US_09.005 Appearance")
    @Description("Change color theme to System and check It")
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
    public void returnDefaultTheme() {
        AppearancePage appearancePage = new AppearancePage(getDriver());

        if (!appearancePage.getCurrentThemeAttribute().equals("none")) {
            appearancePage
                    .clickDefaultThemeButton()
                    .clickApplyButton();
        }
    }
}