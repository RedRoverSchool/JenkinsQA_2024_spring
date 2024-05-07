package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class NodeStatusSwitchTest extends BaseTest {

    private void clickOn(String xpath) {
        getDriver().findElement(By.xpath(xpath)).click();
    }


    @Test
    public void testNumberOfItems() {

        HomePage homePage = new HomePage(getDriver());
        String text = homePage.getBuildExecutorStatusText();
        List<WebElement> buildExecutors = homePage.getBuildExecutorStatusList();
        int number = homePage.getBuildExecutorListSize();

        if(text.contains("( offline)")) {
            int number1 = buildExecutors.size();

            Assert.assertEquals(number, number1);

        } else if(number >= 1){
            int number2 = buildExecutors.size();

            Assert.assertEquals(number, number2);
        }
    }

    @Test(dependsOnMethods = "testNumberOfItems")
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
