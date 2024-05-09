package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.ManageJenkinsPage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    private static final By SETTINGS_SEARCH_BAR_LOCATOR = By.id("settings-search-bar");


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
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();

        String placeholderText = getDriver().findElement(By.id("settings-search-bar")).getDomProperty("placeholder");
        Assert.assertEquals(placeholderText, "Search settings");
    }

    @Test
    public void testSearchSettingsInvalidData() {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();

        getDriver().findElement(SETTINGS_SEARCH_BAR_LOCATOR).click();
        getDriver().findElement(SETTINGS_SEARCH_BAR_LOCATOR).sendKeys("admin");

        String searchResult = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[class='jenkins-search__results'] p"))).getText();

        Assert.assertEquals(searchResult, "No results");
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

        Assert.assertTrue(manageJenkinsPage.isShortcutDisplayed());
    }

    @Test
    public void testTooltipAppears() {
        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .clickManageJenkins()
                .hoverMouseOverTheTooltip();

        Assert.assertTrue(manageJenkinsPage.isSearchHintDisplayed()
                        && manageJenkinsPage.getSearchHintText().equals("Press / on your keyboard to focus"), "tooltip text is incorrect");
    }
}
