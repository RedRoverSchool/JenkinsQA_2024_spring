package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class WarningIconTest extends BaseTest {

    @Test
    public void testTooltipAccessible() {
        String warningTooltiptext = new HomePage(getDriver())
                .clickWarningIcon()
                .getWarningTooltipText();

        Assert.assertTrue(warningTooltiptext.contains("Warnings"));
    }

    @Test
    public void testWarningsSettingPage() {
        String pageTitle = new HomePage(getDriver())
                .clickWarningIcon()
                .clickConfigureTooltipButton()
                .getTitleText();

        Assert.assertTrue(pageTitle.contains("Security"));
    }

    @Test
    public void testAccessToManageJenkinsPage() {
        String pageTitle = new HomePage(getDriver())
                .clickWarningIcon()
                .clickManageJenkinsTooltipLink()
                .getPageHeadingText();

        Assert.assertTrue(pageTitle.contains("Manage Jenkins"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/manage/"));
    }
}
