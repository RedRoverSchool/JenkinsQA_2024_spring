package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseProjectPage;

import java.util.ArrayList;
import java.util.List;

public class MultibranchPipelineProjectPage extends BaseProjectPage<MultibranchPipelineProjectPage> {

    @FindBy(xpath = "//span[.='Configure the project']")
    private WebElement configureButton;

    @FindBy(name = "Submit")
    private WebElement enableMPButton;

    @FindBy(xpath = "//form[@id='disable-project']/button")
    private WebElement disableProjectButton;

    @FindBy(id = "enable-project")
    private WebElement disableMPMessage;

    @FindBy(xpath = "//*[contains(@data-title,'Delete')]")
    private WebElement sidebarDeleteButton;

    @FindBy(xpath = "//*[contains(@data-id,'ok')]")
    private WebElement confirmDeleteButton;

    @FindBy(css = "a[href$='rename']")
    private WebElement sidebarRenameButton;

    @FindBy(css = "[href $='move']")
    private WebElement moveOnSidebar;

    @FindBy(css = "[class^='task-link-wrapper']")
    private List<WebElement> sidebarTasksList;

    @FindBy(css = "a[href^='/job'] > button")
    private WebElement breadcrumbsDropdownArrow;

    @FindBy(css = "[class*='dropdown'] [href$='doDelete']")
    private WebElement deleteMultibranchPipelineInBreadcrumbsLink;

    public MultibranchPipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineConfigPage selectConfigure() {
        configureButton.click();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public MultibranchPipelineProjectPage clickEnableButton() {
        enableMPButton.click();

        return this;
    }

    public MultibranchPipelineProjectPage clickDisableProjectButton() {
        disableProjectButton.click();

        return this;
    }

    public String getDisableMultibranchPipelineButtonText() {
        return enableMPButton.getText().trim();
    }


    public String getDisableMultibranchPipelineText() {
        return disableMPMessage.getDomProperty("innerText").split(" Enable")[0];
    }

    public String getDisableMultibranchPipelineTextColor() {
        return disableMPMessage.getCssValue("color");
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

    public MultibranchPipelineProjectPage clickDeleteButton() {
        sidebarDeleteButton.click();
        return this;
    }

    public HomePage confirmDeleteButton() {
        confirmDeleteButton.click();
        return clickLogo();
    }

    public MultibranchPipelineProjectPage clickMPDropdownArrow() {
        clickSpecificDropdownArrow(breadcrumbsDropdownArrow);

        return this;
    }

    public DeleteDialog clickDeleteMultibranchPipelineInBreadcrumbs(DeleteDialog dialog) {
        deleteMultibranchPipelineInBreadcrumbsLink.click();

        return dialog;
    }

    public List<String> getProjectSidebarList() {
        List<String> sideBarItemList = new ArrayList<>();
        List<WebElement> taskElements = sidebarTasksList;

        for (WebElement task : taskElements) {
            sideBarItemList.add(task.getText());
        }

        return sideBarItemList;
    }

    public MovePage clickMoveOnSidebar(String name) {
        moveOnSidebar.click();

        return new MovePage(getDriver());
    }
}
