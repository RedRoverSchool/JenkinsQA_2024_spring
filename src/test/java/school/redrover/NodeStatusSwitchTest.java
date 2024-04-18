package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NodeStatusSwitchTest extends BaseTest {
    private void clickOn(String xpath) {
        getDriver().findElement(By.xpath(xpath)).click();
    }

    @Test
    public void testSwitchToOfflineStatus() {
        clickOn("//a[normalize-space()='Build Executor Status']");
        clickOn("//a[@href='../computer/(built-in)/']");
        clickOn("//div[@class='jenkins-app-bar__controls']");
        clickOn("//button[@name='Submit']");

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class='message']")).getText(),
                "Disconnected by admin");
    }
}
