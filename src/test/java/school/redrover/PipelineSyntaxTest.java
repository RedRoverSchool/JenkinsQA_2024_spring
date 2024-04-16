package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineSyntaxTest extends BaseTest {
    @Test
    public void testPipelineSyntax() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("OrganizatinFolder1");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.linkText("Pipeline Syntax")).click();

        Assert.assertTrue(getDriver().findElement(By.linkText("Snippet Generator")).isDisplayed());

    }

}
