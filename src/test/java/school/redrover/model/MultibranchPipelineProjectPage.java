package school.redrover.model;

import io.qameta.allure.Step;
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
    private WebElement renameOnSidebar;

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

    @Step("Click 'Configure' on sidebar")
    public MultibranchPipelineConfigPage clickConfigure() {
        configureButton.click();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    @Step("Click 'Enable' button")
    public MultibranchPipelineProjectPage clickEnableButton() {
        enableMPButton.click();

        return this;
    }

    @Step("Click 'Disable Multibranch Pipeline'")
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

    @Step("Click 'Rename' on sidebar menu")
    public MultibranchPipelineRenamePage clickRenameOnSidebarMenu() {
        renameOnSidebar.click();

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

    @Step("Click 'Delete Multibranch Pipeline' on sidebar menu")
    public DeleteDialog clickDeleteOnSideBarMenu() {
        sidebarDeleteButton.click();

        return new DeleteDialog(getDriver());
    }

    @Step("Click Dropdown arrow on breadcrumbs for Multibranch Ppeline")
    public MultibranchPipelineProjectPage clickMultibranchPipelineDropdownArrowOnBreadcrumbs() {
        clickSpecificDropdownArrow(breadcrumbsDropdownArrow);

        return this;
    }

    @Step("Click 'Delete Multibranch Pipeline' on breadcrumbs dropdown menu")
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

    @Step("Click 'Move' on sidebar menu")
    public MovePage clickMoveOnSidebar() {
        moveOnSidebar.click();

        return new MovePage(getDriver());
    }
}
