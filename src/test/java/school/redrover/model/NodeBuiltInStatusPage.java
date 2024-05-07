package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import school.redrover.model.base.BasePage;

import java.util.Collections;
import java.util.List;

public class NodeBuiltInStatusPage extends BasePage {

    @FindBy(className = "advancedButton")
    private WebElement monitoringDataButton;

    @FindBy(css = "[class*='jenkins-table'] td:nth-of-type(odd)")
    private List<WebElement> monitoringDataElements;

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

    public void assertMonitoringDataValues(List<String> actualMonitoringDataValues,
                                           List<String> expectedMonitoringDataValues) {
        try {
            Assert.assertEquals(actualMonitoringDataValues, expectedMonitoringDataValues);
        } catch (AssertionError e) {
            Collections.sort(expectedMonitoringDataValues);
            Assert.assertEquals(actualMonitoringDataValues, expectedMonitoringDataValues,
                    "Actual Monitoring Data list is different after sorting expected values alphabetically");
        }
    }
}
