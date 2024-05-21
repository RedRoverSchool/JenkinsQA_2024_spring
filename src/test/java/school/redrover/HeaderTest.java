package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class HeaderTest extends BaseTest {

    @Test
    public void testTooltipAccessible() {
        String warningTooltipText = new HomePage(getDriver())
                .clickWarningIcon(new HomePage(getDriver()))
                .getWarningTooltipText(getDriver());

        Assert.assertTrue(warningTooltipText.contains("Warnings"));
    }

    @Test
    public void testWarningsSettingPage() {
        String pageTitle = new HomePage(getDriver())
                .clickWarningIcon(new HomePage(getDriver()))
                .clickConfigureTooltipButton(getDriver())
                .getTitleText();

        Assert.assertTrue(pageTitle.contains("Security"));
    }

    @Test
    public void testAccessToManageJenkinsPage() {
        String pageTitle = new HomePage(getDriver())
                .clickWarningIcon(new HomePage(getDriver()))
                .clickManageJenkinsTooltipLink(getDriver())
                .getPageHeadingText();

        Assert.assertTrue(pageTitle.contains("Manage Jenkins"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/manage/"));
    }
}