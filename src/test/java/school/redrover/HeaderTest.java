package school.redrover;

import io.qameta.allure.Allure;
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
                .getHeadingText();

        Assert.assertTrue(pageTitle.contains("Manage Jenkins"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/manage/"));
    }

    @Test
    @Story("US_14.007 Username on header")
    @Description("Check user page redirection")
    public void testCheckCurrentUserID() {
        String userID = new HomePage(getDriver())
                .getHeader()
                .clickUserNameOnHeader()
                .getUserID();

        Allure.step("Expected result:  Jenkins user ID is present on page");
        Assert.assertEquals(userID, "Jenkins User ID: admin");
    }

    @Test
    @Story("US_14.007 Username on header")
    @Description("Check 'My Views' page redirection from username dropdown")
    public void testGoToMyViewsFromUsernameDropdown() {
        String views = "My Views";

        boolean textVisibility = new HomePage(getDriver())
                .getHeader()
                .clickMyViewsOnHeaderDropdown()
                .isThereTextInBreadcrumbs(views);

        Assert.assertTrue(textVisibility, "Page 'My Views' didn't open");
    }

    @Test
    @Story("US_14.005  Entrance to the user's account")
    @Description("Check user logout from header")
    public void testLogout() {
        String actualPageTitle = new HomePage(getDriver())
                .getHeader()
                .clickLogOut()
                .getHeadingText();

        Assert.assertEquals(actualPageTitle, "Sign in to Jenkins");
    }
}
