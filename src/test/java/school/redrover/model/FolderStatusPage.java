package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FolderStatusPage extends BasePage {

    @FindBy(css = "[class*='breadcrumbs']>[href*='job']")
    private WebElement breadcrumbsName;

    @FindBy(css = "[href*='confirm-rename']")
    private WebElement renameButton;

    @FindBy(linkText = "Rename")
    private WebElement renameItemLink;

    @FindBy(linkText = "Delete Folder")
    private WebElement deleteFolderLink;

    public FolderStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getBreadcrumbName() {
        return breadcrumbsName.getText();
    }

    public FolderRenamePage clickOnRenameButton() {
        renameButton.click();

        return new FolderRenamePage(getDriver());
    }

    public RenameItemPage clickRenameMenu () {
        renameItemLink.click();

        return new RenameItemPage(getDriver());
    }

    public DeleteDialog clickDeleteMenu () {
        deleteFolderLink.click();

        return new DeleteDialog (getDriver());
    }
}
