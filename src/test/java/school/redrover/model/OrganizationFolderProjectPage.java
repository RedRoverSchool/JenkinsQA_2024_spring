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
    private WebElement pipelineSyntaxButton;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButtonOnDeleteOrganizationFolderAlert;

    @FindBy(xpath = "//a[contains(@href,'console')]")
    private WebElement scanButton;

    public OrganizationFolderProjectPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Configure' on sidebar menu")
    public OrganizationFolderConfigPage clickConfigure() {
        configureButton.click();

        return new OrganizationFolderConfigPage(getDriver());
    }

    public String getOrganizationFolderIcon() {
        return itemIcon.getAttribute("title");
    }

    @Step("Click on 'Pipeline Syntax' in the sidebar menu")
    public PipelineSyntaxPage clickSidebarPipelineSyntax() {
        pipelineSyntaxButton.click();

        return new PipelineSyntaxPage(getDriver());
    }

    public OrganizationFolderProjectPage clickSidebarScanOrganizationFolderLog(){
        scanButton.click();
        return this;
    }

    public String getScanText() {
        return scanText.getText();
    }

    @Step("Click 'Yes' button in the confirming deletion dialog")
    public HomePage clickYesForDeleteOrganizationFolder() {
        yesButtonOnDeleteOrganizationFolderAlert.click();
        return new HomePage(getDriver());
    }
}
