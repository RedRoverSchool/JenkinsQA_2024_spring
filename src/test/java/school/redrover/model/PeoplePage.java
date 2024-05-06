package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;

public class PeoplePage extends BasePage {

    public PeoplePage(WebDriver driver) {
        super(driver);
    }

    public String getPeoplePageUrl() {
        return TestUtils.getBaseUrl() + "/asynchPeople/";
    }
}
