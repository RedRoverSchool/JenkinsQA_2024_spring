package school.redrover;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import school.redrover.runner.BaseTest;

public class MultibranchPipelineDependentTests extends BaseTest {

    private final String MULTI_PIPELINE_NAME = "MultibranchPipeline";
    private final String NEW_MULTI_PIPELINE_NAME = "newMultibranchPipeline";

    @Test

    public void testCreate() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
            .sendKeys(MULTI_PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class*='WorkflowMultiBranchProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        WebElement actualMultibranchPipelineName = getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//li[3]"));

        Assert.assertEquals(actualMultibranchPipelineName.getText(), MULTI_PIPELINE_NAME);
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    @Test(dependsOnMethods = "testCreate")
    public void testChangeFromDisableOnStatusPage() {
        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        WebElement configureLink = getDriver().findElement(By.cssSelector(".task-link-wrapper [href$='configure']"));
        configureLink.click();
        if (getDriver().findElement(By.className("jenkins-toggle-switch__label__checked-title"))
            .isDisplayed()) {
            getDriver().findElement(By.cssSelector("[data-title*='Disabled']")).click();
        }
        getDriver().findElement(By.cssSelector("[name*='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        getDriver().findElement(By.xpath("//button[contains(., 'Enable')]")).click();
        List<WebElement> disabledMultiPipelineMessage = getDriver().findElements(
            By.xpath("//form[contains(., 'This Multibranch Pipeline is currently disabled')]"));

        Assert.assertEquals(disabledMultiPipelineMessage.size(), 0, "Disabled message is displayed!!!");
    }

    @Test(dependsOnMethods = {"testCreate", "testVerifyMpDisabledOnStatusPage"})
    public void testRenameOnTheSidebar() {
        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        getDriver().findElement(By.cssSelector("[href $='rename']")).click();
        WebElement renameInput = getDriver().findElement(By.xpath("//input[@name='newName']"));
        renameInput.clear();
        renameInput.sendKeys(NEW_MULTI_PIPELINE_NAME);
        getDriver().findElement(By.name("Submit")).click();
        String multiPipelinePageHeading = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(multiPipelinePageHeading, NEW_MULTI_PIPELINE_NAME, "Wrong name");
    }

    @Test (dependsOnMethods = "testCreate")
    public void  testVerifyMpDisabledOnStatusPage() {
        getDriver().findElement(By.cssSelector("[href='job/" + MULTI_PIPELINE_NAME + "/']")).click();
        getDriver().findElement(By.name("Submit")).click();

        String disabledMpMessage = getDriver().findElement(By.id("enable-project"))
                .getDomProperty("innerText").split(" Enable")[0];
        Assert.assertEquals(disabledMpMessage,"This Multibranch Pipeline is currently disabled");
    }
}
