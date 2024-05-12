package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class CheckVersionTest extends BaseTest {

    @Test
    public void testCheckVersion() {
        String jenkinsVersion = new HomePage(getDriver())
                .clickVersion()
                .clickAbout()
                .jenkinsVersion();

        Assert.assertEquals(jenkinsVersion, "Version 2.440.2");
    }
}

