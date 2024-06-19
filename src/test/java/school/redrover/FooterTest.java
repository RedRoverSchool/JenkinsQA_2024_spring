package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

@Epic("Footer")
public class FooterTest extends BaseTest {

    private final String jenkinsVersion = "2.440.2";

    @Test
    @Story("US_12.002 'Jenkins 2.440.2' button > Visibility, click ability and functionality")
    @Description("Check a drop-down list upon clicking on the 'Jenkins 2.440.2' button")
    public void testLinkButtonsListInVersionDropdown() {
        final List<String> expectedDropdownElementsValues = List.of("About Jenkins", "Get involved", "Website");

        List<String> actualDropdownElementsValues = new HomePage(getDriver())
                .getFooter().clickVersion()
                .getFooter().getVersionDropdownElementsValues();

        Assert.assertEquals(actualDropdownElementsValues, expectedDropdownElementsValues);
    }

    @Test
    @Story("US_12.003 REST API link > Redirection")
    @Description("Verify if the title name is correct on the Api Link page")
    public void testRestAPIButtonTitle() {
        String titleText = new HomePage(getDriver())
                .getFooter().clickApiLink()
                .getTitle();

        Assert.assertEquals(titleText, "Remote API [Jenkins]");
    }

    @Test
    @Story("US_12.002 'Jenkins 2.440.2' button > Visibility, click ability and functionality")
    @Description("Check tab bar menu items on the 'About Jenkins' page")
    public void testJenkinsInformationFooter() {
        boolean isExistJenkinsInformationFooter = new HomePage(getDriver())
                .getFooter().clickVersion()
                .getFooter().selectAboutJenkinsAndClick()
                .isExistJenkinsInformationFooter();

        Assert.assertTrue(isExistJenkinsInformationFooter);
    }

    @Test
    @Story("US_12.002 'Jenkins 2.440.2' button > Visibility, click ability and functionality")
    @Description("Check Jenkins version in the footer on the 'About Jenkins' page")
    public void testVersionOnAboutJenkinsPage() {
        String versionOnPage = new HomePage(getDriver())
                .getFooter().clickVersion()
                .getFooter().selectAboutJenkinsAndClick()
                .getJenkinsVersion();

        Assert.assertEquals(versionOnPage, jenkinsVersion);
    }

    @Test
    @Story("US_12.002 'Jenkins 2.440.2' button > Visibility, click ability and functionality")
    @Description("Check Jenkins version in the footer on the "
            + "Home, New Item, People, Build History, Manage Jenkins, My Views pages")
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
