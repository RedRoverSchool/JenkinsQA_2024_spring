package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    @Step("Click on the button 'Mark this node temporarily offline'")
    public NodeManagePage clickMarkThisNodeTemporaryOfflineButton() {
        markThisNodeTemporaryOfflineButton.click();
        return new NodeManagePage(getDriver());
    }

    @Step("Confirm switch by click on the button 'Mark this node temporarily offline'")
    public NodeManagePage clickMarkThisNodeTemporaryOfflineConfirmationButton() {
        markThisNodeTemporaryOfflineConfirmationBtn.click();
        return new NodeManagePage(getDriver());
    }

    @Step("Click on the button 'Bring This Node Back Online'")
    public NodeManagePage clickBringThisNodeBackOnlineButton() {
        try {
            getWait5().until(ExpectedConditions.elementToBeClickable(bringThisNodeBackOnlineBtn)).click();
        } catch (Exception e) {

        }
        return new NodeManagePage(getDriver());
    }

    public String getNodeOfflineStatusText() {
        return nodeOfflineStatusMessageList.get(0).getText();
    }

    public Boolean isNodeOfflineStatusMessageDisplayed() {
        return !nodeOfflineStatusMessageList.isEmpty();
    }
}
