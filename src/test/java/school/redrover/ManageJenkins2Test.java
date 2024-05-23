package school.redrover;

import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.*;
import school.redrover.model.*;
import school.redrover.runner.*;

import java.util.*;

import static java.util.Map.entry;

public class ManageJenkins2Test extends BaseTest {


    @Ignore
    @Test
    public void testListOfManageJenkinsLinks() {
        List<String> expectedLinksName = List.of
                ("System", "Tools", "Plugins", "Nodes", "Clouds", "Appearance", "Security", "Credentials",
                        "Credential Providers", "Users", "System Information", "System Log", "Load Statistics",
                        "About Jenkins", "Manage Old Data", "Reload Configuration from Disk", "Jenkins CLI",
                        "Script Console", "Prepare for Shutdown"
                );

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        List<WebElement> linksList = getDriver().findElements(By.xpath("//div[@class='jenkins-section__item']//dt"));

        List<String> actualListName = new ArrayList<>();
        for (WebElement link : linksList) {
            actualListName.add(link.getText());
        }

        Assert.assertEquals(actualListName, expectedLinksName);
    }
}
