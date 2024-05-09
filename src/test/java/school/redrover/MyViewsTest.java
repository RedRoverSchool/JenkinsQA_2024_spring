package school.redrover;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewViewPage;
import school.redrover.model.HomePage;
import school.redrover.model.ViewConfigPage;
import school.redrover.runner.BaseTest;

public class MyViewsTest extends BaseTest {
    public static final String multiConfigurationProjectName = "MultiConfigurationProject";
    public static final String viewName = "NewView";
    @Test
    public void testCreateNewView() {

        new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(multiConfigurationProjectName)
                .selectMultiConfigurationAndClickOk()
                .clickLogo()
                .clickNewView()
                .setViewName(viewName)
                .clickMyViewRadioButton()
                .clickCreateView();
               //.getNewViewName();
       //  String result2 = String.valueOf(new ViewConfigPage(getDriver())
         //  .getNewViewName());
//String result3 = new ViewConfigPage(getDriver()).getNewViewName();
//Assert.assertEquals(result3,viewName);

//         String result = getDriver().findElement(By.xpath("//div[@class='tab active']"))
//          .getText();
//        Assert.assertEquals(result, viewName);

    }

    private void getNewViewName() {
    }
}
