package school.redrover.model.base;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;

import java.util.List;

public abstract class BaseProjectPage<T extends BaseProjectPage<T>> extends BasePage<T> {

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(tagName = "h1")
    private WebElement projectName;

    @FindBy(id = "description-link")
    private WebElement addOrEditDescriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionInput;

    @FindBy(css = "#description>:first-child")
    private WebElement displayedDescription;

    @FindBy(css = ".textarea-preview")
    private WebElement descriptionPreview;

    @FindBy(css = ".textarea-show-preview")
    private WebElement showDescriptionPreview;

    @FindBy(css = ".textarea-hide-preview")
    private WebElement hideDescriptionPreview;

    @FindBy(xpath = "//span[contains(text(), 'Delete')]")
    private WebElement sidebarDelete;

    @FindBy(css = "a[href*='rename']")
    private WebElement sidebarRename;

    @FindBy(css = "dialog .jenkins-button--primary")
    WebElement yesButton;

    @FindBy(css = "[class*='dropdown'] [href$='rename']")
    private WebElement breadcrumbsRename;

    @FindBy(css = "[href^='/job'] [class$='dropdown-chevron']")
    private WebElement breadcrumbsDropdownArrow;

    @FindBy(css = "[class*='dropdown'] [href$='Delete']")
    private WebElement breadcrumbsDelete;

    @FindBy(css = "[class*='breadcrumbs']>[href*='job']")
    private WebElement breadcrumbsName;

    @FindBys({
            @FindBy(id = "tasks"),
            @FindBy(className = "task-link-text")
    })
    private List<WebElement> taskList;

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {
        return projectName.getText();
    }

    @Step("Click on 'Add description'")
    public T clickAddDescription() {
        addOrEditDescriptionButton.click();

        return (T) this;
    }

    @Step("Click on 'Edit description'")
    public T clickEditDescription() {
        addOrEditDescriptionButton.click();

        return (T) this;
    }

    @Step("Check 'Add description button")
    public boolean isAddDescriptionButtonEnable() {
        return addOrEditDescriptionButton.isEnabled();
    }

    @Step("Clear previous description")
    public T clearDescription() {
        descriptionInput.clear();

        return (T) this;
    }

    @Step("Type description text in the input text area")
    public T typeDescription(String text) {
        descriptionInput.sendKeys(text);

        return (T) this;
    }

    public T waitAddDescriptionButtonDisappears() {
        getWait2().until(ExpectedConditions.invisibilityOf(addOrEditDescriptionButton));

        return (T) this;
    }

    public T clickOnDescriptionInput() {
        descriptionInput.click();

        return (T) this;
    }

    public String getColorOfDefaultTextAreaBorderBacklight() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        return (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--focus-input-glow');",
                descriptionInput);
    }

    @Step("Click on the 'Save' button")
    public T clickSaveButton() {
        saveButton.click();

        return (T) this;
    }

    @Step("Get description text")
    public String getDescriptionText() {
        return displayedDescription.getText();
    }

    public T clickShowDescriptionPreview() {
        showDescriptionPreview.click();

        return (T) this;
    }

    public T clickHideDescriptionPreview() {
        hideDescriptionPreview.click();

        return (T) this;
    }

    public boolean isDescriptionPreviewVisible() {
        return descriptionPreview.isDisplayed();
    }

    public String getColorOfTextAreaBorderBacklight() {
        return getDriver().switchTo().activeElement().getCssValue("box-shadow").split(" 0px")[0];
    }

    public T clickDeleteOnSidebar() {
        sidebarDelete.click();

        return (T) this;
    }

    public T clickDeleteOnBreadcrumbsMenu() {
        breadcrumbsDelete.click();

        return (T) this;
    }


    public HomePage clickYes() {
        getWait5().until(ExpectedConditions.elementToBeClickable(yesButton)).click();
        this.clickLogo();

        return new HomePage(getDriver());
    }

    public String getYesButtonColorDeletingViaSidebar() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        return (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--color');",
                yesButton);
    }

    public ProjectRenamePage<T> clickRenameOnSidebar() {
        sidebarRename.click();

        return new ProjectRenamePage<>(getDriver(), (T) this);
    }

    public T clickBreadcrumbsDropdownArrow() {
        clickSpecificDropdownArrow(breadcrumbsDropdownArrow);

        return (T) this;
    }

    public ProjectRenamePage<T> clickRenameOnBreadcrumbsMenu() {
        breadcrumbsRename.click();

        return new ProjectRenamePage<>(getDriver(), (T) this);
    }

    public String getErrorText() {
        return new RenameErrorPage(getDriver()).getErrorText();
    }

    @Step("Check task presenсe on sidebar")
    public boolean isTaskPresentOnSidebar(String task) {
        getWait2().until(ExpectedConditions.visibilityOfAllElements(taskList));

        return taskList.stream()
                .anyMatch(element -> task.equals(element.getText()));
    }

    public T hoverOverBreadcrumbsName() {
        hoverOverElement(breadcrumbsName);

        return (T) this;
    }

}