package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class NodeBuiltInStatusPage extends BasePage<NodeBuiltInStatusPage> {

    @FindBy(className = "advancedButton")
    private WebElement monitoringDataButton;

    @FindBy(css = "[class*='jenkins-table'] td:nth-of-type(odd)")
    private List<WebElement> monitoringDataElements;

    @FindBy(xpath = "//button[@class='jenkins-button jenkins-button--primary ']")
    private WebElement nodeOnlineButton;

    @FindBy(xpath = "//div[@class='jenkins-app-bar__controls']")
    private WebElement markThisNodeTemporaryOfflineButton;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement markThisNodeTemporaryOfflineConfirmationBtn;

    @FindBy(css = ".message")
    private List<WebElement> nodeOfflineStatusMessageList;

    @FindBy(css = ".jenkins-button.jenkins-button--primary")
    private WebElement bringThisNodeBackOnlineBtn;

    @FindBy(id = "jenkins-name-icon")
    private WebElement jenkinsIcon;

    public NodeBuiltInStatusPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the button 'Monitoring Data'")
    public NodeBuiltInStatusPage clickMonitoringDataButton() {
        monitoringDataButton.click();

        return this;
    }

    public List<String> getMonitoringDataElementsList() {
        return monitoringDataElements
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    @Step("Turn node 'On' if 'Offline' status")
    public HomePage turnNodeOnIfOffline() {
        try {
            nodeOnlineButton.click();
            jenkinsIcon.click();

            return new HomePage(getDriver());

        } catch (Exception e) {
            jenkinsIcon.click();

            return new HomePage(getDriver());
        }
    }

    @Step("Confirm switch by click on the button 'Mark this node temporarily offline'")
    public NodeBuiltInStatusPage clickMarkThisNodeTemporaryOfflineConfirmationButton() {
        markThisNodeTemporaryOfflineConfirmationBtn.click();

        return this;
    }

    @Step("Click on the button 'Mark this node temporarily offline'")
    public NodeBuiltInStatusPage clickMarkThisNodeTemporaryOfflineButton() {
        markThisNodeTemporaryOfflineButton.click();

        return this;
    }

    public String getNodeOfflineStatusText() {
        return nodeOfflineStatusMessageList.get(0).getText();
    }

    public Boolean isNodeOfflineStatusMessageDisplayed() {

        return !nodeOfflineStatusMessageList.isEmpty();
    }

    @Step("Click on the button 'Bring This Node Back Online'")
    public NodeBuiltInStatusPage clickBringThisNodeBackOnlineButton() {
        try {
            getWait5().until(ExpectedConditions.elementToBeClickable(bringThisNodeBackOnlineBtn)).click();
        } catch (Exception e) {

        }
        return this;
    }
}

