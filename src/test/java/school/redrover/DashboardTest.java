package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.DashboardPage;
import school.redrover.runner.BaseTest;

import java.util.Arrays;
import java.util.List;

public class DashboardTest extends BaseTest {

    @Test
    public void  userIdTest (){
        getDriver().findElement(By.xpath("//div/a[@href and @class='model-link']"))
                   .click();
        String idText = getDriver().findElement(By.xpath("//*[contains(text(), 'ID')]")).getText();
        Assert.assertTrue(idText.contains("Jenkins User ID"));
    }

    @Test
    public void testDashboardMenu() {
        final List<String> expectedDashboardMenu = Arrays.asList(
                "New Item",
                "People",
                "Build History",
                "Manage Jenkins",
                "My Views");

        List<String> actualDashboardMenu = new DashboardPage(getDriver())
                .getDashboardMenuList();

        Assert.assertEquals(actualDashboardMenu, expectedDashboardMenu );
    }
}
