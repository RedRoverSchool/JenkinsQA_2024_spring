package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.MultiConfigurationConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class MyViewsTest extends BaseTest {
    public static final String multiConfigurationProjectName = "MultiConfigurationProject";
    public static final String viewName = "NewView";
    @Test
    public void testCreateNewView() {

        String newViewName =
                new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(multiConfigurationProjectName)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.MULTI_CONFIGURATION_PROJECT, new MultiConfigurationConfigPage(getDriver()))
                .clickLogo()
                .clickPlusForCreateView()
                .setViewName(viewName)
                .clickMyViewRadioButton()
                .clickCreateMyView()
                .getNewViewName();

       Assert.assertEquals(newViewName, viewName);
    }
}
