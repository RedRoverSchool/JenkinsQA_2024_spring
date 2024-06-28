package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseConfigPage;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class OrganizationFolderConfigPage extends
        BaseConfigPage<OrganizationFolderProjectPage, OrganizationFolderConfigPage> {

    @FindBy(id = "tasks")
    private WebElement sidebarMenu;

    @FindBy(xpath = "(//select[contains(@class, 'dropdownList')])[2]")
    private WebElement iconDropdownList;

    @FindBy(xpath = "//div[text()='Project Recognizers']")
    private WebElement projectRecognizersBlock;

    @FindBy(xpath = "//div[text()='Project Recognizers']/following-sibling::div//button[contains(@class, 'add')]")
    private WebElement projectRecognizersAddButton;

    @FindBy(xpath = "//button[contains(text(), 'Pipeline Jenkinsfile')]")
    private WebElement pipelineJenkinsFileFilter;

    @FindBy(css = "[name='projectFactories'][class='repeated-chunk']")
    private List<WebElement> pipelineJenkinsFileList;

    @FindBy(css = "[class='task']:not(:first-child)")
    private List<WebElement> sidebarAnchorLinksExceptGeneral;

    @FindBy(css = "[class='jenkins-section__title'][id]")
    private List<WebElement> blocksHeadings;

    @FindBy(xpath = "//div[contains(text(), 'Property strategy')]")
    private WebElement propertyStrategyBlock;

    @FindBy(css = "[suffix='props'][class$='add']")
    private WebElement addPropertyButton;

    @FindBy(xpath = "//button[contains(text(), 'Throttle builds')]")
    private WebElement throttleBuildsDropdownOption;

    @FindBy(css = "[class$='select'] [fillurl*='fillDuration']")
    private WebElement timePeriodDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Untrusted')]")
    private WebElement untrustedDropdownOption;

    @FindBy(css = "[name='publisherWhitelist'][type='checkbox']")
    private List<WebElement> untrustedCheckboxesList;

    @FindBy(css = "[descriptorid*='UntrustedBranchProperty'] .dd-handle")
    private WebElement untrustedPropertyDragAndDropIcon;

    @FindBy(css = "[descriptorid$='RateLimitBranchProperty']")
    private WebElement throttleBuildsProperty;

    @FindBy(css = "[name='props'] [class='repeated-chunk__header']")
    private List<WebElement> addedPropertyStrategyList;

    public OrganizationFolderConfigPage(WebDriver driver) {
        super(driver, new OrganizationFolderProjectPage(driver));
    }

    public boolean isSidebarVisible() {
        return sidebarMenu.isDisplayed();
    }

    @Step("Select 'Default Icon' from 'Icon' dropdown list")
    public OrganizationFolderConfigPage selectDefaultIcon() {
        new Select(iconDropdownList)
                .selectByVisibleText("Default Icon");

        return new OrganizationFolderConfigPage(getDriver());
    }

    @Step("Scroll to 'Project Recognizers' block")
    public OrganizationFolderConfigPage scrollToProjectRecognizersBlock() {
        scrollToElement(projectRecognizersBlock);

        return this;
    }

    @Step("Click 'Add' button under 'Project Recognizers' block")
    public OrganizationFolderConfigPage clickProjectRecognizersAddButton() {
        projectRecognizersAddButton.click();

        return this;
    }

    @Step("Click 'Pipeline Jenkinsfile' from Filter list")
    public OrganizationFolderConfigPage addPipelineJenkinsFileFilter() {
        pipelineJenkinsFileFilter.click();

        return this;
    }

    public boolean areProjectRecognizersFiltersBordersDashed() {
        return pipelineJenkinsFileList
                .stream()
                .allMatch(element -> element.getCssValue("border").contains("dashed"));
    }

    public boolean isNavigatedToCorrespondingBlockClickingAnchorLink() {
        for (int i = 0; i < sidebarAnchorLinksExceptGeneral.size(); i++) {
            scrollToTopOfPage();
            sidebarAnchorLinksExceptGeneral.get(i).click();

            try {
                getWait2().until(isElementInViewPort(blocksHeadings.get(i)));
            } catch (TimeoutException e) {
                System.err.println(blocksHeadings.get(i).getText() + " is not in the viewport");
                return false;
            }
        }
        return true;
    }

    @Step("Scroll to 'Property strategy' block")
    public OrganizationFolderConfigPage scrollToPropertyStrategyBlock() {
        scrollToElement(propertyStrategyBlock);

        return this;
    }

    @Step("Click 'Add property' button")
    public OrganizationFolderConfigPage clickAddPropertyButton() {
        addPropertyButton.click();

        return this;
    }

    @Step("Click 'Throttle builds' from dropdown menu")
    public OrganizationFolderConfigPage clickThrottleBuildsDropdownOption() {
        throttleBuildsDropdownOption.click();

        return this;
    }

    public List<String> getTimePeriodOptions() {
        return new Select(timePeriodDropdown).getOptions()
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    @Step("Click 'Untrusted' on dropdown menu")
    public OrganizationFolderConfigPage clickUntrustedDropdownOption() {
        untrustedDropdownOption.click();

        return this;
    }

    @Step("Select all 'Untrusted' check boxes")
    public OrganizationFolderConfigPage selectUntrustedCheckboxes() {
        for (WebElement element : untrustedCheckboxesList) {
            element.click();
        }

        return this;
    }

    public boolean areUntrustedCheckboxesSelected() {
        for (WebElement element : untrustedCheckboxesList) {
            if (!element.isSelected()) {
                return false;
            }
        }
        return true;
    }

    @Step("Change blocks order by dragging 'ThrottleBuilds' to 'Untrusted'")
    public OrganizationFolderConfigPage changeUntrustedAndThrottleBuildsOrder() {
        new Actions(getDriver())
                .clickAndHold(untrustedPropertyDragAndDropIcon)
                .moveToElement(throttleBuildsProperty)
                .release(throttleBuildsProperty)
                .perform();

        return this;
    }

    public List<String> getAddedStrategyPropertyList() {
        return addedPropertyStrategyList
                .stream()
                .map(WebElement::getText)
                .map(text -> text.replace("\n?", ""))
                .toList();
    }
}
