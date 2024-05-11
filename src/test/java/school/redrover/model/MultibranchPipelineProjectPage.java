package school.redrover.model;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import school.redrover.model.base.BaseProjectPage;

public class MultibranchPipelineProjectPage extends BaseProjectPage {


    @FindBy(xpath = "//span[.='Configure the project']")
    private WebElement configureButton;

    @FindBy(name = "Submit")
    private WebElement disableEnableMPButton;


    @FindBy(id = "enable-project")
    private WebElement disableMPMessage;

    @FindBy(xpath = "//form[contains(., 'This Multibranch Pipeline is currently disabled')]")
    private List<WebElement> disabledMultiPipelineMessage;

    @FindBy(tagName = "h1")
    private WebElement name;

    @FindBy(xpath = "//div[@id='main-panel']/h1")
    private WebElement projectName;

    @FindBy(css = "a[href$='rename']")
    private WebElement sidebarRenameButton;

    @FindBy(css = "[class^='task-link-wrapper']")
    private List<WebElement> sidebarTasksList;

    public MultibranchPipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineConfigPage selectConfigure() {
        configureButton.click();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public MultibranchPipelineProjectPage clickDisableEnableMultibranchPipeline() {
        disableEnableMPButton.click();

        return this;
    }

    public String getDisableMultibranchPipelineButtonText() {
        return disableEnableMPButton.getText().trim();
    }


    public String getDisableMultibranchPipelineText() {
        return disableMPMessage.getDomProperty("innerText").split(" Enable")[0];
    }

    public boolean isMultibranchPipelineDisabledTextNotDisplayed() {
        return disabledMultiPipelineMessage.isEmpty();
    }

    public String getDisableMultibranchPipelineTextColor() {
        return disableMPMessage.getCssValue("color");
    }

    public String getProjectNameText() {

        return projectName.getText();
    }

    public String getMultibranchPipelineName() {
        return name.getText();
    }

    public MultibranchPipelineRenamePage clickSidebarRenameButton() {
        sidebarRenameButton.click();

        return new MultibranchPipelineRenamePage(getDriver());
    }

    public List<String> getSidebarTasksListHavingExistingFolder() {
        return sidebarTasksList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public Integer getSidebarTasksSize() {
        return sidebarTasksList.size();
    }
}