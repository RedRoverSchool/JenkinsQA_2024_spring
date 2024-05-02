package school.redrover;

import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.*;
import school.redrover.runner.*;

import java.util.*;

public class ManageJenkins2Test extends BaseTest {
    private static final Map<String, String> manageLinks = new HashMap<>(){{
        put("System", "configure");
        put("Tools", "configureTools");
        put("Plugins", "pluginManager");
        put("Nodes", "computer");
        put("Clouds", "cloud");
        put("Security", "configureSecurity");
        put("Credentials", "credentials");
        put("Credential Providers", "configureCredentials");
        put("Users", "securityRealm");
//        put("In-process Script Approval", "scriptApproval");
        put("System Information", "systemInfo");
        put("System Log", "log");
        put("Load Statistics", "load-statistics");
        put("About Jenkins", "about");
        put("Manage Old Data", "OldData");
        put("Jenkins CLI", "cli");
        put("Script Console", "script");
        put("Prepare for Shutdown", "prepareShutdown");
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
}
