package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class DashboardTest extends BaseTest {

    @Test
    public void  userIdTest (){
        getDriver().findElement(By.xpath("//div/a[@href and @class='model-link']"))
                   .click();
        String idText = getDriver().findElement(By.xpath("//*[contains(text(), 'ID')]")).getText();
        Assert.assertTrue(idText.contains("Jenkins User ID"));
    }
}
