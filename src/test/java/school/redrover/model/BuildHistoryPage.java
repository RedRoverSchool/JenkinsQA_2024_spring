package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;

public class BuildHistoryPage extends BasePage {

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public String getBuildHistoryPageUrl() {
        return TestUtils.getBaseUrl() + "/view/all/builds";
    }
}
