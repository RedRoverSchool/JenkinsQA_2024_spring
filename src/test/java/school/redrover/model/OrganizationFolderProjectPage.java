package school.redrover.model;

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
    private WebElement pipelineSyntaxButton;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButtonOnDeleteOrganizationFolderAlert;

    @FindBy(xpath = "//a[contains(@href,'console')]")
    private WebElement scanButton;

    public OrganizationFolderProjectPage(WebDriver driver) {
        super(driver);
    }

    public OrganizationFolderConfigPage clickConfigure() {
        configureButton.click();

        return new OrganizationFolderConfigPage(getDriver());
    }

    public String getOrganizationFolderIcon() {
        return itemIcon.getAttribute("title");
    }

    public PipelineSyntaxPage clickSidebarPipelineSyntax() {
        pipelineSyntaxButton.click();

        return new PipelineSyntaxPage(getDriver());
    }

    public OrganizationFolderProjectPage clickSidebarScanOrganizationFolderLog(){
        scanButton.click();
        return this;
    }

    public HomePage clickYesForDeleteOrganizationFolder() {
        yesButtonOnDeleteOrganizationFolderAlert.click();
        return new HomePage(getDriver());
    }
}