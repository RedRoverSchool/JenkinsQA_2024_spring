package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.AboutJenkinsPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FooterTest extends BaseTest {

    @Test
    public void testLinkButtonsListInVersionDropDown() {
        final List<String> expectedDropDownElementsValues = List.of("About Jenkins", "Get involved", "Website");

        List<String> actualDropDownElementsValues = new HomePage(getDriver())
                .clickVersion()
                .getVersionDropDownElementsValues();

        Assert.assertEquals(actualDropDownElementsValues, expectedDropDownElementsValues, "Allarm!");
    }

    @Test
    public void testRestAPIButtonTitle() {
        String titleText = new HomePage(getDriver())
                .clickApiLink()
                .getApiPageTitleText();

        Assert.assertEquals(titleText, "Remote API [Jenkins]");
    }

    @Test
    public void testJenkinsVersion() {
        AboutJenkinsPage page = new HomePage(getDriver())
                .clickVersion()
                .selectAboutJenkins();

        Assert.assertTrue(page.isDisplayedVersionJenkins());
    }

    @Test
    public void testDropDownLink() {
        HomePage page = new HomePage(getDriver())
                .clickVersion();

        Assert.assertTrue(page.isDisplayedAboutJenkinsDropdownItem());
        Assert.assertTrue(page.isDisplayedInvolvedDropdownItem());
        Assert.assertTrue(page.isDisplayedWebsiteDropdownItem());
    }

    @Test
    public void testJenkinsInformationFooter() {
        boolean isExistJenkinsInformationFooter = new HomePage(getDriver())
                .clickVersion()
                .selectAboutJenkins()
                .isExistJenkinsInformationFooter();

        Assert.assertTrue(isExistJenkinsInformationFooter);
    }
}