package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
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
    public void testAppearanceQuantityOfThemesViaDashboardDropdown() {

        int quantityOfThemes = new HomePage(getDriver())
                .openDashboardBreadcrumbsDropdownMenu()
                .clickManageJenkinsOnBreadcrumbsMenu()
                .clickAppearance()
                .getThemesList()
                .size();

        Allure.step("Expected results: 3 color themes available for selection");
        Assert.assertEquals(quantityOfThemes, 3);
    }

    @Test
    @Story("US_09.005 Appearance")
    @Description("Change color theme to Dark and wait notification 'Saved'")
    public void testDarkThemeSwitchNotification() {
        String notificationText = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearance()
                .clickDarkThemeButton()
                .clickApplyButton()
                .getNotificationText();

        Allure.step("Expected results: After applying changes notification 'Saved' is displayed");
        Assert.assertEquals(notificationText, "Saved");
    }

    @Test
    @Story("US_09.005 Appearance")
    @Description("Check background color after switch to Dark theme")
    public void testDarkThemeColor() {
        final String expectedColor = "rgba(31, 31, 35, 1)";

        String backgroundColor = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearance()
                .clickDarkThemeButton()
                .clickApplyButton()
                .getBackgroundColor();

        Allure.step("Expected results: After applying changes color of body page: '{expectedColor}'");
        Assert.assertEquals(backgroundColor, expectedColor,
                "The background color doesn't match the theme");
    }

    @Test
    @Story("US_09.005 Appearance")
    @Description("Change color theme to Dark and check It")
    public void testDarkThemeApply() {
        final String expectedThemeAttribute = "dark";

        final String actualThemeApplied = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearance()
                .clickDarkThemeButton()
                .clickApplyButton()
                .getCurrentThemeAttribute();

        Allure.step("Expected results: Attribute of current color theme - '{expectedThemeAttribute}'");
        Assert.assertEquals(actualThemeApplied, expectedThemeAttribute);
    }

    @Story("US_09.005 Appearance")
    @Description("Change color theme to Default and check It")
    @Test
    public void testDefaultThemeApply() {
        final String expectedThemeAttribute = "none";

        final String actualThemeApplied = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearance()
                .clickDefaultThemeButton()
                .clickApplyButton()
                .getCurrentThemeAttribute();

        Allure.step("Expected results: Attribute of current color theme - '{expectedThemeAttribute}'");
        Assert.assertEquals(actualThemeApplied, expectedThemeAttribute);
    }

    @Story("US_09.005 Appearance")
    @Description("Change color theme to System and check It")
    @Test
    public void testSystemThemeApply() {
        final String expectedThemeAttribute = "system";

        final String actualThemeApplied = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearance()
                .clickSystemThemeButton()
                .clickApplyButton()
                .getCurrentThemeAttribute();

        Allure.step("Expected results: Attribute of current color theme - '{expectedThemeAttribute}'");
        Assert.assertTrue(actualThemeApplied.contains(expectedThemeAttribute));
    }

    @Step("Reset color theme to default")
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
