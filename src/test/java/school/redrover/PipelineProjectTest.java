package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

        getDriver().findElement(By.id("itemname-invalid")).getText();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//span[text()='Random pipeline']")).click();
        getDriver().findElement(By.xpath("//span[text()='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//*[@data-id='ok']")).click();

        String welcomeJenkinsText = getDriver().findElement(
                By.xpath("//*[text()='Welcome to Jenkins!']")).getText();

        Assert.assertEquals(welcomeJenkinsText, "Welcome to Jenkins!");
    }

    private void createNewJob(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
    }
}
