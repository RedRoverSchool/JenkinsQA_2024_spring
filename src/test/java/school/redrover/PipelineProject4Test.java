package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineProject4Test extends BaseTest {


    @Test
    public void testVerifyNewPPCreatedNewItem() {
        final String nameProject = "PPProject";

        WebElement buttonNewItem = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@it='hudson.model.Hudson@4149531c']")));
        buttonNewItem.click();
        getDriver().findElement(By.cssSelector("div.add-item-name > input#name")).sendKeys(nameProject);
        getDriver().findElement(By.cssSelector(".org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.cssSelector("button#ok-button")).click();

        getDriver().findElement(By.cssSelector("button.jenkins-button--primary")).click();

        getDriver().findElement(By.cssSelector("li.jenkins-breadcrumbs__list-item > a[href='/']")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("tr#job_" + nameProject)).isDisplayed());
    }


}
