package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.runner.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import java.util.List;

public class ManageJenkinsPage extends BasePage {

    @FindBy(css = "[href='configureSecurity']")
    private WebElement securityLink;

    @FindBy(className = "jenkins-search__input")
    private WebElement searchInput;

    @FindBy(css = "[href='appearance']")
    private WebElement appearanceButton;

    @FindBy(xpath = "(//div[@class='jenkins-section__items'])[5]/div[contains(@class, 'item')]")
    List<WebElement> toolsAndActionsSections;

    @FindBy(css = "[href='securityRealm/']")
    private WebElement usersLink;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public SecurityPage clickSecurity() {
        securityLink.click();

        return new SecurityPage(getDriver());
    }
  
    public String getManageJenkinsPage() {
        return TestUtils.getBaseUrl() + "/manage/";
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
}