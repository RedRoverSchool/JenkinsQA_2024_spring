package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.CredentialProvidersPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

@Epic("Manage Jenkins")
public class ManageJenkinsTest extends BaseTest {

    private static final Object[][] MANAGE_LINKS_DESCRIPTION = {
            {"System", "Configure global settings and paths."},
            {"Tools", "Configure tools, their locations and automatic installers."},
            {"Plugins", "Add, remove, disable or enable plugins that can extend the functionality of Jenkins."},
            {"Nodes", "Add, remove, control and monitor the various nodes that Jenkins runs jobs on."},
            {"Clouds", "Add, remove, and configure cloud instances to provision agents on-demand."},
            {"Appearance", "Configure the look and feel of Jenkins"},
            {"Security", "Secure Jenkins; define who is allowed to access/use the system."},
            {"Credentials", "Configure credentials"},
            {"Credential Providers", "Configure the credential providers and types"},
            {"Users", "Create/delete/modify users that can log in to this Jenkins."},
            {"System Information", "Displays various environmental information to assist trouble-shooting."},
            {"System Log", "System log captures output from java.util.logging output related to Jenkins."},
            {"Load Statistics", "Check your resource utilization and see if you need more computers for your builds."},
            {"About Jenkins", "See the version and license information."},
            {"Manage Old Data", "Scrub configuration files to remove remnants from old plugins and earlier versions."},
            {"Reload Configuration from Disk", "Discard all the loaded data in memory and reload everything "
                    + "from file system. Useful when you modified config files directly on disk."},
            {"Jenkins CLI", "Access/manage Jenkins from your shell, or from your script."},
            {"Script Console", "Executes arbitrary script for administration/trouble-shooting/diagnostics."},
            {"Prepare for Shutdown", "Stops executing new builds, "
                    + "so that the system can be eventually shut down safely."}
    };

