package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class MyViewsTest extends BaseTest {
    public static final String multiConfigurationProjectName = "MultiConfigurationProject";
    public static final String viewName = "NewView";
    @Test
    public void testCreateNewView() {

        String newViewName =
                new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(multiConfigurationProjectName)
                .selectMultiConfigurationAndClickOk()
                .clickLogo()
                .clickPlusForCreateView()
                .setViewName(viewName)
                .clickMyViewRadioButton()
                .clickCreateMyView()
                .getNewViewName();

       Assert.assertEquals(newViewName, viewName);
    }
}
