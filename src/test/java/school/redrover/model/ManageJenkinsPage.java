package school.redrover.model;

import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;

public class ManageJenkinsPage extends BasePage {

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public String getManageJenkinsPage() {
        return TestUtils.getBaseUrl() + "/manage/";
    }
}