    private static final Object[][] MANAGE_LINKS_ENDPOINTS = {
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

    private static final Object[][] MANAGE_LINKS_PAGE_TITLES = {
            {"System", "System [Jenkins]"},
            {"Tools", "Tools [Jenkins]"},
            {"Plugins", "Updates - Plugins [Jenkins]"},
            {"Nodes", "Nodes [Jenkins]"},
            {"Clouds", "Clouds [Jenkins]"},
            {"Appearance", "Appearance [Jenkins]"},
            {"Security", "Security [Jenkins]"},
            {"Credentials", "Jenkins » Credentials [Jenkins]"},
            {"Credential Providers", "Credential Providers [Jenkins]"},
            {"Users", "Users [Jenkins]"},
            {"System Information", "System Information [Jenkins]"},
            {"System Log", "Log Recorders [Jenkins]"},
            {"Load Statistics", "Jenkins Load Statistics [Jenkins]"},
            {"About Jenkins", "About Jenkins 2.440.2 [Jenkins]"},
            {"Manage Old Data", "Manage Old Data [Jenkins]"},
            {"Jenkins CLI", "Jenkins"},
            {"Script Console", "Script Console [Jenkins]"},
            {"Prepare for Shutdown", "Prepare for Shutdown [Jenkins]"}
    };

    @DataProvider(name = "manageLinksDescriptionsProvider")
    public Object[][] manageLinksDescriptionsProvider() {
        return MANAGE_LINKS_DESCRIPTION;
    }


    @Test(dataProvider = "manageLinksDescriptionsProvider")
    @Story("US_09.002 View 'Manage Jenkins' page > Visibility and clickability")
    @Description("Check link description text")
    public void testLinksDescriptionText(String linkName, String description) {
        String linkDescription = new HomePage(getDriver())
                .clickManageJenkins()
                .getLinkDescription(linkName);

        Allure.step("Expected result: Link " + linkName + " description is " + description);
        Assert.assertEquals(linkDescription, description);
    }


    @DataProvider(name = "manageLinksEndpointsProvider")
    public Object[][] manageLinksEndpointsProvider() {
        return MANAGE_LINKS_ENDPOINTS;
    }

    @Test(dataProvider = "manageLinksEndpointsProvider")
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirect to right URL after click on link")
    public void testManageJenkinsLinksRedirectionURL(String linkName, String endpoint) {
        String currentUrl = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageLinkAndGetCurrentUrl(linkName);

        Allure.step("Expected result: Page URL contains " + endpoint);
        Assert.assertTrue(currentUrl.contains(endpoint));
    }

    @DataProvider(name = "manageLinksPageTitlesProvider")
    public Object[][] manageLinksPageTitlesProvider() {
        return MANAGE_LINKS_PAGE_TITLES;
    }

    @Test(dataProvider = "manageLinksPageTitlesProvider")
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check page title after click on link")
    public void testManageJenkinsLinksRedirectToPage(String linkName, String pageTitle) {
        String title = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageLinkAndGetTitle(linkName);

        Allure.step("Expected result: Page title is " + pageTitle);
        Assert.assertEquals(title, pageTitle);
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'System' page")
    public void testRedirectionToSystemPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickSystemLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'System'");
        Assert.assertEquals(pageTitle, "System");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Tools' page")
    public void testRedirectionToToolsPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickToolsLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Tools'");
        Assert.assertEquals(pageTitle, "Tools");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Plugins' page")
    public void testRedirectionToPluginsPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickPluginsLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Plugins'");
        Assert.assertEquals(pageTitle, "Plugins");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Nodes' page")
    public void testRedirectionToNodesPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodesLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Nodes'");
        Assert.assertEquals(pageTitle, "Nodes");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Clouds' page")
    public void testRedirectionToCloudsPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickCloudsLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Clouds'");
        Assert.assertEquals(pageTitle, "Clouds");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Appearance' page")
    public void testRedirectionToAppearancePage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAppearanceLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Appearance'");
        Assert.assertEquals(pageTitle, "Appearance");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Security' page")
    public void testRedirectionToSecurityPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickSecurityLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Security'");
        Assert.assertEquals(pageTitle, "Security");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Credentials' page")
    public void testRedirectionToCredentialsPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickCredentialsLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Credentials'");
        Assert.assertEquals(pageTitle, "Credentials");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Credential Providers' page")
    public void testRedirectionToCredentialProvidersPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickCredentialProvidersLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Credential Providers'");
        Assert.assertEquals(pageTitle, "Credential Providers");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Users' page")
    public void testRedirectionToUsersPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Users'");
        Assert.assertEquals(pageTitle, "Users");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'System Information' page")
    public void testRedirectionToSystemInformationPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickSystemInformationLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'System Information'");
        Assert.assertEquals(pageTitle, "System Information");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'System Log' page")
    public void testRedirectionToSystemLogPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickSystemLogLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Log Recorders'");
        Assert.assertEquals(pageTitle, "Log Recorders");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Load Statistics' page")
    public void testRedirectionToLoadStatisticsPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickLoadStatisticsLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Load statistics: Jenkins'");
        Assert.assertEquals(pageTitle, "Load statistics: Jenkins");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'About Jenkins' page")
    public void testRedirectionToAboutJenkinsPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickAboutJenkinsLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Jenkins'");
        Assert.assertEquals(pageTitle, "Jenkins");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Manage Old Data' page")
    public void testRedirectionToManageOldDataPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageOldDataLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Manage Old Data'");
        Assert.assertEquals(pageTitle, "Manage Old Data");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Jenkins CLI' page")
    public void testRedirectionToJenkinsCLIPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickJenkinsCLILink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Jenkins CLI'");
        Assert.assertEquals(pageTitle, "Jenkins CLI");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Script Console' page")
    public void testRedirectionToScriptConsolePage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickScriptConsoleLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Script Console'");
        Assert.assertEquals(pageTitle, "Script Console");
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Redirection")
    @Description("Check redirection to 'Prepare for Shutdown' page")
    public void testRedirectionToPrepareForShutdownPage() {
        String pageTitle = new HomePage(getDriver())
                .clickManageJenkins()
                .clickPrepareForShutdownLink()
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Prepare for Shutdown'");
        Assert.assertEquals(pageTitle, "Prepare for Shutdown");
    }

    @Test
    @Story("US_09.002 View 'Manage Jenkins' page > Visibility and clickability")
    @Description("Check 'Reload Configuration from Disk' confirmation dialog title")
    public void testReloadConfigurationFromDiskConfirmationDialogTitle() {
        String titleText = new HomePage(getDriver())
                .clickManageJenkins()
                .clickReloadConfigurationFromDiskLink()
                .getDialogTitleText();

        Allure.step("Expected result: Title is 'Reload Configuration from Disk'");
        Assert.assertEquals(titleText, "Reload Configuration from Disk");
    }

    @Test
    @Story("US_09.001  Search settings")
    @Description("Check search input placeholder text")
    public void testSearchInputPlaceholderText() {
        String placeholderText = new HomePage(getDriver())
                .clickManageJenkins()
                .getSearchInputPlaceholderText();

        Allure.step("Expected result: Search input placeholder text is 'Search settings'");
        Assert.assertEquals(placeholderText, "Search settings");
    }

    @Test
    @Story("US_09.001  Search settings")
    @Description("Check the text of the no search results popup message")
    public void testSearchInputNoResultsPopupText() {
        String popupText = new HomePage(getDriver())
                .clickManageJenkins()
                .typeInSearchInput("admin")
                .getNoSearchResultsPopupText();

        Allure.step("Expected result: Popup text is 'No results'");
        Assert.assertEquals(popupText, "No results");
    }

    @Test
    @Story("US_09.001  Search settings")
    @Description("Check that slash key press activates the search input")
    public void testSearchInputPressSlashKeyActivation() {
        boolean isActive = new HomePage(getDriver())
                .clickManageJenkins()
                .pressSlashKey()
                .isSearchInputActive();

        Allure.step("Expected result: Search input is focused");
        Assert.assertTrue(isActive);
    }

    @Test
    @Story("US_09.001  Search settings")
    @Description("Check search input slash icon tooltip text")
    public void testSearchInputSlashIconTooltipText() {
        String slashIconTooltipText = new HomePage(getDriver())
                .clickManageJenkins()
                .hoverMouseOverSlashIcon()
                .getSlashIconTooltipText();

        Allure.step("Expected result: Slash icon tooltip text is 'Press / on your keyboard to focus'");
        Assert.assertEquals(slashIconTooltipText, "Press / on your keyboard to focus");
    }

    @Test
    @Story("US_09.001  Search settings")
    @Description("Check search result click redirection")
    public void testSearchResultClickRedirection() {
        String pageToNavigateHeading = new HomePage(getDriver())
                .clickManageJenkins()
                .typeInSearchInput("Credential")
                .clickSecondSearchResult(new CredentialProvidersPage(getDriver()))
                .getHeadingText();

        Allure.step("Expected result: Page heading is 'Credential Providers'");
        Assert.assertEquals(pageToNavigateHeading, "Credential Providers");
    }
}
