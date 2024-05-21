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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageJenkinsTest extends BaseTest {

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

    private static final Map<String, String> manageLinks = new HashMap<>() {{
        put("System", "/configure");
        put("Tools", "/configureTools");
        put("Plugins", "/pluginManager");
        put("Nodes", "/computer");
        put("Clouds", "/cloud");
        put("Appearance", "/appearance");
        put("Security", "/configureSecurity");
        put("Credentials", "/credentials");
        put("Credential Providers", "/configureCredentials");
        put("Users", "/securityRealm");
        put("System Information", "/systemInfo");
        put("System Log", "/log");
        put("Load Statistics", "/load-statistics");
        put("About Jenkins", "/about");
        put("Manage Old Data", "/OldData");
        put("Jenkins CLI", "/cli");
        put("Script Console", "/script");
        put("Prepare for Shutdown", "/prepareShutdown");
    }};

    @DataProvider(name = "linksDataProvider")
    public Object[][] linksDataProvider() {
        Object[][] data = new Object[manageLinks.size()][2];
        int index = 0;
        for (Map.Entry<String, String> entry : manageLinks.entrySet()) {
            data[index][0] = entry.getKey();
            data[index][1] = entry.getValue();
            index++;
        }
        return data;
    }

    @Test(dataProvider = "linksDataProvider")
    public void testManageJenkinsLinks(String key, String value) {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text()='" + key + "']")).click();

        String actualUrl = getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(value));
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
}
