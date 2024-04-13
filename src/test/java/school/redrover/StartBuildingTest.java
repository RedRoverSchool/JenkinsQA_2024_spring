package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class StartBuildingTest extends BaseTest {

    @Test
    public  void testStartBuilding() {

        WebElement startBuilding = getDriver().findElement(By.xpath("//h2[text()='Start building your software project']"));

        Assert.assertEquals(startBuilding.getText(), "Start building your software project");
    }
}
