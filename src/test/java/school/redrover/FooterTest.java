package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.AboutJenkinsPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class FooterTest extends BaseTest {

    private final String jenkinsVersion = "2.440.2";

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
                .selectAboutJenkinsAndClick();

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
                .selectAboutJenkinsAndClick()
                .isExistJenkinsInformationFooter();

        Assert.assertTrue(isExistJenkinsInformationFooter);
    }

    @Test
    public void testVersionOnAboutJenkinsPage() {
        String versionOnPage = new HomePage(getDriver())
                .clickVersion()
                .selectAboutJenkinsAndClick()
                .getJenkinsVersion();

        Assert.assertEquals(versionOnPage, jenkinsVersion);
    }

    @Test
    public void testVersionOnFooter() {
        Assert.assertEquals(new HomePage(getDriver()).getVersionOnFooter(), jenkinsVersion);
    }

    @Test
    public void testVersionFooterOnEachPage() {
        List<String> versionList = new ArrayList<>();

        versionList.add(new HomePage(getDriver())
                .getVersionOnFooter());

        versionList.add(new HomePage(getDriver())
                .clickNewItem()
                .getVersionOnFooter());

        versionList.add(new HomePage(getDriver())
                .clickLogo()
                .clickPeopleButton()
                .getVersionOnFooter());

        versionList.add(new HomePage(getDriver())
                .clickLogo()
                .clickBuildHistory()
                .getVersionOnFooter());

        versionList.add(new HomePage(getDriver())
                .clickLogo()
                .clickManageJenkins()
                .getVersionOnFooter());

        versionList.add(new HomePage(getDriver())
                .clickLogo()
                .clickMyViewsOnSidebar()
                .getVersionOnFooter());

        Assert.assertTrue(versionList.stream().allMatch(s -> s.equals(jenkinsVersion)));
    }
}