package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class NodeBuiltInStatusPage extends BasePage<NodeBuiltInStatusPage> {

    @FindBy(className = "advancedButton")
    private WebElement monitoringDataButton;

    @FindBy(css = "[class*='jenkins-table'] td:nth-of-type(odd)")
    private List<WebElement> monitoringDataElements;

    @FindBy(xpath = "//button[@class='jenkins-button jenkins-button--primary ']")
    private WebElement nodeOnlineButton;

    public NodeBuiltInStatusPage(WebDriver driver) {
        super(driver);
    }

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

    public HomePage turnNodeOnIfOffline() {
        try {
            nodeOnlineButton.click();
            getDriver().findElement(By.id("jenkins-name-icon")).click();

            return new HomePage(getDriver());

        } catch (Exception e) {
            getDriver().findElement(By.id("jenkins-name-icon")).click();

            return new HomePage(getDriver());
        }
    }
}
