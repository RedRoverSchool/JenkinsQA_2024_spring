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
                .getFooter().clickVersion(new HomePage(getDriver()))
                .getFooter().getVersionDropDownElementsValues();

        Assert.assertEquals(actualDropDownElementsValues, expectedDropDownElementsValues, "Allarm!");
    }

    @Test
    public void testRestAPIButtonTitle() {
        String titleText = new HomePage(getDriver())
                .getFooter().clickApiLink()
                .getApiPageTitleText();

        Assert.assertEquals(titleText, "Remote API [Jenkins]");
    }

    @Test
    public void testJenkinsVersion() {
        AboutJenkinsPage page = new HomePage(getDriver())
                .clickVersion()
                .getFooter().selectAboutJenkinsAndClick();

        Assert.assertTrue(page.isDisplayedVersionJenkins());
    }

    @Test
    public void testDropDownLink() {
        HomePage page = new HomePage(getDriver())
                .clickVersion();

        Assert.assertTrue(page.getFooter().isDisplayedAboutJenkinsDropdownItem());
        Assert.assertTrue(page.getFooter().isDisplayedInvolvedDropdownItem());
        Assert.assertTrue(page.getFooter().isDisplayedWebsiteDropdownItem());
    }

    @Test
    public void testJenkinsInformationFooter() {
        boolean isExistJenkinsInformationFooter = new HomePage(getDriver())
                .clickVersion()
                .getFooter().selectAboutJenkinsAndClick()
                .isExistJenkinsInformationFooter();

        Assert.assertTrue(isExistJenkinsInformationFooter);
    }

    @Test
    public void testVersionOnAboutJenkinsPage() {
        String versionOnPage = new HomePage(getDriver())
                .clickVersion()
                .getFooter().selectAboutJenkinsAndClick()
                .getJenkinsVersion();

        Assert.assertEquals(versionOnPage, jenkinsVersion);
    }

    @Test
    public void testVersionOnFooter() {
        Assert.assertEquals(new HomePage(getDriver()).getFooter().getVersionOnFooter(),
                jenkinsVersion);
    }

    @Test
    public void testVersionFooterOnEachPage() {
        List<String> versionList = new ArrayList<>();

        versionList.add(new HomePage(getDriver())
                .getFooter().getVersionOnFooter());

        versionList.add(new HomePage(getDriver())
                .clickNewItem()
                .getFooter().getVersionOnFooter());

        versionList.add(new HomePage(getDriver())
                .clickLogo()
                .clickPeopleOnSidebar()
                .getFooter().getVersionOnFooter());

        versionList.add(new HomePage(getDriver())
                .clickLogo()
                .clickBuildHistory()
                .getFooter().getVersionOnFooter());

        versionList.add(new HomePage(getDriver())
                .clickLogo()
                .clickManageJenkins()
                .getFooter().getVersionOnFooter());

        versionList.add(new HomePage(getDriver())
                .clickLogo()
                .clickMyViewsOnSidebar()
                .getFooter().getVersionOnFooter());

        Assert.assertTrue(versionList.stream().allMatch(s -> s.equals(jenkinsVersion)));
    }
}