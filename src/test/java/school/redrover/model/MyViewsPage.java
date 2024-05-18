package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;

public class MyViewsPage extends BasePage {

    public MyViewsPage(WebDriver driver) {
        super(driver);
    }

    public String getMyViewsPageUrl() {
        return TestUtils.getBaseUrl() + "/me/my-views/view/all/";
    }
}
