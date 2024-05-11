package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PeoplePage extends BasePage {

    @FindBy(css = "tr>td:nth-child(2)")
    private List<WebElement> userIDList;

    public PeoplePage(WebDriver driver) {
        super(driver);
    }

    public String getPeoplePageUrl() {
        return TestUtils.getBaseUrl() + "/asynchPeople/";
    }

    public UserPage clickUser(String userID) {
        getDriver().findElement(By.cssSelector("td > [href*='" + userID.toLowerCase() + "']")).click();
        return new UserPage(getDriver());
    }


}
