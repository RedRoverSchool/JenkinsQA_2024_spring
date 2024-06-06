package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ManageJenkinsPage extends BasePage<ManageJenkinsPage> {

    @FindBy(css = "[href='configureSecurity']")
    private WebElement securityLink;

    @FindBy(id = "settings-search-bar")
    private WebElement searchInput;

    @FindBy(css = "[href='appearance']")
    private WebElement appearanceButton;

    @FindBy(css = "[href='computer']")
    private WebElement nodesButton;

    @FindBy(xpath = "(//div[@class='jenkins-section__items'])[5]/div[contains(@class, 'item')]")
    private List<WebElement> toolsAndActionsSections;

    @FindBy(css = "[href='securityRealm/']")
    private WebElement usersLink;

    @FindBy(className = "jenkins-search__shortcut")
    private WebElement shortcut;

    @FindBy(xpath = "//div[@aria-describedby='tippy-6']")
    private WebElement searchHint;

    @FindBy(tagName = "h1")
    private WebElement pageHeading;

    @FindBy(css = "[href='#']")
    private WebElement reloadConfigurationFromDiskLink;

    @FindBy(css = "[class*='search__results__no-results']")
    private WebElement noSearchResultsPopUp;

    @FindBy(css = ".jenkins-search__results a:nth-child(2)")
    private WebElement secondSearchResult;

    @FindBy(css = ".jenkins-section__item")
    private List<WebElement> sectionsLinksList;

    @FindBy(xpath = "(//div[@class='jenkins-section__items'])[3]//dt")
    private List<WebElement> systemInformationBlockTitles;

    @FindBy(xpath = "(//div[@class='jenkins-section__items'])[3]//dd [position() mod 2 = 1]")
    private List<WebElement> systemInformationBlockDescriptions;

    @FindBy(xpath = "(//div[@class='jenkins-section__items'])[5]//dt")
    private List<WebElement> toolsAndActionsBlockTitles;

    @FindBy(xpath = "(//div[@class='jenkins-section__items'])[5]//dd [position() mod 2 = 1]")
    private List<WebElement> toolsAndActionsBlockDescriptions;

    @FindBy(xpath = "//section[contains(@class, 'jenkins-section')][2]//div//dt")
    private List<WebElement> securitySectionNameList;

    @FindBy(xpath = "//section[contains(@class, 'jenkins-section')][2]//div//dd[. !='']")
    private List<WebElement> securitySectionDescriptionList;

    @FindBy(xpath = "//div[@class='jenkins-section__item']//dt")
    private List<WebElement> manageJenkinsLinkList;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public SecurityPage clickSecurity() {
        securityLink.click();

        return new SecurityPage(getDriver());
    }

    public boolean isSearchInputDisplayed() {
        return searchInput.isDisplayed();
    }

    public AppearancePage clickAppearanceButton() {
        appearanceButton.click();

        return new AppearancePage(getDriver());
    }

    public boolean areToolsAndActionsSectionsEnabled() {
        return areElementsEnabled(toolsAndActionsSections);
    }

    public UsersPage clickUsers() {
        usersLink.click();

        return new UsersPage(getDriver());
    }

    public boolean isSearchFieldActivateElement() {
        return searchInput.equals(getDriver().switchTo().activeElement());
    }

    public boolean isSearchHintDisplayed() {
        return searchHint.isDisplayed();
    }

    public ManageJenkinsPage pressSlashKey() {
        securityLink.sendKeys("/");

        return new ManageJenkinsPage(getDriver());
    }

    public ManageJenkinsPage hoverMouseOverTheTooltip() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(shortcut);
        actions.perform();

        return new ManageJenkinsPage(getDriver());
    }

    public String getSearchHintText() {
        return searchHint.getAttribute("tooltip");
    }

    public NodesTablePage clickNodes() {
        nodesButton.click();

        return new NodesTablePage(getDriver());
    }

    public String getPageHeadingText() {
        return pageHeading.getText();
    }

    public ReloadConfigurationDialog clickReloadConfigurationFromDisk() {
        reloadConfigurationFromDiskLink.click();

        return new ReloadConfigurationDialog(getDriver());
    }

    public String getSearchInputPlaceholderText() {
        return searchInput.getDomProperty("placeholder");
    }

    public ManageJenkinsPage typeSearchSettingsRequest(String request) {
        searchInput.sendKeys(request);

        return this;
    }

    public String getNoSearchResultsPopUpText() {
        return getWait2().until(ExpectedConditions.visibilityOf(noSearchResultsPopUp)).getText();
    }

    public <T> T clickSecondSearchResult(T page) {
        getWait2().until(ExpectedConditions.visibilityOf(secondSearchResult)).click();

        return page;
    }

    public boolean areSectionsLinksClickable() {
        for (WebElement element : sectionsLinksList) {
            try {
                getWait2().until(ExpectedConditions.elementToBeClickable(element));
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public Integer getNumberOfSectionLinks() {
        return sectionsLinksList.size();
    }

    public Map<String, String> getSystemInformationBlockTitlesAndDescriptions() {
        Map<String, String> actualTitlesAndDescriptions = new LinkedHashMap<>();
        for (int i = 0; i < systemInformationBlockTitles.size(); i++) {
            String actualTitle = systemInformationBlockTitles.get(i).getText();
            String actualDescription = systemInformationBlockDescriptions.get(i).getText();

            actualTitlesAndDescriptions.put(actualTitle, actualDescription);
        }
        return actualTitlesAndDescriptions;
    }

    public Map<String, String> getToolsAndActionsBlockTitlesAndDescriptions() {
        Map<String, String> actualTitlesAndDescriptions = new LinkedHashMap<>();
        for (int i = 0; i < toolsAndActionsBlockTitles.size(); i++) {
            String actualTitle = toolsAndActionsBlockTitles.get(i).getText();
            String actualDescription = toolsAndActionsBlockDescriptions.get(i).getText();

            actualTitlesAndDescriptions.put(actualTitle, actualDescription);
        }
        return actualTitlesAndDescriptions;
    }

    public boolean areToolsAndActionsSectionsAndDescriptionsMatchingInCorrectOrder(Map<String, String> expected) {
        int index = 0;
        for (Map.Entry<String, String> expectedEntry : expected.entrySet()) {
            Iterator<Map.Entry<String, String>> iterator = getToolsAndActionsBlockTitlesAndDescriptions().entrySet().iterator();
            Map.Entry<String, String> actualEntry = null;
            for (int i = 0; i <= index; i++) {
                actualEntry = iterator.next();
            }
            if (!actualEntry.getKey().equals(expectedEntry.getKey()) ||
                    !actualEntry.getValue().equals(expectedEntry.getValue())) {
                return false;
            }
            index++;
        }
        return true;
    }

    public String clickManageLink(String link) {
        getDriver().findElement(By.xpath("//dt[text()='" + link + "']")).click();

        return getDriver().getCurrentUrl();
    }

    public List<String> getSecurityBlockElementList() {
        List<String> textList = new ArrayList<>();
        List<WebElement> securityBlockElementList = securitySectionNameList;

        for (WebElement element : securityBlockElementList) {
            textList.add(element.getText());
        }

        return textList;
    }

    public List<String> getSecurityBlockDescriptionList() {
        List<String> textList = new ArrayList<>();
        List<WebElement> securityDescriptionList = securitySectionDescriptionList;

        for (WebElement element : securityDescriptionList) {
            textList.add(element.getText());
        }

        return textList;
    }

    public List<String> getListOfManageJenkinsLinks() {
        List<String> linkTextList = new ArrayList<>();
        List<WebElement> linkList = manageJenkinsLinkList;

        for (WebElement link : linkList) {
            linkTextList.add(link.getText());
        }

        return linkTextList;
    }
}