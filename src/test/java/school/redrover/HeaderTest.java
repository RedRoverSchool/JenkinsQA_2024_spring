package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

@Epic("Header")
public class HeaderTest extends BaseTest {

    @Test
    @Story("US_14.004  Warning item")
    @Description("Check tooltip with the list of problems")
    public void testTooltipAccessible() {
        String warningTooltipText = new HomePage(getDriver())
                .getHeader()
                .clickWarningIcon()
                .getHeader()
                .getWarningTooltipText();

        Assert.assertTrue(warningTooltipText.contains("Warnings"));
    }

    @Test
    @Story("US_14.004  Warning item")
    @Description("Check getting the settings page with warnings")
    public void testWarningsSettingPage() {
        String pageTitle = new HomePage(getDriver())
                .getHeader()
                .clickWarningIcon()
                .getHeader()
                .clickConfigureTooltipButton()
                .getTitle();

        Assert.assertTrue(pageTitle.contains("Security"));
    }

    @Test
    @Story("US_14.004  Warning item")
    @Description("Check opening a settings page by clicking on link “Manage Jenkins")
    public void testAccessToManageJenkinsPage() {
        String pageTitle = new HomePage(getDriver())
                .getHeader()
                .clickWarningIcon()
                .getHeader()
                .clickManageJenkinsTooltipLink()
                .getPageHeadingText();

        Assert.assertTrue(pageTitle.contains("Manage Jenkins"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/manage/"));
    }

    @Test
    @Story("US_14.005  Entrance to the user's account")
    @Description("Check user logout")
    public void testLogout() {
        String actualPageTitle = new HomePage(getDriver())
                .getHeader()
                .clickLogOut()
                .getSignInToJenkinsTitle();

        Assert.assertEquals(actualPageTitle, "Sign in to Jenkins");
    }
}
