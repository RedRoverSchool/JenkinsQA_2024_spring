package school.redrover.model;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseConfigPage;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class OrganizationFolderConfigPage extends BaseConfigPage<OrganizationFolderProjectPage> {

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

    public OrganizationFolderConfigPage(WebDriver driver) {
        super(driver, new OrganizationFolderProjectPage(driver));
    }

    public boolean isSidebarVisible() {
        return sidebarMenu.isDisplayed();
    }

    public OrganizationFolderConfigPage selectDefaultIcon() {
        new Select(iconDropdownList)
                .selectByVisibleText("Default Icon");

        return new OrganizationFolderConfigPage(getDriver());
    }

    public OrganizationFolderConfigPage scrollToProjectRecognizersBlock() {
        scrollToElement(projectRecognizersBlock);

        return this;
    }

    public OrganizationFolderConfigPage clickProjectRecognizersAddButton() {
        projectRecognizersAddButton.click();

        return this;
    }

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
}
