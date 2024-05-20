package school.redrover.model;

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

    @FindBy(css = "[class='task'] [data-section-id='projects']")
    private WebElement projectsAnchorLink;

    @FindBy(xpath = "//div[text()='Project Recognizers']")
    private WebElement projectRecognizersBlock;

    @FindBy(xpath = "//div[text()='Project Recognizers']/following-sibling::div//button[contains(@class, 'add')]")
    private WebElement projectRecognizersAddButton;

    @FindBy(xpath = "//button[contains(text(), 'Pipeline Jenkinsfile')]")
    private WebElement pipelineJenkinsFileFilter;

    @FindBy(css = "[name='projectFactories'][class='repeated-chunk']")
    private List<WebElement> pipelineJenkinsFileList;

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

    public OrganizationFolderConfigPage clickProjectsAnchorLink() {
        projectsAnchorLink.click();

        return this;
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

    public boolean areProjectRecognizersBoardersFiltersDashed() {
        for (WebElement element : pipelineJenkinsFileList) {
            if (element.getCssValue("border").contains("dashed")) {
                return true;
            }
        }
        return false;
    }
}
