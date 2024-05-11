package school.redrover.model;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class HeaderBlock extends BasePage {
    @FindBy(xpath = "//input[@id='search-box']")
    private WebElement searchBox;
    @FindBy(css = "[data-href*='user']")
    private WebElement adminDropdownChevron;
    @FindBy(css = "[href*='configure']")
    private WebElement adminDropdownConfigureLink;

    public HeaderBlock(WebDriver driver) {
        super(driver);
    }
     public HeaderBlock enterRequestIntoSearchBox(String requestData){
        searchBox.sendKeys(requestData);
        return this;
    }

    public SystemConfigurationPage makeClickToSearchBox(){
        searchBox.sendKeys(Keys.ENTER);
        return new SystemConfigurationPage (getDriver());
    }

    public AdminConfigurePage goToAdminConfigurePage() {
        new Actions(getDriver()).moveToElement(adminDropdownChevron)
                .click()
                .perform();
        adminDropdownConfigureLink.click();
        return new AdminConfigurePage(getDriver());
    }

    public SearchResultPage typeSearchQueryPressEnter(String searchQuery) {
        searchBox.sendKeys(searchQuery, Keys.ENTER);
        return new SearchResultPage(getDriver());
    }
}
