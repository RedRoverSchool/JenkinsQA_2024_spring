package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PipelineProject4Test extends BaseTest {
    List<String> nameProjects = List.of("PPProject", "PPProject2");
    @Test
    public void testVerifyNewPPCreatedByCreateJob() {

        getDriver().findElement(By.cssSelector("a[href='newJob']")).click();
        getDriver().findElement(By.cssSelector("div.add-item-name > input#name")).sendKeys(nameProjects.get(0));
        getDriver().findElement(By.cssSelector(".org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.cssSelector("button#ok-button")).click();

        getDriver().findElement(By.cssSelector("button.jenkins-button--primary")).click();

        getDriver().findElement(By.cssSelector("li.jenkins-breadcrumbs__list-item > a[href='/']")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("tr#job_" + nameProjects.get(0))).isDisplayed());

    }
    @Test(dependsOnMethods = "testVerifyNewPPCreatedByCreateJob")
    public void testVerifyNewPPCreatedNewItem() {

        TestUtils.createNewItemAndReturnToDashboard(this, nameProjects.get(1), TestUtils.Item.PIPELINE);

        for (String nameProject : nameProjects) {
            Assert.assertTrue(getDriver().findElement(By.cssSelector("tr#job_" + nameProject)).isDisplayed());
        }
    }


}
