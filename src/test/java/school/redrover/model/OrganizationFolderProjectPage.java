package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseProjectPage;

public class OrganizationFolderProjectPage extends BaseProjectPage<OrganizationFolderProjectPage> {

    @FindBy(css = ".task [href$='configure']")
    private WebElement configureButton;

    @FindBy(css = "h1 > svg")
    private WebElement itemIcon;

    @FindBy(xpath = "//a[contains(@href,'pipeline-syntax')]")
    private WebElement sidebarPipelineSyntax;

    @FindBy(xpath = "//a[contains(@href,'console')]")
    private WebElement sidebarScanOrganizationFolderLog;

    public OrganizationFolderProjectPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Configure' on sidebar menu")
    public OrganizationFolderConfigPage clickConfigure() {
        configureButton.click();

        return new OrganizationFolderConfigPage(getDriver());
    }

    @Step("Get attribute 'title' from Organization Folder icon")
    public String getAttributeTitleFromOrganizationFolderIcon() {
        return itemIcon.getAttribute("title");
    }

    @Step("Click on 'Pipeline Syntax' in the sidebar menu")
    public PipelineSyntaxPage clickSidebarPipelineSyntax() {
        sidebarPipelineSyntax.click();

        return new PipelineSyntaxPage(getDriver());
    }

    @Step("Click on the 'Scan Organization Folder Log' on the sidebar menu")
    public OrganizationFolderProjectPage clickSidebarScanOrganizationFolderLog() {
        sidebarScanOrganizationFolderLog.click();

        return this;
    }
}
