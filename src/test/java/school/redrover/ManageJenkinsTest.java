package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.CredentialProvidersPage;
import school.redrover.model.HomePage;
import school.redrover.model.ManageJenkinsPage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;


public class ManageJenkinsTest extends BaseTest {

    private static final Object[][] manageLinks = {
            {"System", "/configure"},
            {"Tools", "/configureTools"},
            {"Plugins", "/pluginManager"},
            {"Nodes", "/computer"},
            {"Clouds", "/cloud"},
            {"Appearance", "/appearance"},
            {"Security", "/configureSecurity"},
            {"Credentials", "/credentials"},
            {"Credential Providers", "/configureCredentials"},
            {"Users", "/securityRealm"},
            {"System Information", "/systemInfo"},
            {"System Log", "/log"},
            {"Load Statistics", "/load-statistics"},
            {"About Jenkins", "/about"},
            {"Manage Old Data", "/OldData"},
            {"Jenkins CLI", "/cli"},
            {"Script Console", "/script"},
            {"Prepare for Shutdown", "/prepareShutdown"}
    };

    @Test
    public void testRedirectionToSecurityPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickSecurity()
                .getTitleText();

        Assert.assertEquals(pageTitle, "Security");
    }

    @Test
    public void testSectionNamesOfSecurityBlock() {
        final List<String> sectionsNamesExpected = List.of("Security", "Credentials", "Credential Providers",
                "Users");

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();

        List<WebElement> securityBlockElements = getDriver().findElements(By
                .xpath("//section[contains(@class, 'jenkins-section')][2]//div//dt"));

        for (int i = 0; i < securityBlockElements.size(); i++) {
            Assert.assertTrue(securityBlockElements.get(i).getText().matches(sectionsNamesExpected.get(i)));
        }
    }

    @Test
    public void testSectionDescriptionOfSecurityBlock() {
        final List<String> expectedDescription = List
                .of("Secure Jenkins; define who is allowed to access/use the system.", "Configure credentials",
                        "Configure the credential providers and types",
                        "Create/delete/modify users that can log in to this Jenkins.");

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();

        List<WebElement> actualDescription = getDriver().findElements(By
                .xpath("//section[contains(@class, 'jenkins-section')][2]//div//dd[. !='']"));

        for (int i = 0; i < actualDescription.size(); i++) {
            Assert.assertTrue(actualDescription.get(i).getText().matches(expectedDescription.get(i)));
        }
    }

    @Test
    public void testSecurityBlockSectionsClickable() {
        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();

        List<WebElement> securityBlockSections = getDriver().findElements(By
                .xpath("(//div[contains(@class, 'section__items')])[2]/div"));

        for (WebElement element : securityBlockSections) {
            new Actions(getDriver()).moveToElement(element).perform();
            Assert.assertTrue(element.isEnabled());
        }
    }

    @Test
    public void testToolsAndActionsBlockSectionsClickable() {
        boolean areToolsAndActionsSectionsEnabled = new HomePage(getDriver())
                .clickManageJenkins()
                .areToolsAndActionsSectionsEnabled();

        Assert.assertTrue(areToolsAndActionsSectionsEnabled,"'Tools and Actions' sections are not clickable");
    }

    @Test
    public void testAlertMessageClickingReloadConfigurationFromDisk() {
        boolean isAlertTitleVisible = new HomePage(getDriver())
                .clickManageJenkins()
                .clickReloadConfigurationFromDisk()
                .dialogTitleVisibility();

        Assert.assertTrue(isAlertTitleVisible);
    }

    @Test
    public void testRedirectionByClickingSecurityBlockSections() {
        boolean isSecurityBlockSectionsStale;
        List<String> pageTitle = List.of("Security", "Credentials", "Credential Providers", "Users");
        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();

        List<WebElement> securityBlockSections = getDriver().findElements(By
                .xpath("(//div[contains(@class, 'section__items')])[2]/div"));

        for (int i = 0; i < securityBlockSections.size(); i++) {
            isSecurityBlockSectionsStale = ExpectedConditions.stalenessOf(securityBlockSections.get(i))
                    .apply(getDriver());
            if (isSecurityBlockSectionsStale) {
                securityBlockSections = getDriver().findElements(By
                        .xpath("(//div[contains(@class, 'section__items')])[2]/div"));
            }
            securityBlockSections.get(i).click();
            Assert.assertTrue(getDriver().getTitle().contains(pageTitle.get(i)));
            getDriver().findElement(By.xpath("//a[@href='/manage/']")).click();
        }
    }

    @Test
    public void testPlaceholderSettingsSearchInput() {
        String SearchInputPlaceholderText = new HomePage(getDriver())
                .clickManageJenkins()
                .getSearchInputPlaceholderText();

        Assert.assertEquals(SearchInputPlaceholderText, "Search settings");
    }

    @Test
    public void testSearchSettingsWithInvalidData() {
        String noSearchResultsPopUp = new HomePage(getDriver())
                .clickManageJenkins()
                .typeSearchSettingsRequest("admin")
                .getNoSearchResultsPopUpText();

        Assert.assertEquals(noSearchResultsPopUp, "No results");
    }

    @Test
    public void testSearchSettingsFieldVisibility() {
        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins();

        Assert.assertTrue(manageJenkinsPage.isSearchInputDisplayed());
    }

    @Test
    public void testActivatingSearchPressingSlash() {
        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins()
                .pressSlashKey();

        Assert.assertTrue(manageJenkinsPage.isSearchFieldActivateElement());
    }

    @Test
    public void testTooltipAppears() {
        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins()
                .hoverMouseOverTheTooltip();

        Assert.assertTrue(manageJenkinsPage.isSearchHintDisplayed()
                        && manageJenkinsPage.getSearchHintText().equals("Press / on your keyboard to focus"), "tooltip text is incorrect");
    }

    @Test
    public void testRedirectionToNotFirstSearchResult() {
        String pageToNavigateHeading = new HomePage(getDriver())
                .clickManageJenkins()
                .typeSearchSettingsRequest("Credential")
                .clickSecondSearchResult(new CredentialProvidersPage(getDriver()))
                .getPageHeading();

        Assert.assertEquals(pageToNavigateHeading, "Credential Providers");
    }

    @DataProvider(name = "linksDataProvider")
    public Object[][] linksDataProvider() {
        return manageLinks;
    }

    @Test(dataProvider = "linksDataProvider")
    public void testManageJenkinsLinks(String link, String endpoint) {
        String actualUrl = new HomePage(getDriver()).clickManageJenkins()
                .clickManageLink(link);

        Assert.assertTrue(actualUrl.contains(endpoint));
    }

    @Ignore
    @Test
    public void testListOfManageJenkinsLinks() {
        List<String> expectedLinksName = List.of
                ("System", "Tools", "Plugins", "Nodes", "Clouds", "Appearance", "Security", "Credentials",
                        "Credential Providers", "Users", "System Information", "System Log", "Load Statistics",
                        "About Jenkins", "Manage Old Data", "Reload Configuration from Disk", "Jenkins CLI",
                        "Script Console", "Prepare for Shutdown"
                );

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        List<WebElement> linksList = getDriver().findElements(By.xpath("//div[@class='jenkins-section__item']//dt"));

        List<String> actualListName = new ArrayList<>();
        for (WebElement link : linksList) {
            actualListName.add(link.getText());
        }

        Assert.assertEquals(actualListName, expectedLinksName);
    }

    @Test
    public void testSectionsLinksAreClickable() {
        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins();

        Assert.assertTrue(manageJenkinsPage.areSectionsLinksClickable(), "Not all links are clickable");
        Assert.assertEquals(manageJenkinsPage.getNumberOfSectionLinks(), 19);
    }

    @Test
    public void testSystemInformationBlockTitlesAndDescriptions() {
        final Map<String, String> expectedSystemInformationBlockTitlesAndDescriptions = Map.ofEntries(
                Map.entry("System Information", "Displays various environmental information to assist trouble-shooting."),
                Map.entry("System Log", "System log captures output from java.util.logging output related to Jenkins."),
                Map.entry("Load Statistics","Check your resource utilization and see if you need more computers for your builds."),
                Map.entry("About Jenkins", "See the version and license information.")
        );

        Map<String, String> actualSystemInformationBlockTitlesAndDescriptions = new HomePage(getDriver())
                .clickManageJenkins()
                .getSystemInformationBlockTitlesAndDescriptions();

        Assert.assertEquals(actualSystemInformationBlockTitlesAndDescriptions, expectedSystemInformationBlockTitlesAndDescriptions);
    }

    @Test
    public void testToolsAndActionsBlockSectionsOrderTitlesAndDescriptions() {
        final Map<String, String> expectedTitlesAndDescriptions = new LinkedHashMap<>() {{
        put("Reload Configuration from Disk",
                "Discard all the loaded data in memory and reload everything from file system. " +
                "Useful when you modified config files directly on disk.");
        put("Jenkins CLI", "Access/manage Jenkins from your shell, or from your script.");
        put("Script Console", "Executes arbitrary script for administration/trouble-shooting/diagnostics.");
        put("Prepare for Shutdown", "Stops executing new builds, so that the system can be eventually shut down safely.");
        }};

        boolean areToolsAndActionsBlockTitlesDescriptionsAndOrderMatching = new HomePage(getDriver())
                .clickManageJenkins()
                .areToolsAndActionsSectionsAndDescriptionsMatchingInCorrectOrder(expectedTitlesAndDescriptions);

        Assert.assertTrue(areToolsAndActionsBlockTitlesDescriptionsAndOrderMatching,
                "Title and description pairs or their order are different");
    }
}
