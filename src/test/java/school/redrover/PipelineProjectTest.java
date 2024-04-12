package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineProjectTest extends BaseTest {

    @Test
    public void testSameNamePipeline() {
        final String PROJECT_NAME = "Random pipeline";

        createNewJob(PROJECT_NAME);

        getDriver().findElement(By.xpath("//*[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        createNewJob(PROJECT_NAME);

        getDriver().findElement(By.xpath("//*[text()='Pipeline']")).click();
        // this line duplicates click on Pipeline, because of the Jenkins bug. Sometimes warning message doesn`t appear. Second click on Pipeline makes it happen.

        String warningMessage = getDriver().findElement(By.id("itemname-invalid")).getText();

        Assert.assertEquals(warningMessage, "» A job already exists with the name ‘" + PROJECT_NAME + "’");
    }

    private void createNewJob(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
    }
}
