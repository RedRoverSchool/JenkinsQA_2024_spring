package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.ArrayList;
import java.util.List;

public class DashboardPage extends BasePage {

    @FindBy(xpath = "//div[@id='tasks']/div")
    private List<WebElement> dashboardMenuList;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getDashboardMenuList() {
        List<String> menuList = new ArrayList<>();
        for (WebElement element : dashboardMenuList) {
            menuList.add(element.getText());
        }

        return menuList;
    }
}
