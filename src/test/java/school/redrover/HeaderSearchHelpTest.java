package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class HeaderSearchHelpTest extends BaseTest {

    @Test
    public void testAccessToUserDoc(){
        getDriver().findElement(By.xpath("//a[@class='main-search__icon-trailing']")).click();

        String actualHandbook = getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sidebar-content']/h5"))).getText();
        String actualTutorials = getDriver().findElement(By.xpath("(//div[@id='sidebar-content']/h5)[2]")).getText();
        String actualResources = getDriver().findElement(By.xpath("(//div[@id='sidebar-content']/h5)[3]")).getText();

        Assert.assertEquals(actualHandbook, "User Handbook");
        Assert.assertEquals(actualTutorials, "Tutorials");
        Assert.assertEquals(actualResources, "Resources");
    }
}