package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class NodeManagePage extends BasePage<NodeManagePage> {
    public NodeManagePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='jenkins-app-bar__controls']")
    private WebElement markThisNodeTemporaryOfflineButton;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement markThisNodeTemporaryOfflineConfirmationBtn;

    @FindBy(css = ".jenkins-button.jenkins-button--primary")
    private WebElement bringThisNodeBackOnlineBtn;

    @FindBy(css = ".message")
    private List<WebElement> nodeOfflineStatusMessageList;

    public NodeManagePage clickMarkThisNodeTemporaryOfflineButton() {
        markThisNodeTemporaryOfflineButton.click();
        return new NodeManagePage(getDriver());
    }

    public NodeManagePage clickMarkThisNodeTemporaryOfflineConfirmationButton() {
        markThisNodeTemporaryOfflineConfirmationBtn.click();
        return new NodeManagePage(getDriver());
    }

    public NodeManagePage clickBringThisNodeBackOnlineButton() {
        bringThisNodeBackOnlineBtn.click();
        return new NodeManagePage(getDriver());
    }

    public String getNodeOfflineStatusText() {
        return nodeOfflineStatusMessageList.get(0).getText();
    }

    public Boolean isNodeOfflineStatusMessageDisplayed() {
        return !nodeOfflineStatusMessageList.isEmpty();
    }
}
