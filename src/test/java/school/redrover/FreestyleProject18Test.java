package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.Assert;
import school.redrover.runner.BaseTest;
import static school.redrover.runner.TestUtils.*;

public class FreestyleProject18Test extends BaseTest {
    final  String projectItemName = "JavaHashGroupProject";

    public void testPreconditionCreateProject() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='tasks']//div[1]//span[1]//a[1]"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='name']"))).sendKeys(projectItemName);
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='hudson_model_FreeStyleProject']"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='ok-button']"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).sendKeys("test for JavaHashGroupProject ");
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href = '/']"))).click();
    }


    @Test
    public void testDeleteProjectAnother(){

        testPreconditionCreateProject();

        WebElement findProject = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='JavaHashGroupProject']")));

        Assert.assertEquals(projectItemName,findProject.getText());

        findProject.click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class,'confirmation-link')]"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Yes']"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href = '/']"))).click();

        Assert.assertTrue(getWait2().until(ExpectedConditions.visibilityOfElementLocated(EMPTY_STATE_BLOCK)).isDisplayed());
    }
}
