package school.redrover;

import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.*;
import school.redrover.runner.*;

import java.util.*;

public class ManageJenkins2Test extends BaseTest {
   private static final Map<String, String> manageLinks = new HashMap<>() {{
        put("System", "/configure");
        put("Tools", "/configureTools");
        put("Plugins", "/pluginManager");
        put("Nodes", "/computer");
        put("Clouds", "/cloud");
        put("Appearance", "/appearance");
        put("Security", "/configureSecurity");
        put("Credentials", "/credentials");
        put("Credential Providers", "/configureCredentials");
        put("Users", "/securityRealm");
        put("System Information", "/systemInfo");
        put("System Log", "/log");
        put("Load Statistics", "/load-statistics");
        put("About Jenkins", "/about");
        put("Manage Old Data", "/OldData");
        put("Jenkins CLI", "/cli");
        put("Script Console", "/script");
        put("Prepare for Shutdown", "/prepareShutdown");
    }};

    @DataProvider(name = "linksDataProvider")
    public Object[][] linksDataProvider() {
        Object[][] data = new Object[manageLinks.size()][2];
        int index = 0;
        for (Map.Entry<String, String> entry : manageLinks.entrySet()) {
            data[index][0] = entry.getKey();
            data[index][1] = entry.getValue();
            index++;
        }
        return data;
    }

    @Test(dataProvider = "linksDataProvider")
    public void testManageJenkinsLinks(String key, String value) {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text()='" + key + "']")).click();

        String actualUrl = getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(value));
    }

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
