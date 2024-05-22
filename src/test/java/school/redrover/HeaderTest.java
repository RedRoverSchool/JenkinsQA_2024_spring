package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class HeaderTest extends BaseTest {

    @Test
    public void testTooltipAccessible() {
        String warningTooltipText = new HomePage(getDriver())
                .clickWarningIcon()
                .getWarningTooltipText();

        Assert.assertTrue(warningTooltipText.contains("Warnings"));
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

    @Test
    public void testLogout() {
        getDriver().findElement(By.xpath("//*[@href='/logout']")).click();
        String actual = getDriver().findElement(
                By.xpath("//*[text()='Sign in to Jenkins']")).getText();

        Assert.assertEquals(actual, "Sign in to Jenkins");
    }
}