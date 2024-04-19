package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class ExistingPipelineConfigTest extends BaseTest  {
    public void precondition() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys("Pipeline1");
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    public boolean position(String menuOption) {
        // Find the menu option element based on its text
        WebElement menuElement = getDriver().findElement(By.xpath("//div[@id='" + menuOption + "']"));

        // Initialize JavaScript executor
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        // Execute JavaScript to check if the menu element is within the viewport
        Boolean result = (Boolean) js.executeScript("let rect = arguments[0].getBoundingClientRect();" +
                "return (rect.top >= 0 && rect.left >= 0 && " +
                "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                "rect.right <= (window.innerWidth || document.documentElement.clientWidth));", menuElement);

        return result;
    }

    @Test
    public void testPipelineConfig() throws InterruptedException {
        precondition();

        getDriver().findElement(By.xpath("//a[@href='job/Pipeline1/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/Pipeline1/configure']")).click();

        String configTitle = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(configTitle, "Configure");

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        boolean resultBeforeButtonClick = position("pipeline");
        Assert.assertFalse(resultBeforeButtonClick);

        getDriver().findElement(By.xpath("//button[@data-section-id='pipeline']")).click();

        Thread.sleep(3000);
        boolean resultAfterButtonClick = position("pipeline");
        Assert.assertTrue(resultAfterButtonClick);
    }
}