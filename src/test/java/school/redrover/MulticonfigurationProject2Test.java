package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProject2Test extends BaseTest {

    private static final String EXPECTED_DESCRIPTION = "There is a description for MulticonfigurationProject2!";
    private static final String PROJECT_NAME = "MulticonfigurationProject2";

    @Test
    public void testCreateMulticonfigurationProject() {
        createMulticonfigurationProject();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'main-panel']/h1")).getText(),
                "Project " + PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateMulticonfigurationProject")
    public void testAddDescriptionToMulticonfigurationProject() {
        addDescription();
        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//div[@class='jenkins-!-margin-bottom-0']"))
                .getText()
                .contains(EXPECTED_DESCRIPTION));
    }

    private void createMulticonfigurationProject() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id ='name']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[normalize-space()='Multi-configuration project']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']")).click();
        getDriver().findElement(By.xpath("//td//a[@href = 'job/" + PROJECT_NAME + "/']")).click();
    }

    private void addDescription() {
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//*[@name='description']")).sendKeys(EXPECTED_DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }
}