package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static java.sql.DriverManager.getDriver;

public class EditPipelineTest extends BaseTest {
    public static final String NAME_PIPELINE = "Pipeline1";
    public static final String DESCRIPTION = "Пишем описание";

    @Test
    public void testCreatePipeline() {

        getDriver().findElement(By.linkText("New Item")).click();
        WebElement field = getDriver().findElement(By.className("jenkins-input"));
        field.sendKeys(NAME_PIPELINE);

        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testEditPipelineWithPreview() {
        testCreatePipeline();
        getDriver().findElement(
                By.xpath("//table//a[@href='job/" + NAME_PIPELINE + "/']")).click();
        getDriver().findElement(By.id("description-link")).click();

        WebElement fieldDescription = getDriver().findElement(By.name("description"));
        fieldDescription.sendKeys(DESCRIPTION);
        getDriver().findElement(By.className("textarea-show-preview")).click();

        Assert.assertEquals(getDriver().findElement(By.className("textarea-preview")).getText(), DESCRIPTION);

        getDriver().findElement(By.className("textarea-hide-preview")).click();
        getDriver().findElement(By.name("Submit")).click();

        String textDescription = getDriver().findElement(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[2]/div[1]")).getText();

        Assert.assertEquals(textDescription, DESCRIPTION);
    }
}
