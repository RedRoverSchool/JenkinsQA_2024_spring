package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.runner.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ManageJenkinsPage extends BasePage {

    @FindBy(css = "[href='configureSecurity']")
    private WebElement securityLink;

    @FindBy(className = "jenkins-search__input")
    private WebElement searchInput;

    @FindBy(className = "jenkins-search__shortcut")
    private WebElement shortcut;

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

    public boolean isShortcutDisplayed() {
        return shortcut.isDisplayed();
    }

    public ManageJenkinsPage pressSlashKey() {
        securityLink.sendKeys("/");

        return new ManageJenkinsPage(getDriver());
    }
}