package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import static school.redrover.runner.TestUtils.*;
import java.util.Arrays;
import java.util.List;

public class DashboardMenuTest extends BaseTest {

    @Test
    public void testDashboardMenu() {

        final List<String> expectedDashboardMenu = Arrays.asList(
                "New Item",
                "People",
                "Build History",
                "Manage Jenkins",
                "My Views");

       getDriver().findElement(By.xpath("//ol[@id='breadcrumbs']/li[1]")).click();

        int actualDashboardMenuSize = getDriver().findElements(By.xpath("//div[@id='tasks']/div")).size();

        List<WebElement> dashboardMenu = getDriver().findElements(By.xpath("//div[@id='tasks']/div"));
        List<String> actualDashboardMenu = getTexts(dashboardMenu);

        Assert.assertEquals(actualDashboardMenuSize, expectedDashboardMenu.size());
        Assert.assertEquals(actualDashboardMenu, expectedDashboardMenu );
    }
}
