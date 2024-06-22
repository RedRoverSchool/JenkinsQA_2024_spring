package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ViewPage extends HomePage {

    @FindBy(linkText = "Edit View")
    private WebElement editViewButton;

    @FindBy(xpath = "//a[@data-title='Delete View']")
    private WebElement deleteViewButton;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement confirmButton;


    @FindBy(xpath = "(//li[@class='children'])[2]")
    private WebElement allDropdownChevron;

    public ViewPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Edit View' link on the side bar menu")
    public ListViewConfigPage clickEditViewOnSidebar() {
        editViewButton.click();

        return new ListViewConfigPage(getDriver());
    }

    @Step("Click 'Delete View' link on the sidebar menu")
    public ViewPage clickDeleteViewOnSidebar() {
        deleteViewButton.click();

        return this;
    }

    @Step("Click 'Yes' to confirm deletion")
    public HomePage clickYesToConfirmDeletion() {
        getWait5().until(ExpectedConditions.elementToBeClickable(confirmButton)).click();

        return new HomePage(getDriver());
    }

    @Step("Click 'All' dropdown menu on breadcrumbs")
    public ViewPage clickAllDropdownMenuOnBreadcrumbs() {
        allDropdownChevron.click();

        return this;
    }

    @Step("Select project '{name}' and click")
    public FolderProjectPage clickOnProjectNameOnBreadcrumbsMenu(String name) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//a[@class = 'jenkins-dropdown__item'][contains(@href," + name + ")]"))).click();

        return new FolderProjectPage(getDriver());
    }
}

