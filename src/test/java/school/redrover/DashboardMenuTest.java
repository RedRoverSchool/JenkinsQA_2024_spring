package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardMenuTest extends BaseTest {

    @Test
    public void testDashboardMenu() {

                List<String> expectedDashboardMenu = Arrays.asList(
                "New Item",
                "People",
                "Build History",
                "Manage Jenkins",
                "My Views");

       getDriver().findElement(By.xpath("//ol[@id='breadcrumbs']/li[1]")).click();

        int actualDashboardMenuSize = getDriver()
                .findElements(By.xpath("//div[@id='tasks']/div"))
                .size();

        List<WebElement> dashboardMenu = getDriver().findElements(By.xpath("//div[@id='tasks']/div"));
        List<String> actualDashboardMenu = webElementsToString(dashboardMenu);

        Assert.assertEquals(actualDashboardMenuSize, 5);
        Assert.assertEquals(actualDashboardMenu, expectedDashboardMenu );
    }

    private static List<String> webElementsToString(List<WebElement> dashboardMenu) {
        List<String> stringList = new ArrayList<>();
        for (WebElement element : dashboardMenu) {
            stringList.add(element.getText());
        }

        return stringList;
    }
}
