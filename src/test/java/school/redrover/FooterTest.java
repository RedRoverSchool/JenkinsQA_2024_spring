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
                .clickVersion(new HomePage(getDriver()))
                .getVersionDropDownElementsValues(getDriver());

        Assert.assertEquals(actualDropDownElementsValues, expectedDropDownElementsValues, "Allarm!");
    }

    @Test
    public void testRestAPIButtonTitle() {
        String titleText = new HomePage(getDriver())
                .clickApiLink(getDriver())
                .getApiPageTitleText();

        Assert.assertEquals(titleText, "Remote API [Jenkins]");
    }

    @Test
    public void testJenkinsVersion() {
        AboutJenkinsPage page = new HomePage(getDriver())
                .clickVersion(new HomePage(getDriver()))
                .selectAboutJenkinsAndClick(getDriver());

        Assert.assertTrue(page.isDisplayedVersionJenkins());
    }

    @Test
    public void testDropDownLink() {
        HomePage page = new HomePage(getDriver())
                .clickVersion(new HomePage(getDriver()));

        Assert.assertTrue(page.isDisplayedAboutJenkinsDropdownItem(getDriver()));
        Assert.assertTrue(page.isDisplayedInvolvedDropdownItem(getDriver()));
        Assert.assertTrue(page.isDisplayedWebsiteDropdownItem(getDriver()));
    }

    @Test
    public void testJenkinsInformationFooter() {
        boolean isExistJenkinsInformationFooter = new HomePage(getDriver())
                .clickVersion(new HomePage(getDriver()))
                .selectAboutJenkinsAndClick(getDriver())
                .isExistJenkinsInformationFooter();

        Assert.assertTrue(isExistJenkinsInformationFooter);
    }

    @Test
    public void testVersionOnAboutJenkinsPage() {
        String versionOnPage = new HomePage(getDriver())
                .clickVersion(new HomePage(getDriver()))
                .selectAboutJenkinsAndClick(getDriver())
                .getJenkinsVersion();

        Assert.assertEquals(versionOnPage, jenkinsVersion);
    }

    @Test
    public void testVersionOnFooter() {
        Assert.assertEquals(new HomePage(getDriver()).getVersionOnFooter(getDriver()), jenkinsVersion);
    }

    @Test
    public void testVersionFooterOnEachPage() {
        List<String> versionList = new ArrayList<>();

        versionList.add(new HomePage(getDriver())
                .getVersionOnFooter(getDriver()));

        versionList.add(new HomePage(getDriver())
                .clickNewItem()
                .getVersionOnFooter(getDriver()));

        versionList.add(new HomePage(getDriver())
                .clickLogo()
                .clickPeopleOnSidebar()
                .getVersionOnFooter(getDriver()));

        versionList.add(new HomePage(getDriver())
                .clickLogo()
                .clickBuildHistory()
                .getVersionOnFooter(getDriver()));

        versionList.add(new HomePage(getDriver())
                .clickLogo()
                .clickManageJenkins()
                .getVersionOnFooter(getDriver()));

        versionList.add(new HomePage(getDriver())
                .clickLogo()
                .clickMyViewsOnSidebar()
                .getVersionOnFooter(getDriver()));

        Assert.assertTrue(versionList.stream().allMatch(s -> s.equals(jenkinsVersion)));
    }
}